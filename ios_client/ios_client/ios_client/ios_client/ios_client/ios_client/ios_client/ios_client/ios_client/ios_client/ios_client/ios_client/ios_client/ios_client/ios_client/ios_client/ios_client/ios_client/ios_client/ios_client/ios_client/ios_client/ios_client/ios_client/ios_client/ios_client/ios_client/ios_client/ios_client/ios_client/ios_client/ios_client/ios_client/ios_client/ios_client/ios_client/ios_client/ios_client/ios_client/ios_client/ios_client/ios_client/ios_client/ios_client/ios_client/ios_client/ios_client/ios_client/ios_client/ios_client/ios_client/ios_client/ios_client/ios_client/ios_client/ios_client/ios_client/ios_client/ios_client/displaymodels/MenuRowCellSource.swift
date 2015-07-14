/**
 * MenuRowCellSource.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Implementation for the menu row cell source class
 */
class MenuRowCellSource: CellSource {
   /**
    * member vars
    */
    private var menurow_title: String = ""
    private var segue: String = ""
    private var icon: String = ""
    
    /**
     * custom init
     */
    init(title: String, segue: String, icon: String){
        super.init()
        self.menurow_title = title
        self.segue = segue
        self.icon = icon
    }
    
    /**
     * setters and getters
     */
    func getMenuRowTitle() -> String{
        return self.menurow_title
    }
    
    func getSegue() -> String {
        return self.segue
    }
    
    func getIcon() -> String {
        return self.icon
    }
}
