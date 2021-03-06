package com.smproject.yourcityradio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class Player {

    MediaPlayer mediaPlayer = new MediaPlayer();
    public static Player player;
    String url = "";

    public Player() {
        this.player = this;
    }

    public void playStream(String url) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch (Exception e) {

            }
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try{
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
