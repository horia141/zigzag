/**
 * AsyncImageLoad.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * This function loads an image asyncroniously
 */
func asyncImageLoad(urlstring: NSString, success: (UIImage, CGRect) -> ()){
    // perform async image loading
    // get image in background
    let priority = DISPATCH_QUEUE_PRIORITY_DEFAULT
    dispatch_async(dispatch_get_global_queue(priority, 0)) {
        
        // form the url
        var fullurl = urlstring as String
        var url: NSURL = NSURL(string: fullurl)!
        var img: UIImage = UIImage(data: NSData(contentsOfURL: url)!)!
        
        var orig_w = CGFloat(CGImageGetWidth(img.CGImage))
        var orig_h = CGFloat(CGImageGetHeight(img.CGImage))
        var screen_w = UIScreen.mainScreen().bounds.width
        var new_h = (screen_w * orig_h) / orig_w
        
        // resize the image for the local display
        var newSize:CGSize = CGSizeMake(screen_w, new_h)
        let rect = CGRectMake(0, 0, newSize.width, newSize.height)
        UIGraphicsBeginImageContextWithOptions(newSize, false, 1.0)
        img.drawInRect(rect)
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        // go back
        dispatch_async(dispatch_get_main_queue()) {
            println("loaded \(fullurl)")
            // call success function
            success(newImage, CGRectMake(0, 0, screen_w, new_h))
        }
    }
}