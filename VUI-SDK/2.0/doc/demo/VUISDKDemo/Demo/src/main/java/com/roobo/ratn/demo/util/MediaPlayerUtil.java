package com.roobo.ratn.demo.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;

import java.io.IOException;

/**
 * Created by chengyijun on 2018/9/15.
 */

public class MediaPlayerUtil {
    private static MediaPlayer mediaPlayer;

    static {
        mediaPlayer = new MediaPlayer();
    }

    public static void playByUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
}
