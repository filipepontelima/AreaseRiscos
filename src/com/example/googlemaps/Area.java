package com.example.googlemaps;

public class Area {
	private double latitude, longitude;
	private double raio;
	
	
	//construtor de area
	Area (double y, double x){
		this.latitude = y;
		this.longitude = x;
		raio = 30;
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
	
	public void setLatLng(double lat, double lng) {
		latitude = lat;
		longitude = lng;
	}
	
	

}
