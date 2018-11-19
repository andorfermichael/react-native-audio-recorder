//
//  AudioRecorderModule.java
//  reactnativeaudiorecorder
//
//  Created by Michael Andorfer on 10.09.18.
//  Copyright © 2018 Audvice GmbH. All rights reserved.
//

package com.reactlibrary.AudioRecorder;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import com.facebook.react.bridge.*;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

// Represents the AudioRecorderViewManager which manages the AudioRecorderView
public class AudioRecorderModule extends ReactContextBaseJavaModule {
  // The class identifier
  public static final String TAG = "AudioRecorderModule";

  // The audio recording engine wrapper
  private AudioRecording audioRecording;

  // The promise response
  private WritableNativeMap jsonResponse = null;

  // The constructor
  AudioRecorderModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  // Defines the name under which the module/manager is accessible from React Native
  @Override
  public String getName() {
    // !!! This is not a wrong, don't change this,
    // this is necessary as in Android RN the module is the manager
    return "AudioRecorderViewManager";
  }

  // Pass properties from React Native to the waveform
  @ReactMethod
  public void passProperties(String backgroundColor, String lineColor) {
    // Send event for updating waveform with new parameters
    EventBus.getDefault().post(new DynamicWaveformEvent(3, backgroundColor, lineColor));
  }

  // Instantiates all the things needed for recording
  @ReactMethod
  public void setupRecorder(Promise promise) {
    Log.i(TAG, "Setup Recorder");

    // Send event for pausing the waveform
    EventBus.getDefault().post(new DynamicWaveformEvent(2, null, null));

    try {
      // Instantiate the audio recording engine
      this.audioRecording = new AudioRecording();

      // Create the promise response
      this.jsonResponse = new WritableNativeMap();
      this.jsonResponse.putBoolean("success", true);
      this.jsonResponse.putString("error", "");
      this.jsonResponse.putString("value", "");

      promise.resolve(this.jsonResponse);
    } catch (Exception e) {
      promise.reject("Error", e.getLocalizedMessage(), e);
    }
  }

  // Starts the recording of audio
  @ReactMethod
  private void startRecording(String fileName, Double startTimeInMs, Promise promise) {
    Log.i(TAG, "Start Recording");

    try {
      this.audioRecording.startRecording(fileName, startTimeInMs);

      // Send event for resuming waveform
      EventBus.getDefault().post(new DynamicWaveformEvent(1, null, null));

      // Create the promise response
      this.jsonResponse = new WritableNativeMap();
      this.jsonResponse.putBoolean("success", true);
      this.jsonResponse.putString("error", "");
      this.jsonResponse.putString("value", "");

      promise.resolve(jsonResponse);
    } catch (Exception e) {
      promise.reject("Error", e.getLocalizedMessage(), e);
    }
  }

  // Stops audio recording and stores the recorded data in a file
  @ReactMethod
  private void stopRecording(Promise promise) {
    try {
      File recordedFile = null;

      if(this.audioRecording != null){
        // Send event for pausing waveform
        EventBus.getDefault().post(new DynamicWaveformEvent(2, null, null));

        // Get the file location back from the audio recorder
        recordedFile = this.audioRecording.stopRecording();
      }

      // Read the file duration from the file meta data
      Uri uri = Uri.parse(recordedFile.getAbsolutePath());
      MediaMetadataRetriever mmr = new MediaMetadataRetriever();
      mmr.setDataSource(null,uri);
      String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

      // Retry while duration is 0 meaning file was not ready for reading
      while(Float.parseFloat(durationStr) == 0) {
        uri = Uri.parse(recordedFile.getAbsolutePath());
        mmr = new MediaMetadataRetriever();
        mmr.setDataSource(null, uri);
        durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
      }

      // Create the promise response
      this.jsonResponse = new WritableNativeMap();
      this.jsonResponse.putBoolean("success", true);
      this.jsonResponse.putString("error", "");

      WritableNativeMap metaDataArray = new WritableNativeMap();
      metaDataArray.putString("fileName", recordedFile.getName());
      metaDataArray.putString("fileDurationInMs", durationStr);

      this.jsonResponse.putMap("value", metaDataArray);

      promise.resolve(this.jsonResponse);
    } catch (Exception e) {
      promise.reject("Error", e.getLocalizedMessage(), e);
    }
  }
}