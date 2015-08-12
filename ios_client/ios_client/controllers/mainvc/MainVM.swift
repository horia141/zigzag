/**
 * MainVM.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit

/**
 * This is the VC that presents data to the user
 */
class MainVM: NSObject, UITableViewDataSource {
    
    /**
     * VM variables
     */
    var vm_artifact: Artifact! = nil
    var cellsources: [CellSource] = []
   
    /**
     * custom init variables
     */
    override init(){
        super.init()
    }
    
    /**
     * custom vm functions
     */
    func initVMWithData(artifact: Artifact){
        // get artifact
        self.vm_artifact = artifact
        
        // clear cellsources
        cellsources = []
        
        // form title cell source
        var title = vm_artifact.getTitle() as String
        var source = vm_artifact.getArtifactSource().getName() as String
        // create the date - a little more complicated
        var dt = NSDate(timeIntervalSince1970: self.vm_artifact.getArtifactDate())
        var dt_formatter = NSDateFormatter()
        dt_formatter.dateFormat = "d MMM yyyy h:m a"
        var postdate = dt_formatter.stringFromDate(dt)
        cellsources.append(TitleCellSource(title: title, source: source, postDate: postdate))
        
        // form different cell sources based on photo descriptions
        for descr in vm_artifact.getPhotoDescriptions() {
            // get common description and subtitle
            var description_text = descr.getDescriptionText()
            var subtitle = descr.getSubtitle()
            var type = (descr as PhotoDescription).getPhotoData().getType()
            
            switch type{
            // case type
            case PhotoData.getImagePhotoDataType():
                // get photo data
                var photo_data = (descr as PhotoDescription).getPhotoData() as! ImagePhotoData
                
                // now create cell sources for the photo cell source object
                var subcellsources: [CellSource] = []
                
                // append subtitle, if exists
                // @Warning: have to fix this check to be better
                if (subtitle != ""){
                    subcellsources.append(SubtitleCellSource(subtitle: subtitle))
                }
                
                // append the tiles
                for tile in photo_data.getTiles() {
                    subcellsources.append(TileCellSource(tile: tile as Tile, isMovie: false, video: nil))
                }
                
                // append description, if exists
                // @Warning: have to fix this check to be better
                if (description_text != ""){
                    subcellsources.append(DescriptionCellSource(descriptionText: description_text))
                }
                
                // now finally append a good photo cell source
                cellsources.append(PhotoCellSource(subCellSources: subcellsources))
            break
            case PhotoData.getVideoPhotoDataType():
                // get photo data
                var photo_data = (descr as PhotoDescription).getPhotoData() as! VideoPhotoData
                
                // now create cell sources for the video cell source object
                var subcellsources: [CellSource] = []
                
                // append subtitle, if exists
                // @Warning: have to fix this check to be better
                if (subtitle != ""){
                    subcellsources.append(SubtitleCellSource(subtitle: subtitle))
                }
                
                // append the first frame
                subcellsources.append(TileCellSource(tile: photo_data.getFirstFrame(), isMovie: true, video: photo_data.getVideo()))
                
                // append description, if exists
                // @Warning: have to fix this check to be better
                if (description_text != ""){
                    subcellsources.append(DescriptionCellSource(descriptionText: description_text))
                }
                
                // now finally append a good video cell source
                cellsources.append(VideoCellSource(subCellSources: subcellsources))
            break
            default:
//                println("default")
            break
            }
        }
    }
    
    /**
     * returns a single cell source
     */
    func getCellSourceAtIndexPath(indexPath: NSIndexPath) -> CellSource{
        return cellsources[indexPath.row]
    }
    
    /**
     * Table view data source delegate methods
     */
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cellsources.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var object = cellsources[indexPath.row] as CellSource
        return object.tableView(tableView, representationAsCellForIndexPath: indexPath)
    }
}
