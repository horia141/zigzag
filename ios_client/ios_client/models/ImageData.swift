/**
* ImageData.swift
* Created by Liviu Coman on 3/24/15.
* Copyright (c) 2015 LiviuComan. All rights reserved.
* based on design by HoriaComan
*/

import UIKit

/**
 * ImageData is an abastract base class for all other types
 * of image data in the application:
 * - ImageSetImageData
 * - AnimationSetImageData
 * - TooBigImageData
 * In this regards, since Swift does not have abstract base classes,
 * I will implement it as a protocol, and the other three classes
 * will have to implement all methods described in this protocol
 */
protocol ImageData {
    /**
     * get the Uri paths to fetch for the image 
     * which will be handled differently by each image that 
     * implements this protocol
     */
    func getUriPathsToFetch() -> [String]
    
    /**
     * get the total height of the image object; this will
     * be handled by each class that implements this protocol
     * in a different way
     */
    func getTotalHeight() -> Int
}