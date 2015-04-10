/**
 * LoadVC.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * This is the loading VC, that is used to load the first batch of
 * data in the application
 */
class LoadVC: UIViewController {

    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        
        let priority = DISPATCH_QUEUE_PRIORITY_DEFAULT
        dispatch_async(dispatch_get_global_queue(priority, 0)) {
            
            // do some task
            dispatch_async(dispatch_get_main_queue()) {
                // update some UI
                self.performSegueWithIdentifier("LoadVCtoMainVCSegue", sender: self)
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

