/**
 * TileCellView.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

protocol TileCellViewProtocol {
    func playVideo()
}

class TileCellView: UITableViewCell {

    /**
     * Outlets
     */
    @IBOutlet weak var TileImage: UIImageView!
    @IBOutlet weak var LoadingSpinner: UIImageView!
    @IBOutlet weak var PlayMovie: UIButton!
    var tilecell_delegate: TileCellViewProtocol! = nil
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        // setup play movie as hidden
        self.PlayMovie.hidden = true
        
        // setup the spinner
        LoadingSpinner.animationImages = [UIImage]()
        LoadingSpinner.animationDuration = 1
        
        for (var i = 0; i < 9; i++){
            var str = (i < 9 ? "00\(i)" : "0\(i)")
            var frameName = "4e239a307e\(str)"
            var img = UIImage(named: frameName) as UIImage!
            LoadingSpinner.animationImages?.append(UIImage(named: frameName)!)
        }
        
    }
    
    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    @IBAction func playVideo(sender: AnyObject) {
        self.PlayMovie.hidden = true
        self.tilecell_delegate.playVideo()
    }
}
