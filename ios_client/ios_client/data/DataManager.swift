/**
 * DataManager.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * This is the data manager protocol
 *  Generation
 *      Artifact Sources
 *      Screen Config
 *      Artifact #1
 *          Image Description #1
 *              Screen Config #1 : ImageData {TileData #1, TileData #2}
 */
protocol DataManagerProtocol {
    func generationDidLoad(generation: Generation)
}

/**
 * This class parses data received from a connection into ZigZag objects
 */
class DataManager: NSObject, ConnectionManagerProtocol {
    // singleton method
    class var sharedInstance :DataManager {
        struct Singleton {
            static let instance = DataManager()
        }
        
        return Singleton.instance
    }
    
    // "constants"
    private let GEN_URL = "http://horia141.com:9000/api/v1/nextgen?from=1" as String
    private let IMG_URL = "http://horia141.com:9001/" as String
    
    // basic data variables
    private var connManager: ConnectionManager! = nil
    private var responseJSON: NSDictionary? = nil
    
    // members that deal directly with parsing and the app-world
    private var artifactSources: NSDictionary? = nil
    private var screenConfigs: NSDictionary? = nil
    private var generation: Generation? = nil
    
    // delegate implementation
    var data_delegate : DataManagerProtocol! = nil
    
    override init() {
        super.init()
    }
    
    func loadGeneration(){
        connManager = ConnectionManager()
        connManager.connection_delegate = self
        connManager.dummyRequest(GEN_URL)
    }
    
    /**
     * Implement the two Connection Manager Protocol functions
     */
    func getData(packet: NSData) {
        self.responseJSON = NSJSONSerialization.JSONObjectWithData(packet, options: NSJSONReadingOptions.MutableContainers, error: nil) as NSDictionary!
        println(self.responseJSON)
        if let resp = self.responseJSON{
            self.generation = parseGeneration(resp)
            
            // call to delegate
            self.data_delegate.generationDidLoad(self.generation!)
        }
    }
    
    func failedToGetData() {
        println("Somehow I failed to get data")
    }
    
    /**
    * Function that parses an artifact source
    */
    func parseArtifactSource(artsourcredata: NSDictionary) -> ArtifactSource{
        println("Artifact Source Keys \(artsourcredata.allKeys)")
        var id = artsourcredata.objectForKey("id") as String
        var entityId = EntityId(id: id)
        var start_page_url = artsourcredata.objectForKey("start_page_url") as String
        var name = artsourcredata.objectForKey("name") as String
        var time_added = artsourcredata.objectForKey("time_added") as String
        
        return ArtifactSource(id: entityId, startPageUrl: start_page_url, name: name, timeAdded: time_added)
    }
    
    /**
    * Function that parses a Screen Config
    */
    func parseScreenConfig(scrconfdata: NSDictionary) -> ScreenConfig {
        println("Screen Config Keys \(scrconfdata.allKeys)")
        var key = "" as NSString
        var width = scrconfdata.valueForKey("width") as Int
        
        return ScreenConfig(key: key, width: width);
    }
    
    /**
     * Function that parses a generation
     */
    func parseGeneration(gendata: NSDictionary) -> Generation {
        // get basic generation information
        println("Generation Keys: \(gendata.allKeys)")
        var id = gendata.objectForKey("id") as String
        var entityId = EntityId(id: id)
        var time_added = gendata.objectForKey("time_added") as String
        var time_closed = gendata.objectForKey("time_closed") as String
        var artifacts : [Artifact] = []
        
        // get all artifact sources
        self.artifactSources = gendata.objectForKey("artifact_sources") as? NSDictionary
        
        // get all screen configs
        self.screenConfigs = gendata.objectForKey("screen_configs") as? NSDictionary
        
        // get all artifacts in generation
        for artdict in gendata.objectForKey("artifacts") as NSArray {
           artifacts.append(parseArtifact(artdict as NSDictionary))
        }
        
        return Generation(id: entityId, timeAdded: time_added, timeClosed: time_closed, artifacts: artifacts)
    }
    
    /**
     * Function that parses an artifact
     */
    func parseArtifact(artdata: NSDictionary) -> Artifact {
        // get the basic info
        println("Artifact Keys \(artdata.allKeys)")
        var id = artdata.objectForKey("id") as String
        var entityId = EntityId(id: id)
        var title = artdata.objectForKey("title") as String
        var page_url = artdata.objectForKey("page_url") as String
        var image_descriptions : [ImageDescription] = []
        
        // now get an associated artifact source
        var art_source_id = artdata.objectForKey("artifact_source_id") as String
        var artsourcedata = self.artifactSources?.objectForKey(art_source_id) as NSDictionary
        var artifact_source = parseArtifactSource(artsourcedata)
        
        // now get all images descriptions
        var imgdesc = artdata.objectForKey("images_description") as NSArray
        for imgdescdata in imgdesc {
            image_descriptions.append(parseImageDescription(imgdescdata as NSDictionary))
        }
        
        return Artifact(id: entityId, pageUrl: page_url, artifactSource: artifact_source, title: title, imageDescription: image_descriptions)
    }
    
    /**
    * Function that parses an image description
    */
    func parseImageDescription(imgdescdata: NSDictionary) -> ImageDescription{
        // get basic data
        println("Image description Keys \(imgdescdata.allKeys)")
        var subtitle = imgdescdata.objectForKey("subtitle") as String
        var description = imgdescdata.objectForKey("description") as String
        var original_photo_uri_path = imgdescdata.objectForKey("original_photo_uri_path") as String
        var source_uri = imgdescdata.objectForKey("source_uri") as String
        var imageData: [ScreenConfig: ImageData] = [:] as Dictionary
        
        // now get image data
        var image_data = imgdescdata.objectForKey("photo_data") as NSDictionary
        for key in image_data.allKeys {
            // var get object
            var imgdata = image_data.objectForKey(key) as NSDictionary
            // get type
            var type = imgdata.objectForKey("type") as NSString
            
            // get screen config
            var scrconf = parseScreenConfig(self.screenConfigs?.objectForKey(key) as NSDictionary) as ScreenConfig
            // get associated image data
            if (type.isEqualToString("image")){
                imageData[scrconf] = parseImageData(imgdata)
            }
        }
        
        // return the object
        return ImageDescription(
            subtitle: subtitle,
            imgDescription: description,
            sourceUri: source_uri,
            originalPhotoUriPath: original_photo_uri_path,
            imageData: imageData
        )
    }
    
    /**
     * Function that parses image data
     */
    func parseImageData(imgdata: NSDictionary) -> ImageData{
        println("Image data Keys \(imgdata.allKeys)")
        var tiles_desc_dict = imgdata.objectForKey("tiles_desc") as NSArray
        
        var full_image_desc = parseTileData(imgdata.objectForKey("full_image_desc") as NSDictionary) as TileData
        var tiles_desc: [TileData] = []
        
        for tiledat in tiles_desc_dict {
            tiles_desc.append(parseTileData(tiledat as NSDictionary))
        }
        
        // return the data
        return ImageSetImageData(fullImageDesc: full_image_desc, tilesDesc: tiles_desc)
    }
    
    /**
     * Function that parses tile data
     */
    func parseTileData(tildata: NSDictionary) -> TileData {
        var width = tildata.valueForKey("width") as Int
        var height = tildata.valueForKey("height") as Int
        var uri_path = tildata.objectForKey("uri_path") as String
        return TileData(width: width, height: height, uriPath: uri_path)
    }
    
    /**
     * Helper getter functions
     */
    func getScreenConfigs() -> NSDictionary{
        return self.screenConfigs!
    }
    
    func getIMGURL() -> NSString{
        return self.IMG_URL
    }
}
