//
//  Use this file to import your target's public headers that you would like to expose to Swift.
//

// Enables AudioRecorderViewController Swift class to access our Objective-C AudioRecorderBridge class
#import "AudioRecorderBridge.h"
#import "AudioRecorderUIManager.h"

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#elif __has_include(“RCTBridgeModule.h”)
#import “RCTBridgeModule.h”
#else
#import “React/RCTBridgeModule.h” // Required when used as a Pod in a Swift project
#endif

#import "React/RCTBridge.h"
#import "React/RCTEventDispatcher.h"
