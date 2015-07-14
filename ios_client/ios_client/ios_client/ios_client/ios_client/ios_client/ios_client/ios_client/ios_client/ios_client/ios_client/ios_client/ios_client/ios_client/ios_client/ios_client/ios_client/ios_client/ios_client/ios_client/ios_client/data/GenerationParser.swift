/**
 * GenerationParser.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit

class GenerationParser: NSObject {
    /**
     * private variables
     */
    private var data: NSData! = nil
    private var artifactSources: [ArtifactSource] = []
    private var generation: Generation! = nil
    
    /**
     * constructor
     */
    init(data: NSData){
        super.init()
        self.data = data
    }
    
    /**
     * stat parsing
     */
    func startParsingData() {
        // create a dictionary out of the raw data sent
        var responseJSON = NSJSONSerialization.JSONObjectWithData(self.data, options: NSJSONReadingOptions.MutableContainers, error: nil) as! NSDictionary!
        
        println("response json \(responseJSON)")
        
        // start the actual parsing procedure to pars a generation
        if let resp = responseJSON{
            var generation_dict = resp.objectForKey("generation") as! NSDictionary
            self.generation = parseGeneration(generation_dict)
        }
    }
    
    /**
     * Returns a parsed generation object
     */
    func getParsedGeneration() -> Generation {
        return self.generation
    }
    
    /**
     * Parse functions - starting with the main parse generation function
     */
    private func parseGeneration(gendata: NSDictionary) -> Generation? {
        // get basic generation information
        println("Generation Keys: \(gendata.allKeys)")
        var id = gendata.objectForKey("id") as! NSNumber
        var entity_id = EntityId(id: id)
        var datetime_started = gendata.objectForKey("datetime_started") as! String
        var datetime_started_ts = gendata.objectForKey("datetime_started_ts") as! NSTimeInterval
        var datetime_ended = gendata.objectForKey("datetime_ended") as! String
        var datetime_ended_ts = gendata.objectForKey("datetime_ended_ts") as! NSTimeInterval
        var artifacts : [Artifact] = []
        
        // get all artifact sources
        var artsourcedict = gendata.objectForKey("artifact_sources") as! NSDictionary
        for key in (artsourcedict.allKeys as NSArray){
            artifactSources.append(self.parseArtifactSource(artsourcedict.objectForKey(key) as! NSDictionary))
        }
        
        // get all artifacts in generation
        for artdict in gendata.objectForKey("artifacts") as! NSArray {
            artifacts.append(parseArtifact(artdict as! NSDictionary, artifactDate: datetime_ended_ts))
        }
        
        return Generation(id: entity_id, timeStarted: datetime_started, timeEnded: datetime_ended, timeStartedTS: datetime_started_ts, timeEndedTS: datetime_ended_ts, artifacts: artifacts)
    }
    
    /**
     * Function that parses an artifact source
     */
    private func parseArtifactSource(artsourcredata: NSDictionary) -> ArtifactSource{
        println("Artifact Source Keys \(artsourcredata.allKeys)")
        var id = artsourcredata.objectForKey("id") as! NSNumber
        var entity_id = EntityId(id: id)
        var start_page_uri = artsourcredata.objectForKey("start_page_uri") as! String
        var name = artsourcredata.objectForKey("name") as! String
        
        return ArtifactSource(id: entity_id, startPageUri: start_page_uri, name: name)
    }
    
    /**
     * Aux function that returns a single artifact source from the
     * artifact sources array
     */
    private func auxGetArtifactSourceById(ArtifactSourcePk pk: NSNumber) -> ArtifactSource!{
        for artsrc in artifactSources{
            if (artsrc as ArtifactSource).getId().getId() == pk {
                return artsrc as ArtifactSource
            }
        }
        
        return nil
    }
    
    /**
     * Function that parses an artifact
     */
    private func parseArtifact(artdata: NSDictionary, artifactDate: NSTimeInterval) -> Artifact {
        // get the basic info
        println("Artifact Keys \(artdata.allKeys)")
        var id = artdata.objectForKey("id") as! NSNumber
        var entity_id = EntityId(id: id)
        var title = artdata.objectForKey("title") as! String
        var page_uri = artdata.objectForKey("page_uri") as! String
        var artifact_source_pk = artdata.objectForKey("artifact_source_pk") as! NSNumber
        var artifact_source = auxGetArtifactSourceById(ArtifactSourcePk: artifact_source_pk)
        
        // now get all images descriptions
        var photo_descriptions: [PhotoDescription] = []
        var photodesc = artdata.objectForKey("photo_descriptions") as! NSArray
        for photo in photodesc {
            photo_descriptions.append(parsePhotoDescription(photo as! NSDictionary))
        }
        
        return Artifact(
            id: entity_id,
            pageUri: page_uri,
            artifactSource: artifact_source,
            title: title,
            photoDescription: photo_descriptions,
            artifactDate: artifactDate)
    }
    
    /**
     * Function that parses an image description
     */
    private func parsePhotoDescription(imgdescdata: NSDictionary) -> PhotoDescription{
        // get basic data
        println("Image description Keys \(imgdescdata.allKeys)")
        var subtitle = imgdescdata.objectForKey("subtitle") as! String
        var description_text = imgdescdata.objectForKey("description") as! String
        var source_uri = imgdescdata.objectForKey("source_uri") as! String
        var photodata_dict = imgdescdata.objectForKey("photo_data") as! NSDictionary
        var photodata = parsePhotoData(photodata_dict) as PhotoData
        
        return PhotoDescription(subtitle: subtitle, descriptionText: description_text, sourceUri: source_uri, photoData: photodata)
    }
    
    /**
     * Function that parses a photo data dictionary and returns:
     * - if type == 'image-photo-data' --> new instance of ImagePhotoData
     * - if type == 'video-photo_data' --> new instance of VideoPhotoData
     */
    private func parsePhotoData(photodata: NSDictionary) -> PhotoData! {
        var type = photodata.objectForKey("type") as! String
        if type == PhotoData.getImagePhotoDataType(){
            var data = photodata.objectForKey("image_photo_data") as! NSDictionary
            return parseImagePhotoData(data)
        }
        else if type == PhotoData.getVideoPhotoDataType(){
            var data = photodata.objectForKey("video_photo_data") as! NSDictionary
            return parseVideoPhotoData(data)
        }
        
        return nil
    }
    
    /**
     * Function that parses image photo data
     */
    private func parseImagePhotoData(imagedata: NSDictionary) -> ImagePhotoData {
        // get basic data
        println("Image photo data description Keys \(imagedata.allKeys)")
        var tiles: [Tile] = []
        var tiles_nsarray = imagedata.objectForKey("tiles") as! NSArray
        for tile in tiles_nsarray {
            tiles.append(parseTile(tile as! NSDictionary))
        }
        return ImagePhotoData(tiles: tiles)
    }
    
    /**
     * Function that parses video photo data
     */
    private func parseVideoPhotoData(videodata: NSDictionary) -> VideoPhotoData {
        // get basic data
        println("Video photo data description Keys \(videodata.allKeys)")
        var frames_per_sec = videodata.valueForKey("frames_per_sec") as! Int
        var time_between_frames_ms = videodata.valueForKey("time_between_frames_ms") as! Int
        var first_frame_dict = videodata.objectForKey("first_frame") as! NSDictionary
        var video_dict = videodata.objectForKey("video") as! NSDictionary
        var first_frame = parseTile(first_frame_dict) as Tile
        var video = parseTile(video_dict) as Tile
        
        return VideoPhotoData(firstFrame: first_frame, video: video, framesPerSec: frames_per_sec, timeBetweenFramesMs: time_between_frames_ms)
    }
    
    /**
     * Function that parses a tile
     */
    private func parseTile(tiledata: NSDictionary) -> Tile {
        // get basic data
        println("Tile description Keys \(tiledata.allKeys)")
        var uri_path = tiledata.objectForKey("uri_path") as! String
        var full_uri_path = GenerationManager.sharedInstance.getImgURL() + uri_path
        var width = tiledata.valueForKey("width") as! CGFloat
        var height = tiledata.valueForKey("height") as! CGFloat
        
        return Tile(uriPath: uri_path, fullUriPath: full_uri_path, height: height, width: width)
    }
    
}
