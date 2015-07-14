/**
 * Utils.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

func calcTextHeightForWidth(width _width: CGFloat, text _text: String) -> CGFloat{
    var attr = [NSFontAttributeName: UIFont.systemFontOfSize(14)]
    var attrText = NSAttributedString(string: _text, attributes: attr)
    var rect = attrText.boundingRectWithSize(
        CGSizeMake(_width, CGFloat.max),
        options: NSStringDrawingOptions.UsesLineFragmentOrigin | NSStringDrawingOptions.UsesFontLeading,
        context: nil)
    var fl = (Float)(rect.size.height as CGFloat)
    return (CGFloat)((ceilf(fl)+22)*0.75);
}

/**
 * custom scaling functions
 */
func calcNewHeightFor(newWidth _newWidth: CGFloat, fromOldWidth _oldWidth: CGFloat, andOldHeight _oldHeight: CGFloat) -> CGFloat{
    return (_newWidth * _oldHeight) / _oldWidth
}

func calcProportionalSizeForTile(width _width: CGFloat, height _height: CGFloat) -> CGSize{
    let new_width = UIScreen.mainScreen().bounds.width - (16 as CGFloat)
    var new_height = (new_width * _height) / _width
    return CGSizeMake(new_width, new_height)
}

func getVC(withId _storyboardId: String) -> UIViewController {
    var storyboard = UIStoryboard(name: "Main", bundle: nil) as UIStoryboard
    return storyboard.instantiateViewControllerWithIdentifier(_storyboardId) as! UIViewController
}

/**
 * Create custom sharing VC
 */
func shareInfo(title _title: String?, text _text: String?, url _url: String?, img _img: String?) {
    var items: [AnyObject] = [] as [AnyObject]
    
    if let t = _title {
        items.append(t as String)
    }
    if let tx = _text {
        items.append(tx as String)
    }
    if let u = _url {
        items.append(NSURL(string: u) as NSURL!)
    }
    if let i = _img {
        items.append(UIImage(named: i) as UIImage!)
    }
    
    var activity =  UIActivityViewController(activityItems: items, applicationActivities: nil)
    UIApplication.sharedApplication().keyWindow?.rootViewController?.presentViewController(activity, animated: true, completion: nil)
}

//func calcTextHeightForWidth2(width _width: CGFloat, text _text: String) -> CGFloat {
//    var tmplabel = UILabel(frame: CGRectMake(0, 0, _width, CGFloat.max))
//    tmplabel.font = UIFont.systemFontOfSize(14)
//    tmplabel.text = _text
//    tmplabel.numberOfLines = 0
//    tmplabel.lineBreakMode = NSLineBreakMode.ByWordWrapping
//    tmplabel.sizeToFit()
//    return tmplabel.frame.size.height
//}