package com.insumoskeij.appaksu.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.insumoskeij.appaksu.DetalleActivity;
import com.insumoskeij.appaksu.GlideApp;
import com.insumoskeij.appaksu.R;
import com.insumoskeij.appaksu.model.Producto;

import java.util.List;


public class Adapter extends BaseAdapter {
    //private Activity activity;
    Context context;
    private LayoutInflater inflater;
    private List<Producto> item;

    public Adapter(Context context, List<Producto> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, parent, false);


        TextView txt_tipoProd_2 = convertView.findViewById(R.id.txtTipoProd_2);
        TextView txt_codProd_2 = convertView.findViewById(R.id.txtCodProd_2);
        ImageView imgProd = convertView.findViewById(R.id.imgProd);

        txt_tipoProd_2.setText(item.get(position).getTxtTipoProd());
        txt_codProd_2.setText(item.get(position).getTxtCodigoProd());


        GlideApp.with(this.context).load(item.get(position).getRutaImg())
                .centerCrop()
                .placeholder(R.drawable.ic_base_camara)
                .into(imgProd);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailActivity(position);

            }
        });


        return convertView;
    }

    private void openDetailActivity(int position) {

        Intent intent = new Intent(context, DetalleActivity.class);
        intent.putExtra("MOTOR", (item.get(position).getTxtMotor()));
        intent.putExtra("DETALLE", (item.get(position).getTxtDetalle()));
        intent.putExtra("DETALLECODBARRA", (item.get(position).getTxtDetalleCodBarra()));
        intent.putExtra("DETALLEMEDIDA", (item.get(position).getTxtDetalleMedida()));
        intent.putExtra("DETALLEPESO", (item.get(position).getTxtDetallePeso()));
        intent.putExtra("RutaImgProd", (item.get(position).getRutaImg_2()));
        intent.putExtra("MARCA", item.get(position).getTxtMarca());
        intent.putExtra("MODELO", item.get(position).getTxtModelo());
        intent.putExtra("ANNO", item.get(position).getTxtAnno());
        intent.putExtra("DETALLEHP", item.get(position).getTxtKwPotencia());
        intent.putExtra("TPROD", item.get(position).getTxtTipoProd());
        intent.putExtra("CPROD", item.get(position).getTxtCodigoProd());
        intent.putExtra("OMARCAS", item.get(position).getTxtDetalleOMarca());
        intent.putExtra("OCODIGO", item.get(position).getTxtDetalleOCod());

        context.startActivity(intent);
    }
}