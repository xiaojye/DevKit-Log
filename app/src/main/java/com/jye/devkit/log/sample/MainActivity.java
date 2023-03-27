package com.jye.devkit.log.sample;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jye.devkit.log.DkLog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DkLog.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_print_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DkLog.d(TAG, "onClick: R.id.btn_print_time, time=" + TIME_FORMAT.format(System.currentTimeMillis()));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DkLog.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        DkLog.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        DkLog.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        DkLog.d(TAG, "onStop");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        DkLog.d(TAG, "onSaveInstanceState, outState=" + outState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DkLog.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DkLog.d(TAG, "onDestroy");
    }
}