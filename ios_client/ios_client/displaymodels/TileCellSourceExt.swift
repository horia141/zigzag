/**
 * TileCellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Extension functions for TileCellSource.
 */
extension TileCellSource{
    /**
     * overidden functions for this extension implementation
     */
    override func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> TileCellView {
        // create
        var cell = tableView.dequeueReusableCellWithIdentifier("TileCellViewId") as! TileCellView?
        if (cell == nil){
            cell = TileCellView()
        }
        
        // clear image
        cell?.TileImage.image = nil
        
//        cell?.TileImage.layer.borderColor = UIColor.magentaColor().CGColor
//        cell?.TileImage.layer.borderWidth = 1.0
        
        cell?.LoadingSpinner.startAnimating()
        cell?.LoadingSpinner.alpha = 1
        
        // download image
        asyncImageLoad(self.getTile().getFullUriPath(), { (image: UIImage, size: CGRect) in
            cell?.TileImage.image = image
            cell?.LoadingSpinner.stopAnimating()
            cell?.LoadingSpinner.alpha = 0
            
            // just in case tile is movie
            if (self.getIsMovie()){
                cell?.PlayMovie.hidden = false
                cell?.tilecell_delegate = self
                self.setVideoContainer(cell!.TileImage)
            }
        })
        
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        var new_width = (UIScreen.mainScreen().bounds.width - 16) as CGFloat
        var old_width = self.getTile().getWidth()
        var old_height = self.getTile().getHeight()
        return calcNewHeightFor(
            newWidth: new_width,
            fromOldWidth: old_width,
            andOldHeight: old_height
        )
    }
}
