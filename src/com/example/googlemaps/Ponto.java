package com.example.googlemaps;

public class Ponto {
	private float coordX, coordY;
	
	//deixei a cor como valores RGB enquanto nao sei usar a cor da engine
	private String rgb;
	
	//get e set da coordX
	public float getCoordX() {
		return coordX;
	}
	public void setCoordX(float x) {
		coordX = x;
	}
	
	//get e set da coordY
	public float getCoordY() {
		return coordY;
	}
	public void setCoordY(float y) {
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
