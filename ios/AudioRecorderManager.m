//
//  AudioRecorderManager.m
//  reactnativeaudiorecorder
//
//  Created by Michael Andorfer on 04.07.18.
//  Copyright © 2018 Crowdio. All rights reserved.
//

// import RCTBridge
#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#elif __has_include(“RCTBridge.h”)
#import “RCTBridge.h”
#else
#import “React/RCTBridge.h” // Required when used as a Pod in a Swift project
#endif

// import RCTEventDispatcher
#if __has_include(<React/RCTEventDispatcher.h>)
#import <React/RCTEventDispatcher.h>
#elif __has_include(“RCTEventDispatcher.h”)
#import “RCTEventDispatcher.h”
#else
#import “React/RCTEventDispatcher.h” // Required when used as a Pod in a Swift project
#endif


// #import "AudioRecorderManager.h"
// #import "AudioRecorderUI.h"
// #import "AudioRecorderViewController.h"

//#import "reactnativeaudiorecorder-Swift.h"
#import "AudioRecorderBridge.h"
// #import "ViewController.h"

// @implementation AudioRecorderManager
@implementation AudioRecorderBridge
@synthesize bridge = _bridge;

RCT_EXPORT_MODULE()

//- (UIView *)view
//{
//  AudioRecorderUI * myAudioRecorderUI = [[AudioRecorderUI alloc] init];
//  return myAudioRecorderUI;
//}

- (UIView *)view
{
  return [[UIView alloc] init];
}

// Export methods to a native module
// https://facebook.github.io/react-native/docs/native-modules-ios.html
RCT_EXPORT_METHOD(exampleMethod)
{
  //ViewController* testViewController = [[ViewController alloc] init];
  //[testViewController mainButtonTouched];
  //[self emitMessageToRN:@"EXAMPLE_EVENT" :nil];
}

#pragma mark - Private methods

// Implement methods that you want to export to the native module
- (void) emitMessageToRN: (NSString *)eventName :(NSDictionary *)params {
  // The bridge eventDispatcher is used to send events from native to JS env
  // No documentation yet on DeviceEventEmitter: https://github.com/facebook/react-native/issues/2819
  [self.bridge.eventDispatcher sendAppEventWithName: eventName body: params];
}


@end
