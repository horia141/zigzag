/**
 * ImageSetImageData.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

/**
 * The TooBig Image Data class is a class that implements the ImageData
 * protocol (Swift's/ObjC's way of implementing abstract base classes)
 */
class TooBigImageData: NSObject, ImageData {
   
    func getUriPathsToFetch() -> [String] {
        return []
    }
    
    func getTotalHeight() -> Int {
        return 0
    }
    
}
