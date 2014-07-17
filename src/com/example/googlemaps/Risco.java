package com.example.googlemaps;

public class Risco {

	private Ponto pontoInicial, pontoFinal;
	private double tamanho, forca;
	
	//construtor de risco
	Risco(Ponto inicial, Ponto fim){
		this.pontoInicial = inicial;
		this.pontoFinal = fim;
		//calcular tamanho
		//calcular forca
	}
	
	//get e set pontoInicial
	public Ponto getPontoInicial() {
		return pontoInicial;
	}
	public void setPontoInicial(Ponto pontoInicial) {
		this.pontoInicial = pontoInicial;
	}
	
	//get e set pontoFinal
	public Ponto getPontoFinal() {
		return pontoFinal;
	}
	public void setPontoFinal(Ponto pontoFinal) {
		this.pontoFinal = pontoFinal;
	}
	
	//get e set do tamanho
	public double getTamanho() {
		return tamanho;
	}
	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}
	
	//get e set da forca
	public double getForca() {
		return forca;
	}
	public void setForca(double forca) {
		this.forca = forca;
	}
	
	/*
	public int checarInterseccao(Risco risco){	
		if(determinante(risco) == 0.0) {
			return 0; //nao ha interseccao
		}
		return 1; //ha interseccao
	}
	
	
	double determinante(Risco risco){
		//determinante
		double det;

		//x's e y's da reta 1 (atual)
		double xIni1 = pontoInicial.getLatitude();
		double xFim1 = pontoFinal.getLatitude();
		double yIni1 = pontoInicial.getLongitude();
		double yFim1 = pontoFinal.getLongitude();

		//x's e y's da reta 2 (a ser comparada)
		double xIni2 = risco.getPontoInicial().getLatitude();
		double xFim2 = risco.getPontoFinal().getLatitude();	
		double yIni2 = risco.getPontoInicial().getLongitude();
		double yFim2 = risco.getPontoFinal().getLongitude();	

		det = (xFim2 - xIni2)*(yFim1 - yIni1) - (yFim2 - yIni2)*(xFim1 - xIni1);
		return det;
	}*/

}
