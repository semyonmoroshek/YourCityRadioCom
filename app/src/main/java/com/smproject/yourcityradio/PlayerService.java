package com.smproject.yourcityradio;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

//            createNotification(getApplicationContext(), 1);
//            showNotification(getApplicationContext());
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

    public static void createNotification(Context context, int playbutton) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.t1);


            Intent intentPrevious = new Intent(context, PlayerService.class)
                    .setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent pendingIntentPrevious = PendingIntent.getBroadcast(
                    context,
                    0,
                    intentPrevious,
                    PendingIntent.FLAG_IMMUTABLE |PendingIntent.FLAG_UPDATE_CURRENT);

            int drw_previous = R.drawable.ic_skip_previous_black_24dp;


            Intent intentPlay = new Intent(context, PlayerService.class)
                    .setAction(Constants.ACTION.MAIN_ACTION);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(
                    context,
                    0,
                    intentPlay,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

                Intent intentNext = new Intent(context, PlayerService.class)
                        .setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pendingIntentNext = PendingIntent.getBroadcast(
                    context,
                    0,
                        intentNext,
                    PendingIntent.FLAG_IMMUTABLE |PendingIntent.FLAG_UPDATE_CURRENT);
               int  drw_next = R.drawable.ic_skip_next_black_24dp;

            String CHANNEL_ID = "channel1";

            //create notification
            Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_music_note)
                    .setContentTitle("track.getTitle()")
                    .setContentText("track.getArtist()")
                    .setLargeIcon(icon)
                    .setOnlyAlertOnce(true)//show notification for only first time
                    .setShowWhen(false)
                    .addAction(R.drawable.ic_play_button_dark, "Previous", pendingIntentPrevious)
                    .addAction(playbutton, "Play", pendingIntentPlay)
                    .addAction(R.drawable.ic_play_button_dark, "Next", pendingIntentNext)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Much longer text that cannot fit one line..."))
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            notificationManagerCompat.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);

        }
    }

    private void showMyNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_music_note)
                .setContentTitle("Your City Radio")
                .setContentText("Playing")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Some text"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, builder.build());


    }

    private void showNotification(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Intent notificationIntent = new Intent(context, PlayerService.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            Intent prevIntent = new Intent(context, PlayerService.class);
            notificationIntent.setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent pprevIntent = PendingIntent.getActivity(
                    context,
                    0,
                    prevIntent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            Intent playIntent = new Intent(context, PlayerService.class);
            notificationIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getActivity(
                    context,
                    0,
                    playIntent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            Intent nextIntent = new Intent(context, PlayerService.class);
            notificationIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getActivity(
                    context,
                    0,
                    nextIntent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.t1);

            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle("Music player")
                    .setTicker("Playing music")
                    .setContentText("My song")
                    .setSmallIcon(R.drawable.ic_music_note)
//                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
//                .addAction(android.R.drawable.ic_media_previous, "Previous", pprevIntent)
                    .addAction(android.R.drawable.ic_media_play, "Play", pplayIntent)
//                .addAction(android.R.drawable.ic_media_next, "Next", pnextIntent)
                    .build();

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
        }

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