/**
 * TitleCellSource.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Class that encompasses data for the title cell
 */
class TitleCellSource: CellSource {
    /**
     * private vars
     */
    private var title: String = ""
    private var source: String = ""
    private var post_date: String = ""
    
    /**
     * init
     */
    init(title: String, source: String, postDate: String) {
        super.init()
        self.title = title
        self.source = source
        self.post_date = postDate
    }
    
    /**
     * getters
     */
    func getTitle() -> String{
        return self.title
    }
    
    func getSource() -> String{
        return self.source
    }
    
    func getPostDate() -> String{
        return self.post_date
    }
}
