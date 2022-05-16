//package com.smproject.yourcityradio;
//
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.Binder;
//import android.os.IBinder;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//import java.io.IOException;
//
//public class PlayerService extends Service {
//
//    MediaPlayer mediaPlayer = new MediaPlayer();
//
//    public static final String CHANNEL_ID = "channel1";
//
//    private final IBinder mBinder = new MyBinder();
//
//    public class MyBinder extends Binder {
//        public PlayerService getService() {
//            return PlayerService.this;
//        }
//    }
//
//    public PlayerService() {
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        playStream(intent.getStringExtra("url"));
//
//        return START_NOT_STICKY;
//
//
//
////        if(intent.getStringExtra("url") != null){
////            playStream(intent.getStringExtra("url"));
////            showNotification();
////        }
//
////        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
////            Log.i("infooo", "start foreground service");
////            showNotification();
////        } else if(intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
////            Log.i("infooo", "play pressed");
////        } else if(intent.getAction().equals(Constants.ACTION.STOPTFOREGROUND_ACTION)) {
////            Log.i("infooo", "stop foreground received");
////            stopForeground(true);
////            stopSelf();
////        }
//
////        return START_REDELIVER_INTENT;
//    }
//
////    private void showNotification() {
////
////        Intent notificationIntent = new Intent(this, PlayerService.class);
////        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
////        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        PendingIntent pendingIntent = PendingIntent.getActivity(
////                this,
////                0,
////                notificationIntent,
////                0
////        );
////
////        Intent playIntent = new Intent(this, PlayerService.class);
////        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
////        PendingIntent pplayIntent = PendingIntent.getActivity(
////                this,
////                0,
////                playIntent,
////                0
////        );
////
//////        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_music_note);
////        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
////                .setContentTitle("Your City Radio")
////                .setTicker("Plaing music")
//////                .setContentText("Song Name")
//////                .setSmallIcon(R.drawable.ic_skip_previous_black_24dp)
//////                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
////                .setContentIntent(pendingIntent)
////                .setOngoing(true)
//////                .addAction(R.drawable.ic_play_arrow_black_24dp, "Play", pplayIntent)
////                .build();
////
//////        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
////    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    public void playStream(String url) {
//        if (mediaPlayer != null) {
//            try {
//                mediaPlayer.stop();
//            } catch (Exception e) {
//
//            }
//            mediaPlayer = null;
//        }
//
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        Log.i("OLOURL", url);
//
//        try {
//            mediaPlayer.setDataSource(url);
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    mediaPlayer.start();
//                }
//            });
//
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                   flipPlayPauseButton(false);
//                }
//            });
//
//            mediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void pausePlayer() {
//        try {
//            mediaPlayer.pause();
//            flipPlayPauseButton(false);
//        } catch (Exception e) {
//            Log.d("EXCEPTION", "Failed to pause media player");
//        }
//    }
//
//    public void flipPlayPauseButton(boolean isPlaying) {
//        Intent intent = new Intent("changePlayButton");
//        intent.putExtra("isPlaying", isPlaying);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//    }
//
//    public void playPlayer() {
//        try {
//            mediaPlayer.start();
//            MainActivity.flipPlayPauseButton(true);
//        } catch (Exception e) {
//            Log.d("EXCEPTION", "Failed to start media player");
//        }
//    }
//
//    public void togglePlayer() {
//        try {
//            if (mediaPlayer.isPlaying()) {
//                pausePlayer();
//            } else {
//                playPlayer();
//            }
//        } catch (Exception e) {
//            Log.d("EXCEPTION", "Failed to toggle media player");
//        }
//    }
//}