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
class LoadVC: UIViewController, GenerationManagerProtocol {

    @IBOutlet weak var titleIco: UIImageView!
    /**
     * Outlet variables
     */
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // style for the icon
        titleIco.layer.cornerRadius = 15.0
        titleIco.layer.masksToBounds = true
        
        // Do any additional setup after loading the view, typically from a nib.
        GenerationManager.sharedInstance.generation_delegate = self
        GenerationManager.sharedInstance.downloadNextGeneration()
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        // setup navigation status
        self.navigationController?.navigationBarHidden = true
    }
    
    /**
     * Generation manager delegate methods
     */
    func didGetNextGeneration() {
        self.performSegueWithIdentifier("LoadVCtoMenuVCSegue", sender: self)
    }
    
    func didFailedToGetNextGeneration() {
        // alert somehow
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
//        if (segue.identifier == "LoadVCtoMenuVCSegue"){
//            (segue.destinationViewController as! MenuVC).performSegueWithIdentifier("", sender: sel)
//            var next = segue.destinationViewController as! MainVC
//            GenerationManager.sharedInstance.generation_delegate = next
//        }
    }
}

