/**
 * MenuHeaderCellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * extension for this
 */
extension MenuHeaderCellSource {
    /**
     * public overidden functions
     */
    override func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> MenuHeaderCellView {
        // create
        var cell = tableView.dequeueReusableCellWithIdentifier("MenuHeaderCellViewId") as! MenuHeaderCellView?
        if (cell == nil){
            cell = MenuHeaderCellView()
        }
        
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        return 200
    }
}
