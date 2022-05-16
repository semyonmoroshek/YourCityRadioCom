package com.smproject.yourcityradio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    static ImageView playPouseButton;
//    static TextView title;
//
//    NotificationManager notificationManager;
//    boolean mServiceBound = false;
//    PlayerService mBoundService;
//
//
//    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            boolean isPlaying = intent.getBooleanExtra("isPlaying", false);
//            flipPlayPauseButton(isPlaying);
//        }
//    };
//
//    private ServiceConnection mServiceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder service) {
//            PlayerService.MyBinder myBinder = (PlayerService.MyBinder) service;
//            mBoundService = myBinder.getService();
//            mServiceBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            mServiceBound = false;
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPouseButton = findViewById(R.id.btn_play);
        playPouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.player.togglePlayer();
            }
        });


        String url = "https://c34.radioboss.fm:18234/stream";

        if (Player.player == null) {
            new Player();
        }

        Player.player.playStream(url);

    }

    public static void flipPlayPauseButton(boolean isPlaying) {
        if (isPlaying) {
            playPouseButton.setImageResource(R.drawable.ic_pause_button);
        } else {
            playPouseButton.setImageResource(R.drawable.ic_play_button);

        }
    }

//    private void startStreamingService() {
//        Intent intent = new Intent(this, PlayerService.class);
//        intent.putExtra("url", url);
////        intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
//        startService(intent);
////        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("changePlayButton"));
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
//    }
//
//
//    public static void flipPlayPauseButton(boolean isPlaying) {
//        if (isPlaying) {
//            play.setImageResource(R.drawable.ic_pause_button);
//        } else {
//            play.setImageResource(R.drawable.ic_play_button);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mServiceBound) {
//            unbindService(mServiceConnection);
//            mServiceBound = false;
//        }
//    }
}