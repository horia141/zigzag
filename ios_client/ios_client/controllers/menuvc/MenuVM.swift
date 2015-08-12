/**
 * MenuVM.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * This is the view model of the Menu VC
 */
class MenuVM: NSObject, UITableViewDataSource {
   
    /**
     * member vars
     */
    private var menu_sources: [CellSource] = []
    
    /**
     * init function
     */
    override init(){
        super.init()
        
        // populate menus array
        menu_sources.append(MenuHeaderCellSource(title: "PicJar"))
        menu_sources.append(MenuRowCellSource(title: "Home", segue: "MenuToMainSegue", icon: "1.home"))
        menu_sources.append(MenuRowCellSource(title: "My prefferences", segue: "MenuToPrefSegue", icon: "2.pref"))
        menu_sources.append(MenuHeaderCellSource(title: "Legal stuff"))
        menu_sources.append(MenuRowCellSource(title: "Terms and conditions", segue: "MenuToTermsSegue", icon: "3.terms"))
        menu_sources.append(MenuRowCellSource(title: "About", segue: "MenuToAboutSegue", icon: "4.about"))
        menu_sources.append(MenuHeaderCellSource(title: "Tell your friends"))
        menu_sources.append(MenuRowCellSource(title: "Share app", segue: "MenuToShareSegue", icon: "5.share"))
    }
    
    func getCellSourceAtIndexPath(indexPath: NSIndexPath) -> CellSource{
        return menu_sources[indexPath.row]
    }
    
    /**
     * table view data source delegate functions
     */
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return menu_sources.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var object = menu_sources[indexPath.row] as CellSource
        return object.tableView(tableView, representationAsCellForIndexPath: indexPath)
    }
}
