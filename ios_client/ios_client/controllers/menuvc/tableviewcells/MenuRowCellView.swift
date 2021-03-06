/**
 * MenuRowCellView.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

class MenuRowCellView: UITableViewCell {

    @IBOutlet weak var MenuText: UILabel!
    @IBOutlet weak var Icon: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    
        // find out color
        var text_color: UIColor = UIColor(red: 0.85, green: 0.85, blue: 0.85, alpha: 1)
        var icon_color: UIColor = UIColor(red: 0.7, green: 0.7, blue: 0.7, alpha: 1)
        if (selected){
            text_color = UIColor.whiteColor()
            icon_color = UIColor(red: 37/255.0, green: 183/255.0, blue: 211/255.0, alpha: 1)
        }
        
        // set the actual colors
        self.MenuText.textColor = text_color
        self.Icon.image = self.Icon.image?.imageWithRenderingMode(UIImageRenderingMode.AlwaysTemplate)
        self.Icon.tintColor = icon_color
    }
}
