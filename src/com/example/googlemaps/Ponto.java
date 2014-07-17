package com.example.googlemaps;

public class Ponto {
	private double latitude, longitude;
	
	//int do tipo 0xff223344
	//ff é a transparencia. 22 red, 33 green, 44 blue
	private int rgb;
	
	//construtor de ponto
	Ponto (double y, double x, int rgb){
		this.latitude = y;
		this.longitude = x;
		this.rgb = rgb;
	}
	
	//get e set da latitude
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double y) {
		latitude = y;
	}
	
	//get e set da longitude
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double x) {
		longitude = x;
	}
	
	//get e set das cores RGB
	public int getRGB(){
		return rgb;
	}
	public void setRGB(int rgb){
		this.rgb = rgb;
	}
	
	
}
