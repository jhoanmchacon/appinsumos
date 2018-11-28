package com.insumoskeij.appaksu.adapter;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.insumoskeij.appaksu.GlideApp;
import com.insumoskeij.appaksu.R;
import com.insumoskeij.appaksu.model.Producto;

import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.CatalogoHolder> implements View.OnClickListener {

    List<Producto> productoList;
    private View.OnClickListener listener;
    RequestQueue request;
    Context context;
    Activity activity;

    private LayoutInflater inflater;

    public CatalogoAdapter(List<Producto> productoList, Activity activity){
        this.productoList = productoList;
        this.activity=activity;

        request= Volley.newRequestQueue(activity);
    }


    @Override
    public CatalogoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext() ).inflate(R.layout.list_item,parent,false);
        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new CatalogoHolder(vista);
    }

    public  void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;

    }

    @Override
    public void onBindViewHolder(CatalogoHolder holder, int position) {
        holder.txtFiltro.setText(productoList.get(position).getTxtCodigoProd());
        holder.txtTipoProd.setText(productoList.get(position).getTxtTipoProd());
        cargarImagenWebService(productoList.get(position).getRutaImg(),holder);



    }

    private void cargarImagenWebService(String rutaimg, final CatalogoHolder holder) {
        String urlImagen = rutaimg;
        urlImagen = urlImagen.replace(" ", "%20");
        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.imgProd.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "error al cargar imagen ", Toast.LENGTH_SHORT).show();
                holder.imgProd.setImageResource(R.drawable.img_base);
            }
        });
        request.add(imageRequest);
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    @Override
    public void onClick(View view) {
    if (listener!=null){
        listener.onClick(view);
    }
    }


    public class CatalogoHolder extends RecyclerView.ViewHolder {

        TextView txtFiltro, txtTipoProd;
        ImageView imgProd;
        FrameLayout frameLayout;
        Spinner spinner;

        public CatalogoHolder(View itemView) {
            super(itemView);
            txtTipoProd=itemView.findViewById(R.id.txtTipoProd_2);
            imgProd=itemView.findViewById(R.id.imgProd);
            txtFiltro=itemView.findViewById(R.id.txtCodProd_2);
            frameLayout=itemView.findViewById(R.id.FormBusquedaFragment);

        }
    }
}
