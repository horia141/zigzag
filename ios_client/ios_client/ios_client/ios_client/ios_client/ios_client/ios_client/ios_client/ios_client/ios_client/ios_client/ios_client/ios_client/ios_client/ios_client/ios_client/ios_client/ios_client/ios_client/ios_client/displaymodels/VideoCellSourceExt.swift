/**
 * VideoCellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Extension functions for VideoCellSource.
 */
extension VideoCellSource{
    /**
     * Override of public functions
     */
    override func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> VideoCellView {
        var cell = tableView.dequeueReusableCellWithIdentifier("VideoCellViewId") as! VideoCellView?
        if (cell == nil){
            cell = VideoCellView()
        }
        
        // init this cell
        cell?.initWithSubCellSources(self.getSubCellSources())
        
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        var h: CGFloat = 0
        for cell_src in self.getSubCellSources() {
            h += (cell_src as CellSource).heightForRowAtIndexPath()
        }
        return h+8
    }
}
