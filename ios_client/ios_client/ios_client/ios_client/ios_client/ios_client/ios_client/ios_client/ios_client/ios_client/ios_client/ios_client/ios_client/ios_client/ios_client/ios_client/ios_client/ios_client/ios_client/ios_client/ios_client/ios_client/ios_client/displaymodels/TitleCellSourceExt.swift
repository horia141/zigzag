/**
 * TitleCellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Extension functions for TitleCellSource.
 */
extension TitleCellSource{
    override func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> TitleCellView {
        // create
        var cell = tableView.dequeueReusableCellWithIdentifier("TitleCellViewId") as! TitleCellView?
        if (cell == nil){
            cell = TitleCellView()
        }
        
        // customize
        cell?.TitleLabel.text = self.getTitle().capitalizedString
        cell?.SourceLabel.text = self.getSource()
        cell?.DateLabel.text = self.getPostDate()
        
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        var width = self.getMaxCellWidth()
        return 48 + calcTextHeightForWidth(width: width, text: self.getTitle())
    }
}
