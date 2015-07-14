/**
 * ConnectionManager.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/** 
 * This is the protocol other classes have to implement in order to have access
 * to network transfer throgh the ConnectionManager class
 */
protocol ConnectionManagerProtocol{
    func getData(var packet: NSData);
    func failedToGetData();
}

/**
 * This class manages data connections with the server
 */

class ConnectionManager: NSObject, NSURLConnectionDataDelegate {
    
    var data: NSMutableData! = nil
    var connection_delegate : ConnectionManagerProtocol! = nil
    
    func makeRequest(var urlString: NSString) {
        data = NSMutableData()
        var url = NSURL(string: urlString as String)
        var request = NSURLRequest(URL: url!)
        var connection = NSURLConnection(request: request, delegate: self, startImmediately: true)
    }
    
    func connection(connection: NSURLConnection, didReceiveResponse response: NSURLResponse) {
        println("did reveive data")
    }
    
    func connection(connection: NSURLConnection, didReceiveData data: NSData) {
        self.data.appendData(data)
    }
    
    func connectionDidFinishLoading(connection: NSURLConnection) {
        self.connection_delegate.getData(self.data);
    }
    
    func connection(connection: NSURLConnection, didFailWithError error: NSError) {
        self.connection_delegate.failedToGetData()
    }
}
