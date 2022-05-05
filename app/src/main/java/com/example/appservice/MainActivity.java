package com.example.appservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mBtnStartService;
    Button mBtnStopService;
    Button mBtnSendBroadcast;
    SimpleReceiver mSimpleReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStartService = findViewById(R.id.btn_start_service);
        mBtnStopService = findViewById(R.id.btn_stop_service);
        mBtnSendBroadcast = findViewById(R.id.btn_send_broadcast);

        mBtnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountService.class);
                startService(intent);
            }
        });

        mBtnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountService.class);
                stopService(intent);
            }
        });

        mBtnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast(new Intent(SimpleReceiver.SIMPLE_ACTION));
            }
        });

        mSimpleReceiver = new SimpleReceiver(findViewById(R.id.tv_time));
        intentFilter = new IntentFilter(SimpleReceiver.SIMPLE_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mSimpleReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mSimpleReceiver);
    }
}