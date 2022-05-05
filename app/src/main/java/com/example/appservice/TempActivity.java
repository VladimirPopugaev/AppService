package com.example.appservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        String stringExtra = getIntent().getStringExtra(CountService.TIME);

        TextView mTvTime = findViewById(R.id.tv_time);
        mTvTime.setText("Time is " + stringExtra);
    }
}