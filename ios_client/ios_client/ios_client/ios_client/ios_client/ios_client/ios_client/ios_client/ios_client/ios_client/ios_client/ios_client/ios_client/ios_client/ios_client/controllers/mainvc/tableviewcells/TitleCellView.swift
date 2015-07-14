/**
 * TitleCellView.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

class TitleCellView: UITableViewCell {

    /**
     * Cell Outlets
     */
    @IBOutlet weak var ContentView: UIView!
    @IBOutlet weak var SourceLabel: UILabel!
    @IBOutlet weak var DateLabel: UILabel!
    @IBOutlet weak var TitleLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        // Initialization code
        ContentView.layer.shadowColor = UIColor.blackColor().CGColor
        ContentView.layer.shadowOpacity = 0.2
        ContentView.layer.shadowOffset = CGSizeZero
        ContentView.layer.masksToBounds = false
        ContentView.layer.shadowRadius = 2
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
