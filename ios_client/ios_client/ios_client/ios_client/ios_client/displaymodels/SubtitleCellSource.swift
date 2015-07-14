/**
 * SubtitleCellSource.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit

/**
 * model class that encompasses data for a subtitle cell view
 */
class SubtitleCellSource: CellSource {
    /**
     * private member vars
     */
    private var subtitle: String = ""
    
    /**
     * custom constructor
     */
    init(subtitle: String){
        super.init()
        self.subtitle = subtitle
    }
    
    /**
     * getters
     */
    func getSubtitle() -> String{
        return self.subtitle
    }
}
