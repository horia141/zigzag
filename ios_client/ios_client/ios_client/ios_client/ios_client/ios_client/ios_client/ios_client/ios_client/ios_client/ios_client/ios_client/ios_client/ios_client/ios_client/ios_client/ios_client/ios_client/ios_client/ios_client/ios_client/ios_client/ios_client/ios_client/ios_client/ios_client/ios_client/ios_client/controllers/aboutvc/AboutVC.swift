/**
 * AboutVC.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

class AboutVC: UIViewController {

    @IBOutlet weak var navigationBar: UINavigationBar!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        // some more adjusting
        var frame = self.navigationBar.frame
        frame.origin.y = -25
        frame.size.height = 69
        self.navigationBar.frame = frame
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func openMenu(sender: AnyObject) {
        // do nothing now
        NSNotificationCenter.defaultCenter().postNotificationName(MenuVC.MenuVCOpenMenuNotification(), object: nil)
    }
}
