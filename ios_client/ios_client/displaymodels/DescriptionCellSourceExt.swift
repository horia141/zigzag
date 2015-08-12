/**
 * DescriptionCellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Extension functions for DescriptionCellSource.
 */
extension DescriptionCellSource{
    /**
     * public overidden functions
     */
    override func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> DescriptionCellView {
        // create
        var cell = tableView.dequeueReusableCellWithIdentifier("DescriptionCellViewId") as! DescriptionCellView?
        if (cell == nil){
            cell = DescriptionCellView()
        }
        
        // customize
        cell?.DescriptionLabel.text = self.getDescriptionText()
        
//        cell?.DescriptionLabel.layer.borderColor = UIColor.blueColor().CGColor
//        cell?.DescriptionLabel.layer.borderWidth = 1.0
        
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        var text_w = UIScreen.mainScreen().bounds.width - 16
        return calc3TextHeightForWidth(
            width: text_w,
            text: self.getDescriptionText(),
            font: UIFont(name: "ArialMT", size: 14)!
        )+8
    }
}
