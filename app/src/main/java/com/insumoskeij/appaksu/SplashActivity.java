package com.insumoskeij.appaksu;


import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class SplashActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash);
//        getSupportActionBar().hide();
        //LogoLaucher logoLaucher = new LogoLaucher();
        //logoLaucher.start();

        videoView = findViewById(R.id.video);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.app_aksu;

        videoView.setVideoURI(Uri.parse(path));
        videoView.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogoLaucher logoLaucher = new LogoLaucher();
                logoLaucher.start();
            }
        }, 4000);//se debe colocar mínimo el tiempo de duración del video

    }

    private class LogoLaucher extends Thread{
        public void run(){

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}