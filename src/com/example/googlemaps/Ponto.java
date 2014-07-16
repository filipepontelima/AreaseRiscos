package com.example.googlemaps;

public class Ponto {
	private double coordX, coordY;
	
	//string do tipo #123456
	private String rgb;
	
	//construtor de ponto
	Ponto (double x, double y, String rgb){
		this.coordX = x;
		this.coordY = y;
		this.rgb = rgb;
	}
	
	//get e set da coordX
	public double getCoordX() {
		return coordX;
	}
	public void setCoordX(double x) {
		coordX = x;
	}
	
	//get e set da coordY
	public double getCoordY() {
		return coordY;
	}
	public void setCoordY(double y) {
		coordY = y;
	}
	
	//get e set das cores RGB
	public String getRGB(){
		return rgb;
	}
	public void setRGB(String rgb){
		this.rgb = rgb;
	}
	
	
}
