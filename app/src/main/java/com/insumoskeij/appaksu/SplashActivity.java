package com.insumoskeij.appaksu;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        LogoLaucher logoLaucher = new LogoLaucher();
        logoLaucher.start();

    }

    private class LogoLaucher extends Thread{
        public void run(){
            try {
                sleep(300);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
