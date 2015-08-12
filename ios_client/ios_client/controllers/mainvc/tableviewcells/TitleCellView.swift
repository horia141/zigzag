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
    @IBOutlet weak var DateLabel: UILabel!
    @IBOutlet weak var TitleLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
}
