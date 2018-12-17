package com.insumoskeij.appaksu;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.insumoskeij.appaksu.adapter.Adapter;
import com.insumoskeij.appaksu.app.AppController;
import com.insumoskeij.appaksu.interfaces.IcomunicaFragments;
import com.insumoskeij.appaksu.model.Anno;
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
        IcomunicaFragments {

    ProgressDialog pDialog;
    List<Producto> listData = new ArrayList<Producto>();
    Adapter adapter;
    SwipeRefreshLayout swipe;
    ListView list_view;
    String tag_json_obj = "json_obj_req";

    /************Redireccion  pagina********/

    ImageView imgLogoMenu;



    /*****Combo TIPO PRODUCTO****/
    private TextView txtEslogan;
    private Spinner spTipoProducto;
    private TextView tTipoProdCombo;
    private ArrayList<TipoProducto> TprodList;
    private String txtAgregarTprod = "";

    /*****Combo MARCA****/
    private Spinner spMarca;
    private TextView tMarcaCombo;
    private String txtAgregarMarca = "";
    private ArrayList<Marca> MarcaList;

    /*****Combo MODELO****/
    private Spinner spModelo;
    private TextView tModeloCombo;
    private String txtAgregarModelo = "";
    private ArrayList<Modelo> ModeloList;

    /*****Combo Años****/
    private Spinner spAnnos;
    private TextView tAnnoCombo;
    private String txtAgregarAnno = "";
    private ArrayList<Anno> AnnoList;

    /*****Combo MOTOR****/
    private Spinner spMotor;
    private TextView tMotorCombo;
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
    public static final  String TAG_MEDIDA_L_P = "v_medidas_limpia_parabrisas";
    public static final String TAG_ILUMINACION = "v_iluminacion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabSearch = findViewById(R.id.fabSearch);
        fabSearch.setEnabled(false);
        fabSearch.setClickable(false);
        fabSearch.setAlpha(0.3f);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                if (!compruebaConexion(getApplicationContext()))
                {
                    Snackbar.make(view, "¡No hay conexión a internet!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else if (txtAgregarMarca.isEmpty() && txtAgregarTprod.isEmpty()){
                    //Toast.makeText(getApplicationContext(), ("¡Debe seleccionar una opción para la búsqueda!"), Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "¡Debe seleccionar una opción para la búsqueda!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else {
                   callData();
                }
            }
        });

        FloatingActionButton fabClean = findViewById(R.id.fabClean);
        fabClean.setEnabled(false);
        fabClean.setClickable(false);
        fabClean.setAlpha(0.3f);
        fabClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (!compruebaConexion(getApplicationContext()))
                {
                    Snackbar.make(view, "¡No hay conexión a internet!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("AKSU GLOBAL")
                            .setIcon(R.drawable.aksu_icon)
                            .setMessage("¿Desea repetir la búsqueda?")
                            .setNegativeButton(android.R.string.cancel, null)// sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which) {// Salir
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       /* Fragment fragment = new FormBusquedaFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main, fragment).commit();*/

        if (!compruebaConexion(this.getApplicationContext()))
        {
           // Toast.makeText(getApplicationContext(), ("¡No hay conexión a internet!"), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, NoConnectActivity.class);
            startActivity(intent);
            finish();
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtEslogan = findViewById(R.id.txtEslogan);
        txtEslogan.setText(Html.fromHtml("<P ALIGN=\"right\">Bienvenido a nuestro buscador, donde encontrará un completo repertorio de los productos que ofrecemos para el cuidado de su vehículo.</P>"));


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
                if (!txtAgregarTprod.isEmpty()) {
                    FloatingActionButton fabSearch = findViewById(R.id.fabSearch);
                    fabSearch.setEnabled(true);
                    fabSearch.setClickable(true);
                    fabSearch.setAlpha(1f);

                    FloatingActionButton fabClean = findViewById(R.id.fabClean);
                    fabClean.setEnabled(true);
                    fabClean.setClickable(true);
                    fabClean.setAlpha(1f);
                }
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

                if (!txtAgregarMarca.isEmpty()) {

                    new GetModelo().execute();

                    tModeloCombo = findViewById(R.id.tModeloCombo);
                    spModelo.setVisibility(View.VISIBLE);
                    tModeloCombo.setVisibility(View.VISIBLE);

                    if (spAnnos.getVisibility()==View.VISIBLE){
                        spAnnos.setVisibility(View.GONE);
                        tAnnoCombo.setVisibility(View.GONE);
                        txtAgregarAnno="";
                    }

                    if (spMotor.getVisibility()==View.VISIBLE){
                    spMotor.setVisibility(View.GONE);
                    tMotorCombo.setVisibility(View.GONE);
                    txtAgregarMotor="";
                    }

                    FloatingActionButton fabSearch = findViewById(R.id.fabSearch);
                    fabSearch.setEnabled(true);
                    fabSearch.setClickable(true);
                    fabSearch.setAlpha(1f);

                    FloatingActionButton fabClean = findViewById(R.id.fabClean);
                    fabClean.setEnabled(true);
                    fabClean.setClickable(true);
                    fabClean.setAlpha(1f);
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
                if (!txtAgregarModelo.isEmpty()) {
                    AnnoList.clear();
                    new GetAnno().execute();

                    tAnnoCombo = findViewById(R.id.tAnnoCombo);
                    spAnnos.setVisibility(View.VISIBLE);
                    tAnnoCombo.setVisibility(View.VISIBLE);

                    if (spMotor.getVisibility()==View.VISIBLE){
                        spMotor.setVisibility(View.GONE);
                        tMotorCombo.setVisibility(View.GONE);
                        txtAgregarMotor="";
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /****COMBO AÑOS***/
        spAnnos = findViewById(R.id.spAnnos);
        AnnoList = new ArrayList<Anno>();

        spAnnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarAnno = AnnoList.get(i).getId_anno();
                if (!txtAgregarAnno.isEmpty()) {
                    MotorList.clear();
                    new GetMotor().execute();

                    tMotorCombo = findViewById(R.id.tMotorCombo);
                    spMotor.setVisibility(View.VISIBLE);
                    tMotorCombo.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /****COMBO MOTOR***/
        spMotor = findViewById(R.id.spMotor);
        MotorList = new ArrayList<Motor>();
        MotorList.clear();
        spMotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarMotor = MotorList.get(i).getId_motor();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*btnBuscar = findViewById(R.id.btnBuscar);
        request = Volley.newRequestQueue(MainActivity.this);


       /* btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtAgregarMarca.isEmpty() && txtAgregarTprod.isEmpty()){
                    Toast.makeText(getApplicationContext(), ("¡Debe seleccionar una opción para la búsqueda!"), Toast.LENGTH_SHORT).show();

                }else {
                    callData();
                }
            }
        });*/

    }

    private boolean compruebaConexion(Context applicationContext) {

        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;

    }

    /*************CARGANDO COMBO TIPO PRODUCTO**************************************************************************************/
    private void cargarTproducto() {
        List<String> lables = new ArrayList<String>();
        //txtAgregarTprod.setText("");
        for (int i = 0; i < TprodList.size(); i++) {
            lables.add(TprodList.get(i).getTipoProduto());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_style, lables);
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
                        TprodList.clear();
                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            TipoProducto cat = new TipoProducto (catObj.getString("id"), catObj.getString("desc"));
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
                R.layout.spinner_style, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(spinnerAdapter);
    }

    private class GetMarca extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Cargando...");
            pDialog.show();
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
                    MarcaList.clear();
                    if (jsonObj != null) {
                        JSONArray marca = jsonObj.getJSONArray("data");
                        int i = 0;
                        for ( i = 0; i < marca.length(); i++) {
                            JSONObject catObj = (JSONObject) marca.get(i);
                            //System.out.println(catObj);
                            Marca cat = new Marca(catObj.getString("id"), catObj.getString("desc"));
                            //System.out.println(cat);
                            MarcaList.add(cat);
                        }
                        if (i==0){
                            Marca cat = new Marca(" ", "no se");
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
            pDialog.dismiss();
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
                R.layout.spinner_style, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModelo.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
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
            if (!compruebaConexion(getApplicationContext()))
            {
                Snackbar.make(list_view.getRootView(), "¡No hay conexión a internet!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Modelo cat = new Modelo("", "Seleccione");
                ModeloList.add(cat);
                txtAgregarModelo = "";
                //return null;
            }
            else
            {
                ServiceHandler jsonParser = new ServiceHandler();
                String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMModelo.php?opc=1&marca=" + txtAgregarMarca + "&serie=%22%22", ServiceHandler.GET);
                System.out.println("urlllll" + "http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMModelo.php?opc=1&marca=" + txtAgregarMarca + "&serie=%22%22");
                Log.e("Response: ", "> " + json);

                if (json != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(json);
                        if (jsonObj != null) {
                            JSONArray modelos = jsonObj.getJSONArray("data");
                            int i = 0;
                            ModeloList.clear();
                            for (i = 0; i < modelos.length(); i++) {
                                JSONObject catObj = (JSONObject) modelos.get(i);
                                //System.out.println(catObj);
                                Modelo cat = new Modelo(catObj.getString("id"), catObj.getString("desc"));
                                //System.out.println(cat);
                                ModeloList.add(cat);
                            }
                            if (i == 0) {
                                //Modelo cat = new Modelo(" ", "no se");
                                //System.out.println(cat);
                                //ModeloList.add(cat);
                                Modelo cat = new Modelo("", "Seleccione");
                                ModeloList.add(cat);
                                txtAgregarModelo = "";
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
                    Modelo cat = new Modelo("", "Seleccione");
                    ModeloList.add(cat);
                    txtAgregarModelo = "";
                }
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

    /*************CARGANDO COMBO AÑOS**************************************************************************************/
    private void cargarComboAnnos() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < AnnoList.size(); i++) {
            lables.add(AnnoList.get(i).getAnno());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_style, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnnos.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
    }

    private class GetAnno extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!compruebaConexion(getApplicationContext()))
            {
                Snackbar.make(list_view.getRootView(), "¡No hay conexión a internet!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Anno cat = new Anno("", "Seleccione");
                AnnoList.add(cat);
                txtAgregarAnno = "";
                //return null;
            }
            else
            {
                ServiceHandler jsonParser = new ServiceHandler();
                String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMAnno.php?opc=1&marca=" + txtAgregarMarca + "&modelo=" + txtAgregarModelo, ServiceHandler.GET);
                System.out.println("urlllll" + "http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMAnno.php?opc=1&marca=" + txtAgregarMarca + "&modelo=" + txtAgregarModelo);
                Log.e("Response: ", "> " + json);

                if (json != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(json);
                        if (jsonObj != null) {
                            JSONArray annos = jsonObj.getJSONArray("data");
                            int i = 0;
                            AnnoList.clear();
                            for (i = 0; i < annos.length(); i++) {
                                JSONObject catObj = (JSONObject) annos.get(i);
                                //System.out.println(catObj);
                                Anno cat = new Anno(catObj.getString("id"), catObj.getString("desc"));
                                //System.out.println(cat);
                                AnnoList.add(cat);
                            }
                            if (i == 0) {
                                //Anno cat = new Anno(" ", " ");
                                //System.out.println(cat);
                                //AnnoList.add(cat);
                                Anno cat = new Anno("", "Seleccione");
                                AnnoList.add(cat);
                                txtAgregarAnno = "";
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
                    Anno cat = new Anno("", "Seleccione");
                    AnnoList.add(cat);
                    txtAgregarAnno = "";
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            cargarComboAnnos();
        }
    }

    /*************CARGANDO COMBO MOTOR**************************************************************************************/
    private void cargarComboMotor() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < MotorList.size(); i++) {
            lables.add(MotorList.get(i).getNombre_motor());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_style, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotor.setAdapter(spinnerAdapter);
    }

    private class GetMotor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!compruebaConexion(getApplicationContext()))
            {
                Snackbar.make(list_view.getRootView(), "¡No hay conexión a internet!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Motor cat = new Motor("", "Seleccione");
                MotorList.add(cat);
                txtAgregarMotor = "";
                //return null;
            }
            else
            {
                ServiceHandler jsonParser = new ServiceHandler();
                String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMMotor.php?opc=1&modelo=" + txtAgregarModelo + "&annos=" + txtAgregarAnno.replace(" ", "%20"), ServiceHandler.GET);
                Log.e("Response: ", "> " + json);
                if (json != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(json);
                        if (jsonObj != null) {
                            JSONArray motor = jsonObj.getJSONArray("data");
                            MotorList.clear();
                            int i = 0;
                            for (i = 0; i < motor.length(); i++) {
                                JSONObject catObj = (JSONObject) motor.get(i);
                                //System.out.println(catObj);
                                Motor cat = new Motor(catObj.getString("id"), catObj.getString("desc"));
                                //System.out.println(cat);
                                MotorList.add(cat);
                            }
                            if (i == 0) {
                                //Motor cat = new Motor(" ", "no se");
                                //System.out.println(cat);
                                //MotorList.add(cat);
                                Motor cat = new Motor("", "Seleccione");
                                MotorList.add(cat);
                                txtAgregarMotor = "";
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
                    Motor cat = new Motor("", "Seleccione");
                    MotorList.add(cat);
                    txtAgregarMotor = "";
                }
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
        tTipoProdCombo = findViewById(R.id.tTipoProdCombo);
        tMarcaCombo = findViewById(R.id.tMarcaCombo);
        txtEslogan = findViewById(R.id.txtEslogan);

        tTipoProdCombo.setVisibility(View.GONE);
        spTipoProducto.setVisibility(View.GONE);
        tMarcaCombo.setVisibility(View.GONE);
        spMarca.setVisibility(View.GONE);
        txtEslogan.setVisibility(View.GONE);


        if (spModelo.getVisibility()==View.VISIBLE ){
            tModeloCombo.setVisibility(View.GONE);
            spModelo.setVisibility(View.GONE);
        }

        if (spAnnos.getVisibility()==View.VISIBLE ){
            tAnnoCombo.setVisibility(View.GONE);
            spAnnos.setVisibility(View.GONE);
        }

        if (spMotor.getVisibility()==View.VISIBLE){
            tMotorCombo.setVisibility(View.GONE);
            spMotor.setVisibility(View.GONE);
        }

        //btnBuscar.setVisibility(View.GONE);
        list_view.setVisibility(View.VISIBLE);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Cargando...");
        pDialog.show();

        String url_data = "http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlBusqueda.php?opc=1&pais=1&tipoProducto="
                + txtAgregarTprod + "&marca=" + txtAgregarMarca + "&modelo=" + txtAgregarModelo +
                "&annos=" + txtAgregarAnno.replace(" ","%20") + "&motor=" + txtAgregarMotor;
        System.out.println("ttt "+ url_data);
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
                            String txtMLimpiaP="";
                            String txtIluminacion="";
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
                                txtMLimpiaP = txtMLimpiaP + obj2.getString(TAG_MEDIDA_L_P);
                                txtIluminacion = txtIluminacion + obj2.getString(TAG_ILUMINACION);


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
                            data.setTxtMedidaLP(txtMLimpiaP);
                            data.setTxtIluminacion(txtIluminacion);

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
                        //Toast.makeText(getApplicationContext(), ("¡No hay datos disponibles!"), Toast.LENGTH_SHORT).show();
                        Snackbar.make(list_view.getRootView(), "¡No hay datos disponibles!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        tTipoProdCombo.setVisibility(View.VISIBLE);
                        spTipoProducto.setVisibility(View.VISIBLE);
                        tMarcaCombo.setVisibility(View.VISIBLE);
                        spMarca.setVisibility(View.VISIBLE);
                        txtEslogan.setVisibility(View.VISIBLE);

                        if (!txtAgregarModelo.isEmpty()){
                            spModelo.setVisibility(View.VISIBLE);
                            tModeloCombo.setVisibility(View.VISIBLE);
                        }

                        if (!txtAgregarAnno.isEmpty()){
                            spAnnos.setVisibility(View.VISIBLE);
                            tAnnoCombo.setVisibility(View.VISIBLE);
                        }

                        if(!txtAgregarMotor.isEmpty()){
                            spMotor.setVisibility(View.VISIBLE);
                            tMotorCombo.setVisibility(View.VISIBLE);
                        }
                       // btnBuscar.setVisibility(View.VISIBLE);
                        list_view.setVisibility(View.GONE);
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
                //Toast.makeText(getApplicationContext(), ("¡Verifique su conexion !"), Toast.LENGTH_SHORT).show();
                Snackbar.make(list_view.getRootView(), "¡Verifique su conexion!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        if (!compruebaConexion(getApplicationContext()))
        {
            Snackbar.make(list_view.getRootView(), "¡No hay conexión a internet!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        /*****funcion par aocultar teclado ***/
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(list_view.getWindowToken(), 0);
        /********fin*********/
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

        FloatingActionButton fabClean = findViewById(R.id.fabClean);
        fabClean.setEnabled(true);
        fabClean.setClickable(true);
        fabClean.setAlpha(1f);

        tTipoProdCombo = findViewById(R.id.tTipoProdCombo);
        tMarcaCombo = findViewById(R.id.tMarcaCombo);
        txtEslogan = findViewById(R.id.txtEslogan);

        tTipoProdCombo.setVisibility(View.GONE);
        spTipoProducto.setVisibility(View.GONE);
        tMarcaCombo.setVisibility(View.GONE);
        spMarca.setVisibility(View.GONE);

        tTipoProdCombo.setVisibility(View.GONE);
        spTipoProducto.setVisibility(View.GONE);
        tMarcaCombo.setVisibility(View.GONE);
        spMarca.setVisibility(View.GONE);
        txtEslogan.setVisibility(View.GONE);


        if (spModelo.getVisibility()==View.VISIBLE ){
            tModeloCombo.setVisibility(View.GONE);
            spModelo.setVisibility(View.GONE);
        }

        if (spAnnos.getVisibility()==View.VISIBLE ){
            tAnnoCombo.setVisibility(View.GONE);
            spAnnos.setVisibility(View.GONE);
        }

        if (spMotor.getVisibility()==View.VISIBLE){
            tMotorCombo.setVisibility(View.GONE);
            spMotor.setVisibility(View.GONE);
        }

        if (list_view.getVisibility()==View.GONE){
            list_view.setVisibility(View.VISIBLE);
        }

        //btnBuscar.setVisibility(View.GONE);


        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Cargando...");
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
                            String txtMLimpiaP="";
                            String txtIluminacion="";
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
                                txtMLimpiaP = txtMLimpiaP + obj2.getString(TAG_MEDIDA_L_P);
                                txtIluminacion = txtIluminacion +obj2.getString(TAG_ILUMINACION);

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
                            data.setTxtMedidaLP(txtMLimpiaP);
                            data.setTxtIluminacion(txtIluminacion);

                            data.setTxtTipoProd(obj.getString(TAG_TIPO_PROD));
                            data.setTxtCodigoProd(obj.getString(TAG_CODIGO));
                            data.setRutaImg(obj.getString(TAG_RUTA_IMG));
                            data.setRutaImg_2(obj.getString(TAG_RUTA_IMG_2));
                            data.setTxtDetalle(obj.getString(TAG_DETALLES_O_MARCAS));
                            data.setTxtDetalleCodBarra(obj.getString(TAG_DETALLESCODBARRA));
                            data.setTxtDetalleMedida(obj.getString(TAG_DETALLESMEDIDA));
                            data.setTxtDetallePeso(obj.getString(TAG_DETALLESPESO));
                            listData.add(data);
                            pDialog.dismiss();
                        }

                    } else {
                        //Toast.makeText(getApplicationContext(), ("¡No hay datos disponibles!"), Toast.LENGTH_SHORT).show();
                        Snackbar.make(list_view.getRootView(), "¡No hay datos disponibles!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        pDialog.dismiss();
                        tTipoProdCombo.setVisibility(View.VISIBLE);
                        spTipoProducto.setVisibility(View.VISIBLE);
                        tMarcaCombo.setVisibility(View.VISIBLE);
                        spMarca.setVisibility(View.VISIBLE);
                        txtEslogan.setVisibility(View.VISIBLE);
                        //btnBuscar.setVisibility(View.VISIBLE);
                        list_view.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), ("¡Verifique su conexion!"), Toast.LENGTH_SHORT).show();
                Snackbar.make(list_view.getRootView(), "¡Verifique su conexion!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

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
            FloatingActionButton fabClean = findViewById(R.id.fabClean);
            fabClean.setEnabled(false);
            fabClean.setClickable(false);
            fabClean.setAlpha(0.3f);

            FloatingActionButton fabSearch = findViewById(R.id.fabSearch);
            fabSearch.setEnabled(false);
            fabSearch.setClickable(false);
            fabSearch.setAlpha(0.3f);
            return true;
        }
        System.out.println("flecha: " +id);
        if (id == 16908332) {

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
            tTipoProdCombo = findViewById(R.id.tTipoProdCombo);
            tMarcaCombo = findViewById(R.id.tMarcaCombo);

            tTipoProdCombo.setVisibility(View.VISIBLE);
            spTipoProducto.setVisibility(View.VISIBLE);
            tMarcaCombo.setVisibility(View.VISIBLE);
            spMarca.setVisibility(View.VISIBLE);
            txtEslogan.setVisibility(View.VISIBLE);

            if (spModelo.getVisibility()==View.VISIBLE ){
                tModeloCombo.setVisibility(View.GONE);
                spModelo.setVisibility(View.GONE);
            }

            if (spAnnos.getVisibility()==View.VISIBLE ){
                tAnnoCombo.setVisibility(View.GONE);
                spAnnos.setVisibility(View.GONE);
            }

            if (spMotor.getVisibility()==View.VISIBLE){
                tMotorCombo.setVisibility(View.GONE);
                spMotor.setVisibility(View.GONE);
            }
           // btnBuscar.setVisibility(View.VISIBLE);
            list_view.setVisibility(View.GONE);


        }/*else if (id == R.id.Ajustes) {
            /////////////////Ajustes////////////
        }*/
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
        }else if (id == R.id.mail) {
            Intent intent = new Intent(this, SendMailActivity.class);
            startActivity(intent);
        }else if (id == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.aksu_icon)
                    .setTitle("AKSU GLOBAL")
                    .setMessage("¿Está seguro que desea salir?")
                    .setNegativeButton(android.R.string.cancel, null)// sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
// Salir
                            finish();
                        }
                    })
                    .show();

// Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
// para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }
    public void imageClick(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.aksu_icon)
                .setTitle("AKSU GLOBAL")
                .setMessage("¿Desea visitar nuestra pagina Web?")
                .setNegativeButton(android.R.string.cancel, null)// sin listener
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// S
                        Uri uri = Uri.parse("http://www.aksuglobal.com");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                })
                .show();
// Salir
    }

}
