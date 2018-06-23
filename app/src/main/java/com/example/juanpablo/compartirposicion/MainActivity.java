package com.example.juanpablo.compartirposicion;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import static android.widget.Toast.makeText;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    Button btnGPS;
    TextView tvUbicacion;
    TextView tvLatitud;
    TextView tvLongitud;
    String[] arr;

    RequestQueue rq;
    JsonRequest jrq;


    //cuando arranque la aplicacion va a solicitar el permiso para el gps
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUbicacion = (TextView)findViewById(R.id.tvUbicacion);
        tvLatitud = (TextView)findViewById(R.id.tvLatitud);
        tvLongitud = (TextView)findViewById(R.id.tvLongitud);
        btnGPS = (Button)findViewById(R.id.btnGPS);

        rq = Volley.newRequestQueue(this);

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Acquire a reference to the system Location Manager
                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

                // Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {


                    public void onLocationChanged(Location location) {

                        Double lat = location.getLatitude();
                        Double lon = location.getLongitude();
                        tvUbicacion.setText(""+lat+" "+lon);

                        compartirPosicion(lat,lon);

                        //compartirPosicion();


                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                };


                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                // Register the listener with the Location Manager to receive location updates
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        });



        // Lee el permiso para el gps
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        // Pregunta si el permiso esta denegado, Si no tenemos permiso vamos a solicitarlo
        if ( permissionCheck == getPackageManager().PERMISSION_DENIED ) {
            // Si requiere mostrar un mensaje aparte para solicitud del servicio
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Aca muestra el mensaje si requiere mostrar un mensaje aparte para la solicitud del servicio

            } else {

                // Si no lo necesita, aca hace las solicitud el permiso

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


            }
        }
    }
/*
    private void compartirPosicion( ){

       String url = "http://192.168.0.40/login/comunicacion.php?";
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);

        rq.add(jrq);
    }

*/

    private void compartirPosicion( Double lat, Double lon ){

        String url = "http://192.168.0.85/login/actualizarPosicion.php?x="+lat.toString()+"&y="+lon.toString();

        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this,"errorcito: "+ error.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
/*
        JSONArray jsonArray = response.optJSONArray("coordenadas");
        JSONObject jsonObject = null;

        try{
            jsonObject = jsonArray.getJSONObject(0);
            tvLatitud.setText(jsonObject.optString("latitud"));
            //tvLongitud.setText(jsonObject.optString("longitud"));

            arr = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++){
                //arr[i] = jsonArray.getString(i);
                arr[i] = jsonArray.getJSONObject(i).toString();
                //arr[i] = jsonArray.getString(0);
                }

            tvLatitud.setText(arr[0]);
           // tvLongitud.setText(arr[1]);


        }catch (JSONException e){
            e.printStackTrace();
        }

        Toast.makeText(this,"todo ok: ", Toast.LENGTH_SHORT).show();

    }*/
    }

}



