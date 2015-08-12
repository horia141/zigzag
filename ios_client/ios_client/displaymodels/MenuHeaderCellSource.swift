/**
 * MenuHeaderCellSource.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Implementation for the menu header cell source class
 */
class MenuHeaderCellSource: CellSource {
    
    /**
     * title
     */
    private var title: String! = nil
    
   /**
    * custom init
    */
    init (title: String){
        super.init()
        self.title = title
    }
    
    /**
     * custom setters and getters
     */
    func getTitle() -> String {
        return self.title
    }
}
