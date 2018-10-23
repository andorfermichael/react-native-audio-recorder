import { NativeModules } from 'react-native';

const { AudioRecorderViewManager } = NativeModules;

export default {
  setDimensions (width, height) {
    return AudioRecorderViewManager.setDimensions(width, height);
  },

  passProperties (backgroundColor, lineColor) {
    return AudioRecorderViewManager.passProperties(backgroundColor, lineColor);
  },

  setupRecorder () {
    return AudioRecorderViewManager.setupRecorder();
  },

  startRecording(fileUrl, startTimeInMs) {
    return AudioRecorderViewManager.startRecording(fileUrl, startTimeInMs);
  },

  stopRecording() {
    return AudioRecorderViewManager.stopRecording();
  }
}
