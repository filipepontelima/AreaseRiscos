package com.example.googlemaps;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MainActivity extends FragmentActivity {
 
    // Google Map
    private GoogleMap googleMap;
    
    //Button button;
	ImageView image;
	
	//Lista de pontos
	List<Ponto> listaPontos = new ArrayList<Ponto>();
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        
        GPSTracker gps = new GPSTracker(this);
        
     // latitude and longitude
        //double latitude = -15.7989;
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
         
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");
         
        // adding marker
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        
        googleMap.addMarker(marker);
        
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(14).build();
 
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        
        
        
        
        
        
        
        
        
        /*PolylineOptions rectOptions = new PolylineOptions();
        rectOptions.add(new LatLng(latitude - 0.005, longitude - 0.005));
        rectOptions.add(new LatLng(latitude - 0.008, longitude - 0.008));
        //rectOptions.add(new LatLng(latitude - 0.002, longitude - 0.008));
        //rectOptions.add(new LatLng(latitude - 0.002, longitude - 0.002));
        rectOptions.color(0xff77a858);
        rectOptions.width(5);
        googleMap.addPolyline(rectOptions);*/
        
    }
    
    public void mudaimagem(View view) {
    	 
		image = (ImageView) findViewById(R.id.imageView1);
		image.setImageResource(R.drawable.planetavermelho);
		
		Toast.makeText(this, "You clicked me!", Toast.LENGTH_SHORT).show();
	}
    
    public void mostraPonto (Ponto ponto) {
    	
    	CircleOptions viewPonto = new CircleOptions ();
    	viewPonto.center (new LatLng(ponto.getLatitude(),ponto.getLongitude()));
    	viewPonto.radius (30);
    	viewPonto.fillColor(0x00000000);
    	viewPonto.strokeColor(ponto.getRGB());
    	viewPonto.strokeWidth(4);
        googleMap.addCircle (viewPonto);
    }
    
    public void mostraRiscos (List<Ponto> listaPontos) {
    	
    	if (listaPontos.isEmpty()) {
    		return;
    	}
    	PolylineOptions viewRiscos = new PolylineOptions();
    	
    	for(Ponto ponto : listaPontos){
    		viewRiscos.add(new LatLng(ponto.getLatitude(), ponto.getLongitude()));
    		mostraPonto (ponto);
		}
    	
    	viewRiscos.color(listaPontos.get(0).getRGB());
    	viewRiscos.width(5);
    	googleMap.addPolyline(viewRiscos);
    }
    
    public void novoPonto (View view) {
    	GPSTracker gps = new GPSTracker(this);
        
    	double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
    	
    	Ponto ponto = new Ponto (latitude,longitude,0xfffaa648);
    	
    	listaPontos.add(ponto);
    	
    	Toast.makeText(this, "Novo ponto criado com sucesso!", Toast.LENGTH_SHORT).show();
    	
    	mostraRiscos (listaPontos);
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
 
}