/**
 * MenuHeaderCellView.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Implementation for the Menu Header Cell view
 */
class MenuHeaderCellView: UITableViewCell {

    @IBOutlet weak var ZZLogoTitle: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        ZZLogoTitle.layer.cornerRadius = 7
        ZZLogoTitle.layer.masksToBounds = true
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
