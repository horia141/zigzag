/**
 * MenuRowCellSourceExt.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Extension for the Menu row cell source
 */
extension MenuRowCellSource {
    /**
    * public overidden functions
    */
    override func tableView(tableView: UITableView, representationAsCellForIndexPath indexPath: NSIndexPath) -> MenuRowCellView {
        // create
        var cell = tableView.dequeueReusableCellWithIdentifier("MenuRowCellViewId") as! MenuRowCellView?
        if (cell == nil){
            cell = MenuRowCellView()
        }
        
        cell?.selectionStyle = UITableViewCellSelectionStyle.None
        cell?.MenuText.text = self.getMenuRowTitle()
        cell?.Icon.image = UIImage(named: self.getIcon())
        cell?.Icon.image = cell?.Icon.image?.imageWithRenderingMode(UIImageRenderingMode.AlwaysTemplate)
        cell?.Icon.tintColor = UIColor(red: 0.7, green: 0.7, blue: 0.7, alpha: 1)
        cell?.backgroundColor = UIColor.clearColor()
        
        return cell!
    }
    
    override func heightForRowAtIndexPath() -> CGFloat {
        return 44
    }
}