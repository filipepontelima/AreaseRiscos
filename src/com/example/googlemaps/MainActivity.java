package com.example.googlemaps;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

	// Button button;
	ImageView image;

	// Lista de pontos
	List<Ponto> listaPontos = new ArrayList<Ponto>();
	Jogador jogador;

	private RelativeLayout telainicial;
	private RelativeLayout telamapa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = LayoutInflater.from(this);
		setContentView(R.layout.tela_inicial);
		telainicial = (RelativeLayout) this.findViewById(R.id.main);
		telamapa = (RelativeLayout) inflater.inflate(R.layout.activity_main,
				null);
		
		jogador = new Jogador ("Filipe", 0xfffaa648);
		// setContentView(R.layout.tela_inicial);
		// setContentView(R.layout.activity_main);
		loadAsset();

		// inicializaMapa();

	}

	public void inicializaMapa(View view) {
		setContentView(telamapa);
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

		googleMap.setMyLocationEnabled(true);
		googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

		GPSTracker gps = new GPSTracker(this);
		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();

		// create marker
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(latitude, longitude)).title("Hello Maps ");

		// adding marker
		marker.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		googleMap.addMarker(marker);

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(14).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	public void loadAsset() {
		image = (ImageView) findViewById(R.id.imageView2);

		try {
			InputStream ims = getAssets().open("fundo_cinza_sobremapa.png");
			Drawable d = Drawable.createFromStream(ims, null);
			image.setImageDrawable(d);
		} catch (IOException ex) {
			return;
		}

		image = (ImageView) findViewById(R.id.imageView1);

		try {
			InputStream ims = getAssets().open("marca_sobrefundo.png");
			Drawable d = Drawable.createFromStream(ims, null);
			image.setImageDrawable(d);
		} catch (IOException ex) {
			return;
		}

	}

	/*
	 * public void mudaimagem(View view) {
	 * 
	 * image = (ImageView) findViewById(R.id.imageView2);
	 * //image.setImageResource(R.drawable.planetavermelho);
	 * 
	 * try { InputStream ims = getAssets().open("marca_sobrefundo.png");
	 * Drawable d = Drawable.createFromStream(ims, null);
	 * image.setImageDrawable(d); } catch (IOException ex) { return; }
	 * 
	 * Toast.makeText(this, "You clicked me!", Toast.LENGTH_SHORT).show(); }
	 */

	public void mostraPonto(Ponto ponto, int cor) {

		CircleOptions viewPonto = new CircleOptions();
		viewPonto.center(new LatLng(ponto.getLatitude(), ponto.getLongitude()));
		viewPonto.radius(30);
		viewPonto.fillColor(0x00000000);
		viewPonto.strokeColor(cor);
		viewPonto.strokeWidth(4);
		googleMap.addCircle(viewPonto);
	}

	public void mostraRiscos(Jogador jogador) {
		
		List<Ponto> listaPontos = jogador.getListaPontos();
		if (listaPontos.isEmpty()) {
			return;
		}
		PolylineOptions viewRiscos = new PolylineOptions();

		for (Ponto ponto : listaPontos) {
			viewRiscos.add(new LatLng(ponto.getLatitude(), ponto.getLongitude()));
			mostraPonto(ponto, jogador.getCor());
		}

		viewRiscos.color(jogador.getCor());
		viewRiscos.width(5);
		googleMap.addPolyline(viewRiscos);
	}

	public void novoPonto(View view) {
		GPSTracker gps = new GPSTracker(this);

		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();

		Ponto ponto = new Ponto(latitude, longitude);
		
		jogador.adicionaPonto(ponto);
		
		listaPontos.add(ponto);

		Toast.makeText(this, "Novo ponto criado com sucesso!",
				Toast.LENGTH_SHORT).show();

		mostraRiscos(jogador);
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