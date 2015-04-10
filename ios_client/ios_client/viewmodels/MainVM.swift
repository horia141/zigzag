/**
 * MainVM.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit

/**
 * This is the VC that presents data to the user
 */
class MainVM: NSObject {
   
    /**
     * view model variables
     */
    private var current_config: ScreenConfig! = nil
    private var artifact: Artifact! = nil
    private var image_descriptions: [ImageDescription] = []
    
    /**
     * custom init variables
     */
    init(artifact: Artifact?){
        super.init()
        
        if let a = artifact {
            // init the artifact
            self.artifact = artifact
            
            self.image_descriptions = self.artifact.getImagesDescription()
            var key_configs = DataManager.sharedInstance.getScreenConfigs() as NSDictionary
            self.current_config = DataManager.sharedInstance.parseScreenConfig(key_configs.objectForKey("1200") as NSDictionary)
        }
    }
    
    /**
     * custom specialized getters
     */
    func getArtifactTitle() -> String{
        return self.artifact.getTitle()
    }
    
    func getImageDescriptions() -> [ImageDescription]{
        return self.image_descriptions
    }
    
    func getImageDescription(index i: Int) -> ImageDescription{
        return self.image_descriptions[i]
    }
    
    func getTilesArrayForImageSetImageData(index i: Int) -> [TileData]{
        // get the current image description (from a list of them for each
        // artifact)
        var loc_image_description = self.image_descriptions[i]
        // get dictionary of ScreenConfigs : ImageData objects
        var image_data_dict = (loc_image_description.getImageData() as [ScreenConfig: ImageData])
        
        // parse through this dict, find the value that corresponds with the
        // predefined "current config" and return it's tiles array
        for (config, image_set) in image_data_dict{
            var w = (config as ScreenConfig).getWidth()
            if (w == self.current_config.getWidth()){
                return (image_set as ImageSetImageData).getTilesDesc()
            }
        }
        
        // or, in case of utter failure, return zilch
        return []
    }
    
    func getTileInArray(index i:Int, tile j: Int) -> TileData {
        return ((getTilesArrayForImageSetImageData(index: i)) as [TileData])[j]
    }
    
    func getTileInArrayCalcHeight(index i:Int, tile j: Int) -> CGFloat {
        var screen_w = UIScreen.mainScreen().bounds.width
        var tile = getTileInArray(index: i, tile: j) as TileData
        var tile_w = CGFloat(tile.getWidth())
        var tile_h = CGFloat(tile.getHeight())
        
        println("\(tile_w) ...... \(tile_h)")
        println("\(screen_w) ..... \((screen_w * tile_h) / tile_w)")
        
        return (screen_w * tile_h) / tile_w
    }
    
}
