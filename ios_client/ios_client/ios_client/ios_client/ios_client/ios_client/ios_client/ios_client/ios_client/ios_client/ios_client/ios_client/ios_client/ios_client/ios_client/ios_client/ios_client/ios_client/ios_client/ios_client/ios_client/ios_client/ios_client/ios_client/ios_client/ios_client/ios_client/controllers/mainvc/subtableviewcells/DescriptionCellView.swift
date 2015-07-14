/**
 * DescriptionCellView.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

class DescriptionCellView: UITableViewCell {

    @IBOutlet weak var DescriptionLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
//        self.layer.borderColor = UIColor.blackColor().CGColor
//        self.layer.borderWidth = 1.0
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
