package com.insumoskeij.appaksu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); /**** AGREGAR ****************************************************/

        Intent i = this.getIntent();
        //usarToolbar();

        // Obtener el coche con el identificador establecido en la actividad principal
        //itemDetallado = Producto.getItem(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));


        String rutaImgProd = i.getExtras().getString("IMG");
        String tprod = i.getExtras().getString("TPROD");
        String cprod = i.getExtras().getString("CPROD");


        imagenExtendida =  findViewById(R.id.imagen_extendida);
        PhotoView photoView = (PhotoView) imagenExtendida;

        //tProd = findViewById(R.id.tProd);
       // cProd = findViewById(R.id.cProd);

        //tProd.setText(tprod);
        //cProd.setText(cprod);

      //   System.out.println("qqq "+rutaImgProd);
        GlideApp.with(this).load(rutaImgProd)
                .placeholder(R.drawable.ic_base_camara)
                .into(imagenExtendida);


    }

/**************************************************************************************************/
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