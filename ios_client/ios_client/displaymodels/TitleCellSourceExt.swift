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
                
        // customize text
        cell?.TitleLabel.text = self.getTitle()
        cell?.DateLabel.text = "\(self.getPostDate()) by \(self.getSource())"
    
//        cell?.TitleLabel.layer.borderColor = UIColor.greenColor().CGColor
//        cell?.TitleLabel.layer.borderWidth = 1.0
//        cell?.DateLabel.layer.borderColor = UIColor.blueColor().CGColor
//        cell?.DateLabel.layer.borderWidth = 1.0
        
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        var width = UIScreen.mainScreen().bounds.width - 24
        return calc2TextHeightForWidth(
            width: self.getMaxCellWidth(),
            text: self.getTitle(),
            font: UIFont(name: "Arial-BoldMT", size: 18)!
        ) + 48
    }
}
