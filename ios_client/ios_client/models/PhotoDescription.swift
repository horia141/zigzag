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
class PhotoDescription: NSObject {
    /**
     * private imageDescription class variables
     */
    private var subtitle: String! = nil
    private var description_text: String! = nil
//    private var original_uri_path: String! = nil
    private var source_uri: String! = nil
    private var photo_data: PhotoData! = nil
    
    /**
     * custom constructor
     */
    init(subtitle: String, descriptionText: String, sourceUri: String, photoData: PhotoData)
    {
        super.init()
        self.subtitle = subtitle
        self.description_text = descriptionText
        self.source_uri = sourceUri
//        self.original_uri_path = originalUriPath
        self.photo_data = photoData
    }
    
    /**
     * custom getters
     */
    func getSubtitle() -> String{
        return self.subtitle
    }
    
    func getDescriptionText() -> String {
        return self.description_text
    }
    
    func getSourceUri() -> String {
        return self.source_uri
    }
    
//    func getOriginalUriPath() -> String {
//        return self.original_uri_path
//    }
    
    func getPhotoData() -> PhotoData {
        return self.photo_data
    }
}
