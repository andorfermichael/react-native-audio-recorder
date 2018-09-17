package com.reactlibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

// The audio recording engine wrapper
public class AudioRecording {
  // The class tag for identification
  private static final String TAG = "AudioRecording";

  // The destination file instance
  private File file;

  // The audio recording engine event listener
  private OnAudioRecordListener onAudioRecordListener;

  // The error codes
  private static final int IO_ERROR = 1;
  private static final int RECORDER_ERROR = 2;
  private static final int FILE_NULL = 3;

  // The thread which handles the recording
  private Thread recordingThread;

  // The interface for the event listener
  public interface OnAudioRecordListener {
    void onRecordFinished();
    void onError(int errorCode);
    void onRecordingStarted();
  }

  // The constructor
  AudioRecording() {}

  // Setter method for audio record event listener
  public void setOnAudioRecordListener(OnAudioRecordListener onAudioRecordListener) {
    this.onAudioRecordListener = onAudioRecordListener;
  }

  // Setter method for destination file instance
  public void setFile(String filePath) {
    this.file = new File(filePath);
  }

  // Initiates the recording by starting a thread
  public synchronized void startRecording() {
    // Destination file must not be null
    if(file == null) {
      onAudioRecordListener.onError(FILE_NULL);
      return;
    }

    try {
      // If recording thread exists, meaning recording is still running
      // stop recording first
      if(recordingThread != null) {
        stopRecording(true);
      }

      // Instantiate a new recording thread passing in the destination file wrapped in a stream, plus an event listener instance
      recordingThread = new Thread(new AudioRecordThread(outputStream(file), new AudioRecordThread.OnRecorderFailedListener() {
        // Recording started
        @Override
        public void onRecorderStarted() {
          onAudioRecordListener.onRecordingStarted();
        }

        // Recording failed
        @Override
        public void onRecorderFailed() {
          onAudioRecordListener.onError(RECORDER_ERROR);
          stopRecording(true);
        }
      }));

      // Define the name of the thread
      recordingThread.setName("AudioRecordingThread");

      // Start the recording thread
      recordingThread.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // Stops the recording by stopping the thread
  public synchronized void stopRecording(Boolean cancel){
    System.out.println("Recording stopped");

    // Check if the recording thread exists
    if(recordingThread != null){

      // Stop it and clear it
      recordingThread.interrupt();
      recordingThread = null;

      // If file has no data, throw error
      if (file.length() == 0L) {
        onAudioRecordListener.onError(IO_ERROR);
        return;
      }

      // If cancel flag is false
      // the recording was finished
      if (!cancel) {
        onAudioRecordListener.onRecordFinished();
      } else {
        // Otherwise it was forced to cancel so delete the file
        deleteFile();
      }
    }
  }

  // Deletes the created file
  private void deleteFile() {
    // Check if file can be deleted as it exists physically and virtually
    if (file != null && file.exists())
      System.out.println(String.format("deleting file success %b ", file.delete()));
  }

  // Instantiates and returns a output stream for a given file instance
  private OutputStream outputStream(File file) {
    // Check if file exists
    if (file == null) {
      throw new RuntimeException("file is null !");
    }

    // Create a output stream
    OutputStream outputStream;


    try {
      // Create a file output stream for the given (destination) file instance
      outputStream = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(
              "could not build OutputStream from" + " this file " + file.getName(), e);
    }
    return outputStream;
  }
}