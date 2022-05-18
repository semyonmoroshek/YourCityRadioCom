package com.smproject.yourcityradio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static ImageView playPouseButton;
    PlayerService mBoundService;
    boolean mServiseBound = false;
    TextView homeLink;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.MyBinder myBinder = (PlayerService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiseBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiseBound = false;
        }
    };

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isPlaying = intent.getBooleanExtra("isPlaying", false);
            flipPlayPauseButton(isPlaying);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://c34.radioboss.fm:18234/stream";

        playPouseButton = findViewById(R.id.btn_play);
        playPouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServiseBound)
                    mBoundService.togglePlayer();
            }
        });

        homeLink = findViewById(R.id.txt_homeLink);
        homeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yourcityradio.com/"));
                startActivity(browserIntent);
            }
        });

        startStreamingService(url);
    }

    private void startStreamingService(String url) {
        Intent i = new Intent(this, PlayerService.class);
        i.putExtra("url", url);
        i.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(i);
        bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mServiseBound) {
//            unbindService(mServiceConnection);
//            mServiseBound = false;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("changePlayButton"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager
                .getInstance(this)
                .unregisterReceiver(mMessageReceiver);
    }

    public static void flipPlayPauseButton(boolean isPlaying) {
        if (isPlaying) {
            playPouseButton.setImageResource(R.drawable.ic_pause_button);
        } else {
            playPouseButton.setImageResource(R.drawable.ic_play_button_dark);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.onStop();
        if (mServiseBound) {
            unbindService(mServiceConnection);
            mServiseBound = false;
        }
    }
}