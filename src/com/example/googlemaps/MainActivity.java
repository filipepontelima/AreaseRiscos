package com.example.googlemaps;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.unbiquitous.network.http.connection.ClientMode;
import org.unbiquitous.uos.core.UOS;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;

import servidor.Area;
import servidor.Jogador;
import servidor.Ponto;
import android.bluetooth.BluetoothClass.Device;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
//import android.R;

public class MainActivity extends FragmentActivity {
	
	
	
	// Google Map
	private GoogleMap googleMap;

	// Button button;
	ImageView image;

	// Jogadores
	List<Jogador> listaJogadores = new ArrayList<Jogador>();
	Jogador jogador, jogador2;
	
	private Gateway gateway;
	UOS uos = new UOS();
	UpDriver driver = new UpDriver("cliente");

	private RelativeLayout telainicial;
	private LinearLayout fimdejogo;
	private RelativeLayout telamapa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//retira o titulo de cima
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		AsyncTask midlewareTask = new AsyncTask(){

			@Override
			protected Object doInBackground(Object... arg0) {
				// TODO Auto-generated method stub
				//settar cliente
				
			    ClientMode.Properties props = new ClientMode.Properties();
				//InitialProperties props = new MulticastRadar.Properties();
			    props.setServer("192.168.0.38");
			    uos.start(props);

				return null;
			}};
			
		midlewareTask.execute(null, null, null);
		LayoutInflater inflater = LayoutInflater.from(this);
		setContentView(R.layout.tela_inicial);
		telainicial = (RelativeLayout) this.findViewById(R.id.main);
		telamapa = (RelativeLayout) inflater.inflate(R.layout.activity_main,null);
		fimdejogo = (LinearLayout) inflater.inflate(R.layout.fim_de_jogo,null);

		
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
		
		gateway = uos.getGateway();
		List<UpDevice> listaDevices;
		listaDevices = gateway.listDevices();
		int quant = listaDevices.size();
		
		//Toast.makeText(this, "Começou!", Toast.LENGTH_SHORT).show(); 
		
		
		//loop pra pegar o servidor
		UpDevice device;
		for(int i=0; i<quant; i++){
			if(listaDevices.get(i) != gateway.getCurrentDevice()){
				device = listaDevices.get(i);
				Toast.makeText(this, "device settado", Toast.LENGTH_SHORT).show();
			}
		}
		
		/*Call novoJogador = new Call("uos.aerdriver","adicionarJogador");
		novoJogador.addParameter("jogador", jogador2);
		Response r = gateway.callService(driver.getDevice(), novoJogador);
		String result = r.getResponseString("result");
		if(result == "win"){
			// parabeniza jogador
		}*/
			
		// setContentView(R.layout.tela_inicial);
		// setContentView(R.layout.activity_main);
		loadAsset();
		
		
		

		//inicializaMapa();

	}

	public void inicializaMapa(View view) {
		
		//loadAsset();
		jogador = new Jogador ("Filipe", 0xfffaa648);
		listaJogadores.add(jogador);
		listaJogadores.add(jogador2);
		
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
	

	public void fimDeJogo(View view) {
		TextView placarNomes, placarPontos;
		String pontuacao;
		//loadAsset();
		int i, j;
		double pontuacaoAreas = 0;
		
		for (i=0;i < jogador.getQuantAreas(); i++) {
			for(Jogador jog : listaJogadores){
				if (jog != jogador) {
					for (j=0; j<jog.getQuantAreas(); j++) {
						pontuacaoAreas += jogador.getListaAreas().get(i).interseccaoArea(jog.getListaAreas().get(j));
					}
				}
			}
		}
		//TODO corrigir esta parte
		jogador.setPontuacaoAreas(pontuacaoAreas);
		jogador2.setPontuacaoAreas(pontuacaoAreas);
		
		
		
		
		setContentView(fimdejogo);
		//para cada jogador (Riscos)
		
		placarNomes = (TextView)findViewById(R.id.placarNomes);
		placarNomes.setText("\nRiscos:\n\nJogadores\n");
		for (Jogador jog : listaJogadores) {
			placarNomes.append(jog.getNome() + "\n");
		}
		//placarNomes.append(jogador2.getNome() + "\n");
		
		placarPontos = (TextView)findViewById(R.id.placarPontos);
		placarPontos.setText("\n\n\nMetros\n");
		for (Jogador jog : listaJogadores) {
			pontuacao = String.format("%.0f", jog.getPontuacaoRiscos());
			placarPontos.append(pontuacao + "\n");
		}
		//pontuacao = String.format("%.0f", jogador2.getPontuacaoRiscos());
		//placarPontos.append(pontuacao + "\n");
		
		
		placarNomes.append("\n\nÁreas:\n\nJogadores\n");
		for (Jogador jog : listaJogadores) {
			placarNomes.append(jog.getNome() + "\n");
		}
		//placarNomes.append(jogador2.getNome() + "\n");
		
		placarPontos.append("\n\n\n\nÁreas\n");
		for (Jogador jog : listaJogadores) {
			pontuacao = String.format("%.0f", jog.getPontuacaoAreas());
			placarPontos.append(pontuacao + "\n");
		}
		//pontuacao = String.format("%.0f", jogador2.getPontuacaoAreas());
		//placarPontos.append(pontuacao + "\n");
		
	}
	
	public void novoJogo(View view) {
		//limpar variaveis
		//TODO corrigir clean
		jogador = null;
		setContentView(telainicial);
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
		
		for(Jogador jog : listaJogadores){
			mostraRiscos(jog);
			mostraAreas(jog);
		}
		/*mostraRiscos(jogador2);
		mostraAreas(jogador2);
		mostraRiscos(jogador);
		mostraAreas(jogador);*/
		
		
		
//		MOSTRA AREAS E RISCOS DE TODOS OS JOGADORES
//		Call getJogadores = new Call("uos.aerdriver","getJogadores");
//		Response r = gateway.callService(target, getJogadores);
//		Set<Jogador> jogadores = r.getResponseData("result"); 
//		for(Jogador j : jogadores){
//			mostraRiscos(j);
//			mostraAreas(j);
//		}
		
		
		
		//calcula e mostra pontuacao das areas;
		
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
		viewPonto.fillColor(cor);
		viewPonto.strokeColor(cor);
		viewPonto.strokeWidth(4);
		googleMap.addCircle(viewPonto);
	}

	public void mostraRiscos(Jogador jogador) {
		
		
		
		List<Ponto> listaPontos = jogador.getListaPontos();
		if (listaPontos.isEmpty()) {
			return;
		}
		
		for (Ponto ponto : listaPontos) {
			mostraPonto(ponto, jogador.getCor());
		}
		
		PolylineOptions viewRiscos = new PolylineOptions();

		for (Ponto ponto : listaPontos) {
			viewRiscos.add(new LatLng(ponto.getLatitude(), ponto.getLongitude()));
		}

		viewRiscos.color(jogador.getCor());
		viewRiscos.width(5);
		googleMap.addPolyline(viewRiscos);
		
		
	}
	
	public void novaArea (View view) {

		GPSTracker gps = new GPSTracker(this);
		
		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();
		int indexArea;
		double pontuacao = 0;
		
		//mudar para if esta dentro de uma de suas areas
		//dentro do if: areaExistente =
		Ponto ponto = new Ponto(latitude, longitude);
		indexArea = jogador.contidoEmAlgumaArea(ponto);
		if (indexArea >=0) {
			jogador.getListaAreas().get(indexArea).aumentaRaio(50);
		} else {
			if (jogador.atingiuMaxAreas()) {
				Toast.makeText(this, "Você já usou todas suas areas",Toast.LENGTH_SHORT).show();
			} else {
				Area area = new Area (latitude, longitude);
				jogador.adicionaArea(area);
				
				//pontuacao = area.interseccaoArea(jogador2.getListaAreas().get(0)) ;
				//Toast.makeText(this, "Pontuacao das Areas:" + pontuacao,Toast.LENGTH_SHORT).show();
			}
		}
		
		
		
		//CHAMADA PARA O SERVIDOR CRIAR A AREA
//		Call marcarArea = new Call("uos.aerdriver","marcarArea");
//		marcarArea.addParameter("longitude", longitude);
//		marcarArea.addParameter("latitude", latitude);
//		Response r = gateway.callService(target, marcarArea);
//		String result = r.getResponseString("result");
//		if(result == "1"){
//			//Toast.makeText(this, "ERRO 1: jogador inexistente",Toast.LENGTH_SHORT).show();
//		}
//		if(result == "2"){
//		//Toast.makeText(this, "ERRO 2: limite de areas alcancado",Toast.LENGTH_SHORT).show();
//		}	
		
		

		
		//int i, j;
		
		/*for (i=0; i<jogador.getQuantAreas(); i++) {
			for (j=0; j<jogador2.getQuantAreas(); j++) {
				pontuacao = pontuacao + jogador.getListaAreas().get(i).interseccaoArea(jogador2.getListaAreas().get(j));
			}
		}*/
		 
		//jogador.setPontuacaoAreas (pontuacao);
		
		
		
		atualizaMapa();
	}

	public void novoPonto(View view) {
		
		if (jogador.atingiuMaxPontos()) {
			Toast.makeText(this, "Você já usou todos seus pontos",Toast.LENGTH_SHORT).show();
		} else {
			GPSTracker gps = new GPSTracker(this);
	
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
	
			Ponto ponto = new Ponto(latitude, longitude);
			
			
			//Fazer o call para o servidor a partir daqui
			if (jogador.getQuantPontos()>=1) {
				if (ponto.distancia(jogador.getUltimoPonto()) < 0.001) {
					Toast.makeText(this, "Não pode criar um ponto muito próximo do anterior!",Toast.LENGTH_SHORT).show();
				} else {
					jogador.adicionaPonto(ponto);
					checaColisoes(jogador.getListaPontos().get(jogador.getQuantPontos()-2), ponto);
					Toast.makeText(this, "Sua pontuacao:" + jogador.getPontuacaoRiscos(),Toast.LENGTH_SHORT).show();
				}
			} else {
				jogador.adicionaPonto(ponto);
			}
		}
		
		
		//CHAMADA PARA O SERVIDOR CRIAR O PONTO
//		Call marcarPonto = new Call("uos.aerdriver","marcarPonto");
//		marcarPonto.addParameter("ponto", ponto);
//		Response r = gateway.callService(target, marcarPonto);
//		String result = r.getResponseString("result");
//		if(result == "1"){
//			//Toast.makeText(this, "ERRO 1: jogador inexistente",Toast.LENGTH_SHORT).show();
//		}
//		if(result == "2"){
//		//Toast.makeText(this, "ERRO 2: ponto muito proximo ao ultimo marcado",Toast.LENGTH_SHORT).show();
//		}	

		
		
		atualizaMapa();
	}
	
	public void checaColisoes (Ponto novo1, Ponto novo2) {
		double distancia = novo1.distancia(novo2);
		double distvelho;
		boolean perdeu = false;
		//TODO acessar servidor, coletar lista de jogadores, para cada jogador checar interseccao de cada reta
		for(Jogador jog : listaJogadores){
			if (jog != jogador) {
				if (jog.getQuantPontos()>1) {
					int i = 0;
					List<Ponto> listaPontos = jog.getListaPontos();
					for (i=0;i<jog.getQuantPontos()-1;i++) {
						if (checarInterseccaoReta(novo1, novo2, listaPontos.get(i), listaPontos.get(i+1))) {
							Random rand = new Random(System.currentTimeMillis());
							double numAleatorio = rand.nextDouble();
							
							distvelho = listaPontos.get(i).distancia(listaPontos.get(i+1));
							
							numAleatorio = numAleatorio * (distancia + distvelho);
							if (numAleatorio < distvelho) {
								Toast.makeText(this, "Ganhou a luta",Toast.LENGTH_SHORT).show();
								destroi(jog, listaPontos.get(i+1));
								jog.atualizaPontuacaoRiscos();
								i=-1;
								//TODO avisar jog q perdeu
							}
							else {
								Toast.makeText(this, "Perdeu a luta",Toast.LENGTH_SHORT).show();
								perdeu = true;
							}
						}
					}
				}
			}
		}
		
		if (perdeu == true) {
			destroi(jogador, novo2);
			jogador.atualizaPontuacaoRiscos();
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
		
		double a, b;
		
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
		double distancia1, distancia2;
		int i, index;
		
		if (listaPontos.indexOf(ponto) == jogador.getQuantPontos()-1) {
			listaPontos.remove(ponto);
			jogador.decrementaQuantPontos();
			return;
		}
		if (listaPontos.indexOf(ponto) == 1) {
			listaPontos.remove(0);
			jogador.decrementaQuantPontos();
			return;
		}
		
		distancia1=0;
		distancia2=0;
		
		for (i=0; i < listaPontos.indexOf(ponto)-1; i++) {
			distancia1 = distancia1 + listaPontos.get(i).distancia(listaPontos.get(i+1));
		}
		
		for (i=listaPontos.indexOf(ponto); i < jogador.getQuantPontos()-1; i++) {
			distancia2 = distancia2 + listaPontos.get(i).distancia(listaPontos.get(i+1));
		}
		
		index = listaPontos.indexOf(ponto);
		if (distancia1 > distancia2) {
			while (jogador.getQuantPontos() > index) {
				//remove ultimo da lista
				listaPontos.remove(jogador.getQuantPontos()-1);
				jogador.decrementaQuantPontos();
			}
		} else {
			while (listaPontos.get(0) != ponto) {
				listaPontos.remove(0);
				jogador.decrementaQuantPontos();
			}
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