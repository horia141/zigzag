/**
 * PhotoData.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

class PhotoData: NSObject {
    /**
     * private member variables
     */
    private var type: String! = nil
    
    /**
     * custom constructor
     */
    init(type: String){
        super.init()
        self.type = type
    }
    
    /**
     * getter methods
     */
    func getType() -> String{
        return self.type
    }
    
    /**
     * Define some sort of class constants 
     */
    class func getImagePhotoDataType() -> String {
        return "image-photo-data"
    }
    
    class func getVideoPhotoDataType() -> String {
        return "video-photo-data"
    }
}
