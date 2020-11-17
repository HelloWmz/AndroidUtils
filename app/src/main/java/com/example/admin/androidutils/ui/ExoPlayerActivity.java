package com.example.admin.androidutils.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.admin.androidutils.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

/**
 * Created by Admin on 2018/6/20.
 */

public class ExoPlayerActivity extends AppCompatActivity implements AdaptiveMediaSourceEventListener {
  private Context mContext;
  private SimpleExoPlayerView simpleExoPlayerView;

  private SimpleExoPlayer simpleExoPlayer;
  private DataSource.Factory dataSourceFactory;
  private Handler mainHandler = new Handler();
  public MediaSource mMediaSource;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exoplayer);
    mContext = this;
    simpleExoPlayerView =  findViewById(R.id.player_view);

    init();
  }

  private void init() {
    // 1 create a default TrackSelector
    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    TrackSelection.Factory videoTrackSelectionFactory =
         new AdaptiveTrackSelection.Factory(bandwidthMeter);
    DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
    // 2. Create the player
    simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

    // bind the player to the view
    simpleExoPlayerView.setPlayer(simpleExoPlayer);

    // 默认带宽测量
    DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
    dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, getPackageName()), defaultBandwidthMeter);
    Uri uri = Uri.parse("http://devimages.apple.com/samplecode/adDemo/ad.m3u8");


    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
    //  mMediaSource= new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
    mMediaSource = new HlsMediaSource(uri, dataSourceFactory, mainHandler, this);
    // 准备播放
    simpleExoPlayer.prepare(mMediaSource);
    // 自动播放
    simpleExoPlayer.setPlayWhenReady(true);

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (simpleExoPlayer != null) {
      simpleExoPlayer.release();
    }
  }

  @Override
  public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs) {
    Log.e("test", "开始");

  }

  @Override
  public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
    Log.e("test", "完成");

  }

  @Override
  public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

  }

  @Override
  public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, IOException error, boolean wasCanceled) {
    Log.e("test", error.toString());
  }

  @Override
  public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {

  }

  @Override
  public void onDownstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaTimeMs) {

  }
}
