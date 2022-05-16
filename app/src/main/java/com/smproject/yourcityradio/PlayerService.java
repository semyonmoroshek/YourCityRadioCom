package com.smproject.yourcityradio;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;

public class PlayerService extends Service {

    MediaPlayer mediaPlayer = new MediaPlayer();
    private final IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        PlayerService getService() {
            return PlayerService.this;
        }
    }

    public PlayerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getStringExtra("url") != null) {
            playStream(intent.getStringExtra("url"));
        }
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i("infoo", "Start foreground service");
//            showNotification();
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i("infoo", "Prev pressed");
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i("infoo", "play pressed");
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i("infoo", "Next pressed");
        } else if (intent.getAction().equals(Constants.ACTION.STOPTFOREGROUND_ACTION)) {
            Log.i("infoo", "Stop foreground service");
            stopForeground(true);
            stopSelf();
        }

        return START_REDELIVER_INTENT;
    }

    private void showNotification() {
        Intent notificationIntent = new Intent(this, PlayerService.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Intent prevIntent = new Intent(this, PlayerService.class);
        notificationIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent pprevIntent = PendingIntent.getActivity(
                this,
                0,
                prevIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(this, PlayerService.class);
        notificationIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getActivity(
                this,
                0,
                playIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, PlayerService.class);
        notificationIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getActivity(
                this,
                0,
                nextIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.t1);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Music player")
                .setTicker("Playing music")
                .setContentText("My song")
                .setSmallIcon(R.drawable.ic_music_note)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous, "Previous", pprevIntent)
                .addAction(android.R.drawable.ic_media_play, "Play", pplayIntent)
                .addAction(android.R.drawable.ic_media_next, "Next", pnextIntent)
                .build();

        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);


    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    playPlayer();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    flipPlayPauseButton(false);
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pausePlayer() {
        try {
            mediaPlayer.pause();
            flipPlayPauseButton(false);
        } catch (Exception e) {
            Log.d("EXCEPTION", "failed to pause media player");
        }
    }

    public void playPlayer() {
        try {
            mediaPlayer.start();
            flipPlayPauseButton(true);
        } catch (Exception e) {
            Log.d("EXCEPTION", "failed to start media player");
        }
    }

    public void togglePlayer() {
        try {
            if (mediaPlayer.isPlaying()) {
                pausePlayer();
            } else {
                playPlayer();
            }
        } catch (Exception e) {
            Log.d("Exception", "failed to toggle media player");
        }
    }

    public void flipPlayPauseButton(boolean isPlaying) {

        Intent intent = new Intent("changePlayButton");
        intent.putExtra("isPlaying", isPlaying);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}