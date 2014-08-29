package com.example.googlemaps;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.unbiquitous.network.http.connection.ClientMode;
import org.unbiquitous.uos.core.UOS;

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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity {

	// Google Map
	private GoogleMap googleMap;

	// Button button;
	ImageView image;

	// Jogadores
	Jogador jogador, jogador2;

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
		jogador2 = new Jogador ("David", 0xff53b7d3);
			Ponto ponto = new Ponto(-15.75, -47.88);
			jogador2.adicionaPonto(ponto);
			ponto = new Ponto(-15.75, -47.88);
			ponto.setLatLng(-15.77, -47.885);
			jogador2.adicionaPonto(ponto);
			ponto = new Ponto(-15.75, -47.88);
			ponto.setLatLng(-15.762, -47.871);
			jogador2.adicionaPonto(ponto);
			ponto = new Ponto(-15.75, -47.88);
			ponto.setLatLng(-15.75, -47.888);
			jogador2.adicionaPonto(ponto);
			Area area = new Area (-15.754, -47.883);
			jogador2.adicionaArea(area);
			area = new Area (-15.76, -47.877);
			jogador2.adicionaArea(area);
			
		// setContentView(R.layout.tela_inicial);
		// setContentView(R.layout.activity_main);
		loadAsset();
		
		
		
		//settar cliente
		/*UOS uos = new UOS();
	    ClientMode.Properties props = new ClientMode.Properties();
	    props.setServer("www.my.server.net");
	    uos.start(props);*/

		// inicializaMapa();

	}

	public void inicializaMapa(View view) {
		
		//loadAsset();
		
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
		/*MarkerOptions marker = new MarkerOptions().position(
				new LatLng(latitude, longitude)).title("Hello Maps ");

		// adding marker
		marker.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		googleMap.addMarker(marker);*/

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(14).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
		
		atualizaMapa();
	}

	public void loadAsset() {
		image = (ImageView) findViewById(R.id.imageView1);

		try {
			InputStream ims = getAssets().open("fundo_cinza_sobremapa.png");
			Drawable d = Drawable.createFromStream(ims, null);
			image.setImageDrawable(d);
		} catch (IOException ex) {
			return;
		}

		image = (ImageView) findViewById(R.id.imageView2);

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
	
	
	public void atualizaMapa () {
		
		googleMap.clear();
		
		/* TODO pegar lista de jogadores do servidor
		for (Jogador jogador : listaJogadores) {
			mostraRiscos(jogador);
			mostraAreas(jogador);
		}*/
		
		mostraRiscos(jogador2);
		mostraAreas(jogador2);
		mostraRiscos(jogador);
		mostraAreas(jogador);
		
	}
	
	public void mostraArea (Area area, int cor) {
		
		CircleOptions viewArea = new CircleOptions();
		viewArea.center(new LatLng(area.getLatitude(), area.getLongitude()));
		viewArea.radius(area.getRaio());
		viewArea.fillColor(cor);
		viewArea.strokeWidth(0);
		googleMap.addCircle(viewArea);
	}
	
	public void mostraAreas (Jogador jogador) {
		int cor = jogador.getCor();
		
		cor = cor - 0xaa000000;
		List<Area> listaAreas = jogador.getListaAreas();
		if (listaAreas.isEmpty()) {
			return;
		}
		
		for (Area area : listaAreas) {
			mostraArea(area, cor);
		}
		
	}

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
		}

		viewRiscos.color(jogador.getCor());
		viewRiscos.width(5);
		googleMap.addPolyline(viewRiscos);
		
		for (Ponto ponto : listaPontos) {
			mostraPonto(ponto, jogador.getCor());
		}
	}
	
	public void novaArea (View view) {
		GPSTracker gps = new GPSTracker(this);

		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();
		
		Area area = new Area (latitude, longitude);
		
		jogador.adicionaArea(area);
		
		atualizaMapa();
	}

	public void novoPonto(View view) {
		GPSTracker gps = new GPSTracker(this);

		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();

		Ponto ponto = new Ponto(latitude, longitude);
		
		jogador.adicionaPonto(ponto);
		
		if (jogador.getQuantPontos()>1) {
			checaColisoes(jogador.getListaPontos().get(jogador.getQuantPontos()-2), ponto);
		}
		
		atualizaMapa();

		//Toast.makeText(this, "Novo ponto criado! Sua pontuacao:" + jogador.getPontuacao(),Toast.LENGTH_SHORT).show();
	}
	
	public void checaColisoes (Ponto novo1, Ponto novo2) {
		double distancia = novo1.distancia(novo2);
		double distvelho;
		//TODO acessar servidor, coletar lista de jogadores, para cada jogador checar interseccao de cada reta
		if (jogador2.getQuantPontos()>1) {
			int i = 0;
			List<Ponto> listaPontos = jogador2.getListaPontos();
			for (i=0;i<jogador2.getQuantPontos()-1;i++) {
				if (checarInterseccaoReta(novo1, novo2, listaPontos.get(i), listaPontos.get(i+1))) {
					Random rand = new Random(System.currentTimeMillis());
					double numAleatorio = rand.nextDouble();
					
					distvelho = listaPontos.get(i).distancia(listaPontos.get(i+1));
					
					numAleatorio = numAleatorio * (distancia + distvelho);
					if (numAleatorio < distvelho) {
						Toast.makeText(this, "Ganhou a luta",Toast.LENGTH_SHORT).show();
						destroi(jogador2, listaPontos.get(i+1));
						//TODO avisar jogador2 q perdeu
					}
					else {
						Toast.makeText(this, "Perdeu a luta",Toast.LENGTH_SHORT).show();
						destroi(jogador, novo2);
					}
				}
			}
		}
	}
	
	public double max (double a, double b) {
		if (a > b) {
			return a;
		}
		else {
			return b;
		}
	}
	
	public double min (double a, double b) {
		if (a < b) {
			return a;
		}
		else {
			return b;
		}
	}
	
	
	public boolean checarInterseccaoReta(Ponto novo1, Ponto novo2, Ponto velho1, Ponto velho2){	
		
		double result, a, b;
		
		//Teste do envelope
		if (max(novo1.getLatitude(),novo2.getLatitude()) < min(velho1.getLatitude(),velho2.getLatitude())) {
			return false;
		}
		if (min(novo1.getLatitude(),novo2.getLatitude()) > max(velho1.getLatitude(),velho2.getLatitude())) {
			return false;
		}
		if (max(novo1.getLongitude(),novo2.getLongitude()) < min(velho1.getLongitude(),velho2.getLongitude())) {
			return false;
		}
		if (min(novo1.getLongitude(),novo2.getLongitude()) > max(velho1.getLongitude(),velho2.getLongitude())) {
			return false;
		}
		
		//equacao da reta y=ax+b
		a = ((novo1.getLatitude() - novo2.getLatitude())/(novo1.getLongitude() - novo2.getLongitude()));
		b = novo1.getLatitude() - (novo1.getLongitude() * a);
		if (MesmoLado(a, b, velho1, velho2)) {
			return false;
		}
		
		a = ((velho1.getLatitude() - velho2.getLatitude())/(velho1.getLongitude() - velho2.getLongitude()));
		b = velho1.getLatitude() - (velho1.getLongitude() * a);
		if (MesmoLado(a, b, novo1, novo2)) {
			return false;
		}
		
		return true;
		
		//parte antiga que nao funcionou
		/*if(determinante(novo1, novo2, velho1, velho2) == 0.0) {
			return false; //nao ha interseccao
		}
		return true; //ha interseccao*/
		
		/*Line2D line1 = new Line2D.Float(100, 100, 200, 200);
		return linesIntersect(
				novo1.getLongitude(),
				novo1.getLatitude(),
				novo2.getLongitude(),
				novo2.getLatitude(),
				velho1.getLongitude(),
				velho1.getLatitude(),
				velho2.getLongitude(),
				velho2.getLatitude());*/
	}
	
	//retorna true se os dois pontos estao no mesmo lado da reta
	public boolean MesmoLado(double a, double b, Ponto ponto1, Ponto ponto2) {
		double x, y;
		
		x = ponto1.getLongitude();
		y = (-1*ponto1.getLatitude());
		if (((a*x) + y + b) > 0) {
			x = ponto2.getLongitude();
			y = (-1*ponto2.getLatitude());
			if (((a*x) + y + b) > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			x = ponto2.getLongitude();
			y = (-1*ponto2.getLatitude());
			if (((a*x) + y + b) < 0) {
				return true;
			}
			else {
				return false;
			}
		}		
	}
	
	public double determinante(Ponto novo1, Ponto novo2, Ponto velho1, Ponto velho2){
		//determinante
		double det;

		//x's e y's da reta 1 (atual)
		double xIni1 = novo1.getLongitude();
		double xFim1 = novo2.getLongitude();
		double yIni1 = novo1.getLatitude();
		double yFim1 = novo2.getLatitude();

		//x's e y's da reta 2 (a ser comparada)
		double xIni2 = velho1.getLongitude();
		double xFim2 = velho2.getLongitude();	
		double yIni2 = velho1.getLatitude();
		double yFim2 = velho2.getLatitude();	

		det = (xFim2 - xIni2)*(yFim1 - yIni1) - (yFim2 - yIni2)*(xFim1 - xIni1);
		return det;
	}
	
	public void destroi(Jogador jogador, Ponto ponto) {
		List<Ponto> listaPontos = jogador.getListaPontos();
		
		if (listaPontos.indexOf(ponto) == jogador.getQuantPontos()-1) {
			listaPontos.remove(ponto);
			return;
		}
		if (listaPontos.indexOf(ponto) == 1) {
			listaPontos.remove(0);
			return;
		}
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