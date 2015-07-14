/**
 * MenuGoSegue.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Custom segue to go to menu
 */
class MenuGoSegue: UIStoryboardSegue {
    override func perform() {
        // get
        var firstVC = self.sourceViewController as! UIViewController
        var secondVC = self.destinationViewController as! UIViewController
        
        // show
        firstVC.view.addSubview(secondVC.view)
        firstVC.addChildViewController(secondVC)
        (firstVC as! MenuVC).setCurrentVC(secondVC)
        secondVC.didMoveToParentViewController(firstVC)
        
        // add shadow
        secondVC.view.layer.shadowColor = UIColor.blackColor().CGColor
        secondVC.view.layer.shadowOpacity = 0.2
        secondVC.view.layer.shadowOffset = CGSizeZero
        secondVC.view.layer.masksToBounds = false
        secondVC.view.layer.shadowRadius = 2
        secondVC.view.layer.cornerRadius = 7
     }
}
