package com.insumoskeij.appaksu;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;


public class ImagenDetalle extends AppCompatActivity {


    private ImageView imagenExtendida;
    private TextView tProd, cProd;

    //GET INTENT
    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_detalle);
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


        Intent i = this.getIntent();
        //usarToolbar();

        // Obtener el coche con el identificador establecido en la actividad principal
        //itemDetallado = Producto.getItem(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));


        String rutaImgProd = i.getExtras().getString("IMG");
        String tprod = i.getExtras().getString("TPROD");
        String cprod = i.getExtras().getString("CPROD");


        imagenExtendida =  findViewById(R.id.imagen_extendida);
        PhotoView photoView = (PhotoView) imagenExtendida;
        GlideApp.with(this).load(rutaImgProd)
                .placeholder(R.drawable.ic_base_camara)
                .into(imagenExtendida);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
/**************************************************************************************************/


}