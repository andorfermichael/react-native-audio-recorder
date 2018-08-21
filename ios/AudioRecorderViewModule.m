//
//  AudioRecorderViewModule.m
//  reactnativeaudiorecorder
//
//  Created by Michael Andorfer on 24.07.18.
//  Copyright © 2018 Audvice GmbH. All rights reserved.
//

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#elif __has_include(“RCTBridgeModule.h”)
#import “RCTBridgeModule.h”
#else
#import “React/RCTBridgeModule.h” // Required when used as a Pod in a Swift project
#endif

#import <React/RCTViewManager.h>

// Represents the bridge which enables access to AudioRecorderView(Manager) in React Native
@interface RCT_EXTERN_MODULE(AudioRecorderViewManager, RCTViewManager)
  // General
  RCT_EXTERN_METHOD(setDimensions: (double)width dimHeight:(double)height);

  // Recording
  RCT_EXTERN_METHOD(setupRecorder: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject);
  RCT_EXTERN_METHOD(startRecording: (double)startTimeInMS file:(NSString)fileUrl resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject);
  RCT_EXTERN_METHOD(stopRecording: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject);
@end
