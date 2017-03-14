package cn.ucai.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.fulicenter.R;

/**
 * Created by liuning on 2017/3/14.
 */

public class SplashActivity extends AppCompatActivity {
    int time = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            }
        }, time);
    }
}
