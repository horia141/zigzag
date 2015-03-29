/**
 * ImageDescription.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

/**
 * This class describes an image
 */
class ImageDescription: NSObject {
    /**
     * private imageDescription class variables
     */
    private var subtitle: String! = nil
    private var imgDescription: String! = nil
    private var sourceUri: String! = nil
    private var originalImageUriPath: String! = nil
    private var imageData: [ScreenConfig: ImageData] = [:]
    
    /**
     * custom constructor
     */
    init(subtitle: String, imgDescription: String, sourceUri: String, originalImageUriPath: String,
        imageData: [ScreenConfig: ImageData])
    {
        super.init()
        self.subtitle = subtitle
        self.imgDescription = imgDescription
        self.sourceUri = sourceUri
        self.originalImageUriPath = originalImageUriPath
        self.imageData = imageData
    }
    
    /**
     * custom getters
     */
    func getSubtitle() -> String{
        return self.subtitle
    }
    
    func getImgDescription() -> String {
        return self.imgDescription
    }
    
    func getSourceUri() -> String {
        return self.sourceUri
    }
    
    func getOriginalImageUriPath() -> String {
        return self.originalImageUriPath
    }
    
    func getImageData() -> [ScreenConfig: ImageData] {
        return self.imageData
    }
}
