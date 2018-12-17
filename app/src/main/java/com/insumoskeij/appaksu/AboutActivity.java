package com.insumoskeij.appaksu;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /******Cambio de icono de Fecha atras toolbar****/
        final Drawable upArrow = ResourcesCompat.getDrawable(
                getResources(),
                R.drawable.ic_arrow_back_black_24dp, //this is the <- arrow from android resources. Change this to the thing you want.
                null);
        assert upArrow != null;
        upArrow.setColorFilter(
                ContextCompat.getColor(
                        getApplicationContext(),
                        R.color.text_color // change this to the color you want (or remove the setColorFilter call)
                ),
                PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        /************fin cambio de icono****/

        TextView version = findViewById(R.id.version);
        version.setText("Versión " + BuildConfig.VERSION_NAME);

        TextView footer = findViewById(R.id.footer);
        //footer.setText("Desarrollado por KEIJ-TECH para Inversiones AKSU, C.A.");

        //footer.setText(Html.fromHtml("<strong>Desarrollado por <a href=\"http://www.keij-tech.com\" target=\"_blank\">KEIJ-TECH</a> para Inversiones AKSU, C.A.</strong>"));
        footer.setText(Html.fromHtml("<strong>Desarrollado por <a href=\"mailto:insumos.keij.ca@gmail.com\" target=\"_blank\">KEIJ-TECH</a> para Inversiones AKSU, C.A. J-31630694-1.</strong>"));
        footer.setMovementMethod(LinkMovementMethod.getInstance());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);

        TextView copy = findViewById(R.id.copy);
        copy.setText("\u00a9 "+fecha+". Todos los Derechos Reservados.");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
