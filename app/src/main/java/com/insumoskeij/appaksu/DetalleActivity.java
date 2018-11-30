package com.insumoskeij.appaksu;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DetalleActivity extends AppCompatActivity {

    TextView txtMarca, txtModelo, txtAnno,
            txtTipoProd, txtCodProd, txtCodProd_2,
            txtOMarcas, tOMarcas,
            tCodBarra, txtDetalleCodBarra,
            txtDetalleMedida, tDetalleMedida,
            txtDetallePeso, tDetallePeso,
            txtDetalleVeh, tDetalleVeh,
            tbMarca, tbModelo, tbanno, tbMotor,motor, tbHP,hp,
            tbOMarca ,tbOCod;
    View lineaCodBarra,lineaMedida,lineaPeso;
    ImageView imgProd;
    RequestQueue request;
    String rutaImgProd, tprod, cprod;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        imgProd = findViewById(R.id.imgProd);
        //txtTipoProd = findViewById(R.id.txtTipoProd);
        txtCodProd_2 = findViewById(R.id.txtCodProd_D_2);
        tOMarcas = findViewById(R.id.tOMarcas);
        txtDetalleCodBarra = findViewById(R.id.txtDetallesCodBarra);
        tCodBarra = findViewById(R.id.tCodBarra);
        lineaCodBarra = findViewById(R.id.lineaCodBarra);
        txtDetalleMedida = findViewById(R.id.txtDetallesMedida);
        tDetalleMedida = findViewById(R.id.tMedida);
        lineaMedida = findViewById(R.id.lineaMedida);
        txtDetallePeso = findViewById(R.id.txtDetallesPeso);
        tDetallePeso = findViewById(R.id.tPeso);
        lineaPeso = findViewById(R.id.lineaPeso);

        tDetalleVeh = findViewById(R.id.tDetallesVeh);
        tbMarca = findViewById(R.id.tbMarca);
        tbModelo = findViewById(R.id.tbModelo);
        tbanno = findViewById(R.id.tbAnno);
        tbMotor = findViewById(R.id.tbMotor);
        motor = findViewById(R.id.motor);
        tbHP = findViewById(R.id.tbHP);
        hp = findViewById(R.id.HP);

        tbOMarca = findViewById(R.id.tbOMarca);
        tbOCod = findViewById(R.id.tbOCod);


        request = Volley.newRequestQueue(getApplicationContext());


        //GET INTENT
        Intent i = this.getIntent();

        //RECEIVE DATA

        String detalle = i.getExtras().getString("DETALLE");
        String detalleCodBarra = i.getExtras().getString("DETALLECODBARRA");
        String detalleMedida = i.getExtras().getString("DETALLEMEDIDA");
        String detallePeso = i.getExtras().getString("DETALLEPESO");
        tprod = i.getExtras().getString("TPROD");
        cprod = i.getExtras().getString("CPROD");
        rutaImgProd = i.getExtras().getString("RutaImgProd");


        String detalleMarca = i.getExtras().getString("MARCA");
        String detalleModelo = i.getExtras().getString("MODELO");
        String detalleAnno = i.getExtras().getString("ANNO");
        String detalleMotor = i.getExtras().getString("MOTOR");
        String detalleHP = i.getExtras().getString("DETALLEHP");

        String Omarcas = i.getExtras().getString("OMARCAS");
        String Ocodigo = i.getExtras().getString("OCODIGO");

        /*if (detalleMotor.trim().length()>0){
            tbMotor.setVisibility(View.VISIBLE);
            motor.setVisibility(View.VISIBLE);
        }*/

        if  (tprod.equals("Limpia Parabrisas")|| tprod.equals("Iluminación")){
            tbMotor.setVisibility(View.GONE);
            motor.setVisibility(View.GONE);
            tbHP.setVisibility(View.GONE);
            hp.setVisibility(View.GONE);
        }

        if (detalle.trim().length() > 0) {
            tOMarcas.setVisibility(View.VISIBLE);
            //txtOMarcas.setVisibility(View.VISIBLE);
        }

        if (detalleCodBarra.trim().length() > 0) {
            tCodBarra.setVisibility(View.VISIBLE);
            txtDetalleCodBarra.setVisibility(View.VISIBLE);
            lineaCodBarra.setVisibility(View.VISIBLE);
        }

        if (detalleMedida.trim().length() > 0) {
            tDetalleMedida.setVisibility(View.VISIBLE);
            txtDetalleMedida.setVisibility(View.VISIBLE);
            lineaMedida.setVisibility(View.VISIBLE);
        }

        if (detallePeso.trim().length() > 0) {
            tDetallePeso.setVisibility(View.VISIBLE);
            txtDetallePeso.setVisibility(View.VISIBLE);
            lineaPeso.setVisibility(View.VISIBLE);
        }



        //BIND DATA

//        txtOMarcas.setText(Html.fromHtml(detalle));
        txtDetalleCodBarra.setText(detalleCodBarra);
        txtDetalleMedida.setText(detalleMedida);
        txtDetallePeso.setText(detallePeso);
        txtCodProd_2.setText(tprod+" "+cprod);


        tbMarca.setText(Html.fromHtml(detalleMarca));
        tbModelo.setText(Html.fromHtml(detalleModelo));
        tbanno.setText(Html.fromHtml(detalleAnno));
        tbMotor.setText(Html.fromHtml(detalleMotor));
        tbHP.setText(Html.fromHtml(detalleHP));

        tbOMarca.setText(Html.fromHtml(Omarcas));
        tbOCod.setText(Html.fromHtml(Ocodigo));


       GlideApp.with(this).load(rutaImgProd)
               .placeholder(R.drawable.ic_base_camara)
               .into(imgProd);

        imgProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDetailActivity(v);


            }
        });


    }

    private void openDetailActivity(View view) {

        //Toast.makeText(this,"sssssssssss "+ rutaImgProd, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DetalleActivity.this, ImagenDetalle.class);
        //System.out.println("qqq2 "+rutaImgProd);
        intent.putExtra("CPROD", cprod);
        intent.putExtra("TPROD", tprod);
        intent.putExtra("IMG", rutaImgProd);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(view.findViewById(R.id.imgProd),
                                    ImagenDetalle.VIEW_NAME_HEADER_IMAGE)
                    );

            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        } else
            startActivity(intent);


    }
}
