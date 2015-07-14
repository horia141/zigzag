/**
 * CellSource.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * base cell source class
 */
class CellSource: NSObject {
    /**
     * function that returns the max cell width
     */
    func getMaxCellWidth() -> CGFloat {
        return UIScreen.mainScreen().bounds.width - 32
    }
}
