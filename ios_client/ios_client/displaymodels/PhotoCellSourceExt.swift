/**
 * PhotoCellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Extension functions for PhotoCellSource.
 */
extension PhotoCellSource{
    override func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> PhotoCellView {
        var cell = tableView.dequeueReusableCellWithIdentifier("PhotoCellViewId") as! PhotoCellView?
        if (cell == nil){
            cell = PhotoCellView()
        }
        
        // init this cell
        cell?.initWithSubCellSources(self.getSubCellSources())
//        cell?.layer.borderColor = UIColor.blackColor().CGColor
//        cell?.layer.borderWidth = 2.0
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        var h: CGFloat = 0
        for cell_src in self.getSubCellSources() {
            h += (cell_src as CellSource).heightForRowAtIndexPath()
        }
        return h+16
    }
}
