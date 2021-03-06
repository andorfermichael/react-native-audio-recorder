//
//  AudioRecorderViewManager.swift
//  reactnativeaudiorecorder
//
//  Created by Michael Andorfer on 07.08.18.
//  Copyright © 2018 Audvice GmbH. All rights reserved.
//

import Foundation
import UIKit

import AudioKit
import AudioKitUI

// Represents the AudioPlayerViewManager which manages our AudioPlayerView Module
@objc(AudioPlayerViewManager)
class AudioPlayerViewManager : RCTViewManager {
  // The native ui view
  private var currentView: AudioPlayerView?
  
  // The promise response
  private var jsonArray: JSON = [
    "success": false,
    "error": "",
    "value": ["fileName": ""]
  ]
  
  // Instantiates the view
  override func view() -> AudioPlayerView {
    // Turn off application exit on error of audio file reading in EZAudioUtilities (AudioKit)
    AudioPlayerHelper.setShouldExitOnCheckResultFail()
  
    let newView = AudioPlayerView()
    self.currentView = newView
    self.currentView?.setupWaveform()
    return newView
  }
  
  // Tells React Native to use Main Thread
  override class func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  // Received properties from React Native and sets them on the view
  @objc public func passProperties(_ backgroundColor:String, propLineColor lineColor:String, pixels pixelsPerSecond:Double) {
    if (backgroundColor != "") {
      let transparentColor = UIColor(white: 1, alpha: 0.0)
      self.currentView?.bgColor = ColorHelper.hexStringToUIColor(hex: backgroundColor, fallback: transparentColor)
    }
    
    if (lineColor != "") {
      let brandColor = UIColor(red: 124.0 / 255.0, green: 219.0 / 255.0, blue: 213.0 / 255.0, alpha: 1.0)
      self.currentView?.lineColor = ColorHelper.hexStringToUIColor(hex: lineColor, fallback: brandColor)
    }
    
    self.currentView?.pixelsPerSecond = pixelsPerSecond
    
    DispatchQueue.main.async {
      self.currentView?.layoutSubviews()
    }
  }
  
  // Sets the dimensions of the AudioRecorderView to the component dimensions received from React Native
  @objc public func setDimensions(_ width:Double, dimHeight height:Double) {
    self.currentView?.componentWidth = width
    self.currentView?.componentHeight = height
    
    DispatchQueue.main.async {
      self.currentView?.layoutSubviews()
    }
  }
  
  // Enables to re-run a method x-times or until a condition is fullfilled
  func retry(_ attempts: Int, task: @escaping (_ onSuccess: @escaping (Bool) -> Void, _ onError: @escaping (Error) -> Void) -> Void, onSuccess: @escaping (Bool) -> Void, onError: @escaping (Error) -> Void) {
    // Try
    task({(success) in
      // Condition fullfilled
      onSuccess(success)
    }) {(error) in
      // Error
      print("Error retry left \(attempts)")
      
      // Attempts left
      if attempts > 1 {
        // Wait x seconds before retrying
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
          self.retry(attempts - 1, task: task, onSuccess: onSuccess, onError: onError)
        }
      } else {
        onError(error)
      }
    }
  }
  
  // Render a waveform from audio file data
  @objc public func renderByFile(_ fileName:String, resolver resolve:@escaping RCTPromiseResolveBlock, rejecter reject:@escaping RCTPromiseRejectBlock) {
    
    // Build the file url
    let fileUrl = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)[0] + "/" + fileName
    
    // Remove timestamp from file url
    var cleanUrl: String = ""
    if let index = fileUrl.range(of: "?")?.lowerBound {
      cleanUrl = String(fileUrl[..<index])
    }
    
    // Try X-times to create waveform from audio file, this retry is necessary since writing the file
    // is not always finished when file access to read is done
    self.retry(10, task: { success, onError in
      self.currentView?.updateWaveformWithData(fileUrl: URL(string: cleanUrl)!, onSuccess: success, onError: onError) },
      onSuccess: { success in
        self.jsonArray["success"] = true
        resolve(self.jsonArray.rawString());
      },
      onError: { error in
        self.jsonArray["success"] = false
        self.jsonArray["error"].stringValue = error.localizedDescription
        reject("Error", self.jsonArray.rawString(), error)
      })
  }
}
