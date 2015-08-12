/**
 * SubtitleCellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Extension functions for SubtitleCellSource.
 */
extension SubtitleCellSource{
    /**
     * public overidden functions
     */
    override func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> SubtitleCellView {
        // create
        var cell = tableView.dequeueReusableCellWithIdentifier("SubtitleCellViewId") as! SubtitleCellView?
        if (cell == nil){
            cell = SubtitleCellView()
        }
        
        // customize
        cell?.SubtitleLabel.text = self.getSubtitle()
        
//        cell?.SubtitleLabel.layer.borderColor = UIColor.redColor().CGColor
//        cell?.SubtitleLabel.layer.borderWidth = 1.0
        
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        var text_w = UIScreen.mainScreen().bounds.width - 16
        return calc3TextHeightForWidth(
            width:text_w,
            text: self.getSubtitle(),
            font:UIFont(name: "Arial-BoldMT", size: 14)!
        )+8
    }
}
