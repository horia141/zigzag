/**
 * DescriptionCellSource.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit

/**
 * model class that encompasses data for a description cell view
 */
class DescriptionCellSource: CellSource {
    /**
     * private member vars
     */
    private var description_text: String = ""
    
    /**
     * custom constructor
     */
    init(descriptionText: String){
        super.init()
        self.description_text = descriptionText
    }
    
    /**
     * getters
     */
    func getDescriptionText() -> String{
        return self.description_text
    }
}
