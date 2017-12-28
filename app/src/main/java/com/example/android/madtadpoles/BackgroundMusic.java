package com.example.android.madtadpoles;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Michał Jura on 18.12.2017.
 */

public class BackgroundMusic extends Service implements MediaPlayer.OnErrorListener {

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    AudioManager mAudioManager;
    private int length = 0;

    public BackgroundMusic() {
    }

    public class ServiceBinder extends Binder {
        BackgroundMusic getService() {
            return BackgroundMusic.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mPlayer = MediaPlayer.create(this, R.raw.background);
        mPlayer.setOnErrorListener(this);

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100, 100);
        }


        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int extra) {

                onError(mPlayer, what, extra);
                return true;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.start();
        return START_STICKY;
    }

    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();

        }
    }

    public void resumeMusic() {
        if (!mPlayer.isPlaying()) {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void stopMusic() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }

    // =============== audiofokus===============================
    //DLA MICHALA
    // tu znalazlam -> https://github.com/udacity/ud839_Miwok/blob/lesson-four/app/src/main/java/com/example/android/miwok/ColorsActivity.java
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocus = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mPlayer.pause();
                mPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                onDestroy();
            }
        }
    };

    //===============================end AF=================================

}
