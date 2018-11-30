package com.insumoskeij.appaksu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

public class NoConnectActivity extends AppCompatActivity {
    Button btnReintentar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connect);

        btnReintentar = findViewById(R.id.btnReintentar);
        //request = Volley.newRequestQueue(MainActivity.this);


        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NoConnectActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}