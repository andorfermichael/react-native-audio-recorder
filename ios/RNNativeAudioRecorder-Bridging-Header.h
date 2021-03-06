//
//  RNNativeAudioRecorder-Bridging-Header.h
//  reactnativeaudiorecorder
//
//  Created by Michael Andorfer on 04.07.18.
//  Copyright © 2018 Audvice GmbH. All rights reserved.
//

//
//  Use this file to import your target's public headers that you would like to expose to Swift.
//

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#elif __has_include(“RCTBridgeModule.h”)
#import “RCTBridgeModule.h”
#else
#import “React/RCTBridgeModule.h” // Required when used as a Pod in a Swift project
#endif

#import "React/RCTBridge.h"
#import "React/RCTEventDispatcher.h"

#import <React/RCTViewManager.h>

#import "AudioPlayerHelper.h"
