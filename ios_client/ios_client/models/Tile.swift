/**
 * Tile.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

class Tile: NSObject {
    /**
     * private member variables
     */
    private var uri_path: String! = nil
    private var height: CGFloat = 0
    private var width: CGFloat = 0
    private var full_uri_path: String! = nil
    
    /**
     * custom constructor
     */
    init(uriPath: String, fullUriPath: String, height: CGFloat, width: CGFloat){
        super.init()
        self.uri_path = uriPath
        self.height = height
        self.width = width
        self.full_uri_path = fullUriPath
    }
    
    /**
     * getter methods
     */
    func getUriPath() -> String{
        return self.uri_path
    }
    
    func getWidth() -> CGFloat{
        return self.width
    }
    
    func getHeight() -> CGFloat{
        return self.height
    }
    
    func getFullUriPath() -> String{
        return self.full_uri_path
    }
}