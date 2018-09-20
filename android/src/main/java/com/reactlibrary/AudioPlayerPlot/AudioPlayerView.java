package com.reactlibrary.AudioPlayerPlot;

import android.content.Context;
import android.widget.RelativeLayout;

import com.reactlibrary.R;

public class AudioPlayerView extends RelativeLayout {
  private Context context;

  // The constructor
  public AudioPlayerView(Context context) {
    super(context);
    this.context = context;
    this.init();
  }

  // Initialize the waveform
  public void init() {
    // Apply layout from xml
    inflate(context, R.layout.audio_recorder_view, this);
  }
}