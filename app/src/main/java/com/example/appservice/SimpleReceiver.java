package com.example.appservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class SimpleReceiver extends BroadcastReceiver {

    public static final String SIMPLE_ACTION = "com.example.appservice.SIMPLE_ACTION";

    private WeakReference<TextView> mTextViewWeakReference;

    public SimpleReceiver(TextView textView) {
        this.mTextViewWeakReference = new WeakReference<>(textView);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        long time = intent.getLongExtra(CountService.TIME,0l);
        Toast.makeText(context, "Current time is " + time,Toast.LENGTH_SHORT).show();

        TextView textView = mTextViewWeakReference.get();
        StringBuilder builder = new StringBuilder(textView.getText().toString());
        builder.append(time).append("\n");
        textView.setText(builder.toString());

//        Intent launchActivityIntent = new Intent(context, TempActivity.class);
//        launchActivityIntent.putExtra(CountService.TIME, time);
//        context.startActivity(launchActivityIntent);
    }
}
