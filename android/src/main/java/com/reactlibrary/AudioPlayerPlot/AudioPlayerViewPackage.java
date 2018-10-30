//
//  AudioPlayerViewPackage.java
//  reactnativeaudiorecorder
//
//  Created by Michael Andorfer on 20.09.18.
//  Copyright © 2018 Audvice GmbH. All rights reserved.
//

package com.reactlibrary.AudioPlayerPlot;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Represents the bridge which enables access to AudioRecorderView(Manager) in React Native
public class AudioPlayerViewPackage implements ReactPackage {
  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    return Arrays.<NativeModule>asList(new AudioPlayerViewModule(reactContext));
  }

  // Deprecated from RN 0.47
  public List<Object> createJSModules() {
    return Collections.emptyList();
  }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Arrays.<ViewManager> asList(new AudioPlayerViewManager());
  }
}