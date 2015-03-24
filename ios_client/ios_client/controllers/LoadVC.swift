//
//  ViewController.swift
//  ios_client
//
//  Created by Liviu Coman on 3/24/15.
//  Copyright (c) 2015 LiviuComan. All rights reserved.
//

import UIKit

class LoadVC: UIViewController {

    var e1 : EntityId? = nil
    var e2 : EntityId? = nil
    var gen: Generation? = nil
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        e1 = EntityId(id: "213")
        e2 = EntityId(id: "213")
        gen = Generation(id: e1!, timeAdded: NSDate(), timeClosed: NSDate(), artifacts: [])
        
        println(e1!.hashCode())
        println(e1!.equals(e2!))
        println(gen!.getId().getId())
    
        var d1 = NSDate()
        var formatter = NSDateFormatter()
        formatter.dateFormat = "MMMM dd, YYYY "
        println(d1)
    
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

