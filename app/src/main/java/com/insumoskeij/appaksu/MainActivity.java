package com.insumoskeij.appaksu;



import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.insumoskeij.appaksu.adapter.Adapter;
import com.insumoskeij.appaksu.app.AppController;
import com.insumoskeij.appaksu.fragment.FormBusquedaFragment;
import com.insumoskeij.appaksu.interfaces.IcomunicaFragments;
import com.insumoskeij.appaksu.model.Marca;
import com.insumoskeij.appaksu.model.Modelo;
import com.insumoskeij.appaksu.model.Motor;
import com.insumoskeij.appaksu.model.Producto;
import com.insumoskeij.appaksu.model.TipoProducto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener,
        IcomunicaFragments,
        FormBusquedaFragment.OnFragmentInteractionListener {

    ProgressDialog pDialog;
    List<Producto> listData = new ArrayList<Producto>();
    Adapter adapter;
    SwipeRefreshLayout swipe;
    ListView list_view;
    String tag_json_obj = "json_obj_req";

    Button btnBuscar;
    RequestQueue request;

    boolean fragmentSelecionado = false;

    /*****Combo TIPO PRODUCTO****/
    public Spinner spTipoProducto;
    private ArrayList<TipoProducto> TprodList;
    private String txtAgregarTprod = "";

    /*****Combo MARCA****/
    private Spinner spMarca;
    private String txtAgregarMarca = "";
    private ArrayList<Marca> MarcaList;

    /*****Combo MODELO****/
    private Spinner spModelo;
    private String txtAgregarModelo = "";
    private ArrayList<Modelo> ModeloList;

    /*****Combo MOTOR****/
    private Spinner spMotor;
    private String txtAgregarMotor = "";
    private ArrayList<Motor> MotorList;

    public String url_data = "http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlBusqueda.php?opc=1&pais=1&tipoProducto=" + txtAgregarTprod + "&marca=" + txtAgregarMarca + "&modelo=" + txtAgregarModelo + "&motor=" + txtAgregarMotor;
    public static final String url_cari = "http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlBusqueda.php?opc=1&pais=1";

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String TAG_MARCA = "v_nombre_marca";
    public static final String TAG_MODELO = "v_nombre_modelo";
    public static final String TAG_MOTOR = "v_nombre_motor";
    public static final String TAG_KW = "v_kw_poten";
    public static final String TAG_ANNO = "v_annos";
    public static final String TAG_CODIGO = "d_codigo";
    public static final String TAG_TIPO_PROD = "d_tipo_prod";
    public static final String TAG_RUTA_IMG = "d_imagen";
    public static final String TAG_RUTA_IMG_2 = "d_imagen_2";
    public static final String TAG_DETALLES_O_MARCAS = "d_detallesOtrasMarcas";
    public static final String TAG_DETALLESCODBARRA = "d_detallesCodBarra";
    public static final String TAG_DETALLESMEDIDA = "d_detallesMedida";
    public static final String TAG_DETALLESPESO = "d_detallesPeso";
    public static final String TAG_RESULTS = "data";
    public static final String TAG_RESULTSVEH = "d_detallesVehiculos";
    public static final String TAG_MESSAGE = "d_tipo_prod";
    public static final String TAG_VALUE = "value";
    public static final  String TAG_DETALLE_O_MARCA = "r_desc_marca_prod";
    public static final  String TAG_DETALLE_O_COD = "r_cod_prod_marca";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       /* Fragment fragment = new FormBusquedaFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main, fragment).commit();*/


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //swipe = findViewById(R.id.swipe_refresh);


        list_view = findViewById(R.id.list_view);

        adapter = new Adapter(this, listData);
        list_view.setAdapter(adapter);

        /*swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           callData();
                       }
                   }
        );*/

        /*****Combo Tipo Producto******/
        spTipoProducto = findViewById(R.id.spTipoProducto);
        TprodList = new ArrayList<TipoProducto>();

        spTipoProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarTprod = TprodList.get(i).getIdTipoProducto();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        new GetTproducto().execute();

        /*****Combo Marca******/
        spMarca = findViewById(R.id.spMarca);
        MarcaList = new ArrayList<Marca>();

        spMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarMarca = MarcaList.get(i).getId_marca();
                ModeloList.clear();
                MotorList.clear();
                if (!txtAgregarMarca.isEmpty()) {
                    new GetModelo().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new GetMarca().execute();

        /****COMBO MODELO***/
        spModelo = findViewById(R.id.spModelo);
        ModeloList = new ArrayList<Modelo>();

        spModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarModelo = ModeloList.get(i).getId_modelo();
                MotorList.clear();
                if (!txtAgregarModelo.isEmpty()) {
                    new GetMotor().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /****COMBO MOTOR***/
        spMotor = findViewById(R.id.spMotor);
        MotorList = new ArrayList<Motor>();

        spMotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarMotor = MotorList.get(i).getId_motor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnBuscar = findViewById(R.id.btnBuscar);
        request = Volley.newRequestQueue(MainActivity.this);


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtAgregarMarca.isEmpty() && txtAgregarTprod.isEmpty()){
                    Toast.makeText(getApplicationContext(), ("¡Debe seleccionar una opción para la búsqueda!"), Toast.LENGTH_SHORT).show();

                }else {
                    callData();
                }
            }
        });

    }
    /*************CARGANDO COMBO TIPO PRODUCTO**************************************************************************************/
    private void cargarTproducto() {
        List<String> lables = new ArrayList<String>();
        //txtAgregarTprod.setText("");
        for (int i = 0; i < TprodList.size(); i++) {
            lables.add(TprodList.get(i).getTipoProduto());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoProducto.setAdapter(spinnerAdapter);
    }

    private class GetTproducto extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
              /*  progreso = new ProgressDialog(getContext());
                progreso.setMessage("Cargando compbos..");
                progreso.setCancelable(false);
                progreso.show();*/
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            //String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMTipoProducto.php?opc="+idIdioma, ServiceHandler.GET);
            String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMTipoProducto.php?opc=1", ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj.getJSONArray("data");

                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            TipoProducto cat = new TipoProducto(catObj.getString("id"), catObj.getString("desc"));

                            TprodList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
                /*if (progreso.isShowing())
                    progreso.dismiss();*/
            cargarTproducto();
        }
    }
    /**************FIN CARGA DE COMBO TIPO PRODUCTO***********************************************************************************/

    /*************CARGANDO COMBO MARCA**************************************************************************************/
    private void cargarComboMarca() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < MarcaList.size(); i++) {
            lables.add(MarcaList.get(i).getNombre_marca());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(spinnerAdapter);
    }

    private class GetMarca extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
              /*  progreso = new ProgressDialog(getContext());
                progreso.setMessage("Cargando compbos..");
                progreso.setCancelable(false);
                progreso.show();*/
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            //String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMMarca.php?opc="+idIdioma+"&pais="+idPais+"", ServiceHandler.GET);
            String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMMarca.php?opc=1&pais=1", ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj.getJSONArray("data");

                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            Marca cat = new Marca(catObj.getString("id"), catObj.getString("desc"));
                            //System.out.println(cat);
                            MarcaList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
                /*if (progreso.isShowing())
                    progreso.dismiss();*/
            cargarComboMarca();
        }
    }
    /**************FIN CARGA DE MARCA***********************************************************************************/

    /*************CARGANDO COMBO MODELO**************************************************************************************/
    private void cargarComboModelo() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < ModeloList.size(); i++) {
            lables.add(ModeloList.get(i).getNombre_modelo());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModelo.setAdapter(spinnerAdapter);
    }

    private class GetModelo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
              /*  progreso = new ProgressDialog(getContext());
                progreso.setMessage("Cargando compbos..");
                progreso.setCancelable(false);
                progreso.show();*/
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMModelo.php?opc=1&marca=" + txtAgregarMarca + "&serie=%22%22", ServiceHandler.GET);
            System.out.println("urlllll" + "http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMModelo.php?opc=1&marca=" + txtAgregarMarca + "&serie=%22%22");
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj.getJSONArray("data");

                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            Modelo cat = new Modelo(catObj.getString("id"), catObj.getString("desc"));
                            //System.out.println(cat);
                            ModeloList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
                /*if (progreso.isShowing())
                    progreso.dismiss();*/
            cargarComboModelo();
        }
    }
    /**************FIN CARGA DE MODELO***********************************************************************************/

    /*************CARGANDO COMBO MOTOR**************************************************************************************/
    private void cargarComboMotor() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < MotorList.size(); i++) {
            lables.add(MotorList.get(i).getNombre_motor());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotor.setAdapter(spinnerAdapter);
    }

    private class GetMotor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
              /*  progreso = new ProgressDialog(getContext());
                progreso.setMessage("Cargando compbos..");
                progreso.setCancelable(false);
                progreso.show();*/
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMMotor.php?opc=1&modelo=" + txtAgregarModelo, ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj.getJSONArray("data");

                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            Motor cat = new Motor(catObj.getString("id"), catObj.getString("desc"));
                            //System.out.println(cat);
                            MotorList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
                /*if (progreso.isShowing())
                    progreso.dismiss();*/
            cargarComboMotor();
        }
    }

    /**************FIN CARGA DE MOTOR***********************************************************************************/


    private void callData() {
        spTipoProducto.setVisibility(View.GONE);
        spMarca.setVisibility(View.GONE);
        spModelo.setVisibility(View.GONE);
        spMotor.setVisibility(View.GONE);
        spTipoProducto.setVisibility(View.GONE);
        btnBuscar.setVisibility(View.GONE);
        list_view.setVisibility(View.VISIBLE);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url_data = "http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlBusqueda.php?opc=1&pais=1&tipoProducto=" + txtAgregarTprod + "&marca=" + txtAgregarMarca + "&modelo=" + txtAgregarModelo + "&motor=" + txtAgregarMotor;
        listData.clear();
        adapter.notifyDataSetChanged();
       // swipe.setRefreshing(true);

        // Creating volley request obj
        StringRequest strReq = new StringRequest(Request.Method.GET, url_data, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);


                    if (value > 0) {
                        listData.clear();
                        adapter.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Producto data = new Producto();
                            String txtMarca = "";
                            String txtModelo = "";
                            String txtMotor = "";
                            String txtAnno = "";
                            String txtHp = "";
                            String txtOMarca="";
                            String txtOCod="";

                            String getObjectveh = obj.getString(TAG_RESULTSVEH);
                            JSONArray jsonArrayVeh = new JSONArray(getObjectveh);

                            for (int j = 0; j < jsonArrayVeh.length(); j++) {

                                JSONObject obj2 = jsonArrayVeh.getJSONObject(j);

                                txtMarca = txtMarca + obj2.getString(TAG_MARCA);
                                txtModelo = txtModelo + obj2.getString(TAG_MODELO);
                                txtMotor = txtMotor + obj2.getString(TAG_MOTOR);
                                txtAnno = txtAnno + obj2.getString(TAG_ANNO);
                                txtHp = txtHp + obj2.getString(TAG_KW);

                            }

                            String getObjectoMarcas = obj.getString(TAG_DETALLES_O_MARCAS);
                            JSONArray jsonArrayOMarcas = new JSONArray(getObjectoMarcas);

                            for (int j = 0; j < jsonArrayOMarcas.length(); j++) {

                                JSONObject obj3 = jsonArrayOMarcas.getJSONObject(j);

                                txtOMarca = txtOMarca + obj3.getString(TAG_DETALLE_O_MARCA);
                                txtOCod = txtOCod + obj3.getString(TAG_DETALLE_O_COD);

                            }

                            data.setTxtDetalleOMarca(txtOMarca);
                            data.setTxtDetalleOCod(txtOCod);

                            data.setTxtMarca(txtMarca);
                            data.setTxtModelo(txtModelo);
                            data.setTxtMotor(txtMotor);
                            data.setTxtKwPotencia(txtHp);
                            data.setTxtAnno(txtAnno);

                            data.setTxtTipoProd(obj.getString(TAG_TIPO_PROD));
                            data.setTxtCodigoProd(obj.getString(TAG_CODIGO));
                            data.setRutaImg(obj.getString(TAG_RUTA_IMG));
                            data.setRutaImg_2(obj.getString(TAG_RUTA_IMG_2));
                            data.setTxtDetalle(obj.getString(TAG_DETALLES_O_MARCAS));
                            data.setTxtDetalleCodBarra(obj.getString(TAG_DETALLESCODBARRA));
                            data.setTxtDetalleMedida(obj.getString(TAG_DETALLESMEDIDA));
                            data.setTxtDetallePeso(obj.getString(TAG_DETALLESPESO));
                            listData.add(data);

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), ("¡No hay datos disponibles!"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                 pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
               //swipe.setRefreshing(false);
                pDialog.dismiss();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onRefresh() {
        //callData();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cariData(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.Buscar));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    public void cariData(final String keyword) {


        spTipoProducto.setVisibility(View.GONE);
        spMarca.setVisibility(View.GONE);
        spModelo.setVisibility(View.GONE);
        spMotor.setVisibility(View.GONE);
        spTipoProducto.setVisibility(View.GONE);
        btnBuscar.setVisibility(View.GONE);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_cari, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);


                    if (value > 0) {
                        listData.clear();
                        adapter.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Producto data = new Producto();
                            String txtMarca = "";
                            String txtModelo = "";
                            String txtMotor = "";
                            String txtAnno = "";
                            String txtHp = "";
                            String txtOMarca="";
                            String txtOCod="";

                            String getObjectveh = obj.getString(TAG_RESULTSVEH);
                            JSONArray jsonArrayVeh = new JSONArray(getObjectveh);

                            for (int j = 0; j < jsonArrayVeh.length(); j++) {

                                JSONObject obj2 = jsonArrayVeh.getJSONObject(j);

                                txtMarca = txtMarca + obj2.getString(TAG_MARCA);
                                txtModelo = txtModelo + obj2.getString(TAG_MODELO);
                                txtMotor = txtMotor + obj2.getString(TAG_MOTOR);
                                txtAnno = txtAnno + obj2.getString(TAG_ANNO);
                                txtHp = txtHp + obj2.getString(TAG_KW);

                            }

                            String getObjectoMarcas = obj.getString(TAG_DETALLES_O_MARCAS);
                            JSONArray jsonArrayOMarcas = new JSONArray(getObjectoMarcas);

                            for (int j = 0; j < jsonArrayOMarcas.length(); j++) {

                                JSONObject obj3 = jsonArrayOMarcas.getJSONObject(j);

                                txtOMarca = txtOMarca + obj3.getString(TAG_DETALLE_O_MARCA);
                                txtOCod = txtOCod + obj3.getString(TAG_DETALLE_O_COD);

                            }

                            data.setTxtDetalleOMarca(txtOMarca);
                            data.setTxtDetalleOCod(txtOCod);

                            data.setTxtMarca(txtMarca);
                            data.setTxtModelo(txtModelo);
                            data.setTxtMotor(txtMotor);
                            data.setTxtKwPotencia(txtHp);
                            data.setTxtAnno(txtAnno);

                            data.setTxtTipoProd(obj.getString(TAG_TIPO_PROD));
                            data.setTxtCodigoProd(obj.getString(TAG_CODIGO));
                            data.setRutaImg(obj.getString(TAG_RUTA_IMG));
                            data.setRutaImg_2(obj.getString(TAG_RUTA_IMG_2));
                            data.setTxtDetalle(obj.getString(TAG_DETALLES_O_MARCAS));
                            data.setTxtDetalleCodBarra(obj.getString(TAG_DETALLESCODBARRA));
                            data.setTxtDetalleMedida(obj.getString(TAG_DETALLESMEDIDA));
                            data.setTxtDetallePeso(obj.getString(TAG_DETALLESPESO));
                            listData.add(data);

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), ("¡No hay datos disponibles!"), Toast.LENGTH_SHORT).show();
                        spTipoProducto.setVisibility(View.VISIBLE);
                        spMarca.setVisibility(View.VISIBLE);
                        spModelo.setVisibility(View.VISIBLE);
                        spMotor.setVisibility(View.VISIBLE);
                        spTipoProducto.setVisibility(View.VISIBLE);
                        btnBuscar.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                 pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


                  pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.BusquedaPersonalisada) {
            new GetTproducto().execute();
            new GetMarca().execute();
            new GetModelo().execute();
            new GetMotor().execute();

            spTipoProducto.setVisibility(View.VISIBLE);
            spMarca.setVisibility(View.VISIBLE);
            spModelo.setVisibility(View.VISIBLE);
            spMotor.setVisibility(View.VISIBLE);
            spTipoProducto.setVisibility(View.VISIBLE);
            btnBuscar.setVisibility(View.VISIBLE);
            list_view.setVisibility(View.GONE);
        }else if (id == R.id.Ajustes) {
            /////////////////Ajustes////////////
        }
        else if (id == R.id.instagram) {
            Uri uri = Uri.parse("https://instagram.com/aksuglobal");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        } else if (id == R.id.twitter) {
            Uri uri = Uri.parse("https://twitter.com/aksuglobal");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        } else if (id == R.id.facebook) {
            Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/aksuglobal/?ref=br_rs");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Uri uri2 = Uri.parse("https://www.facebook.com/aksuglobal/?ref=br_rs");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);

                startActivity(intent2);
            }
        } else if (id == R.id.youtube) {
            Uri uri = Uri.parse("https://www.youtube.com/user/AKSUVZLA");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void enviarProducto(Producto producto) {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
