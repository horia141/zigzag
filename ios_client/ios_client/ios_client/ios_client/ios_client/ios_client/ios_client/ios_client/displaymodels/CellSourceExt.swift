/**
 * CellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Extension functions for CellSource. All children of CellSource will have
 * to implement the functions that are here
 */
extension CellSource{
    // base implementation of this function
    func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCellWithIdentifier("BasicCellId") as! UITableViewCell?
        if (cell == nil){
            cell = UITableViewCell()
        }
        
        return cell!
    }
    
    func heightForRowAtIndexPath() -> CGFloat {
        return 0
    }
}
