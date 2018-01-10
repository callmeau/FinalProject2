package com.example.lpf.finaldemo;

/**
 * Created by Victory on 2017/11/25.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY_MILLIS=2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        },SPLASH_DELAY_MILLIS);
    }
    private void goHome(){
        Intent intent = new Intent(MainActivity.this,Log_in_activity.class);
        MainActivity.this.startActivity(intent);
        MainActivity.this.finish();
    }
}
