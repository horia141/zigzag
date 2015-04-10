/**
 * ScreenConfig.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

/**
 * Class Screen config that holds info about the
 * screen display method of an image
 */
class ScreenConfig: NSObject {
   /**
    * private variables
    */
    private var key: String! = nil
    private var width: Int! = nil
    
    /**
     * custom constructor
     */
    init(key: String, width: Int){
        super.init()
        self.key = key
        self.width = width
    }
    
    /**
     * getter functions
     */
    func getKey() -> String {
        return self.key
    }
    
    func getWidth() -> Int {
        return self.width
    }
    
    /**
     * function that acts as Equal
     */
    func equals(var o: AnyObject) -> Bool{
        if (self == o as ScreenConfig){
            return true
        }
        
        if (!(o is ScreenConfig)){
            return false;
        }
        
        if (self.width != (o as ScreenConfig).width){
            return false
        }
        
        if (self.key != (o as ScreenConfig).key){
            return false
        }
        
        return true
    }
    
    /**
     * form and get the hash key
     */
    func hashCode() -> Int {
        var result = key.hash
        return 31 * result + width
    }
}
