/**
 * MainVC_TableViewCell.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
*/

import UIKit

/**
 * This is the cell that I will use to display images
 */
class MainVC_TableViewCell: UITableViewCell {

    @IBOutlet weak var TileImage: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        // Configure the view for the selected state
    }
    
}
