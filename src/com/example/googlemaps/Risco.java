package com.example.googlemaps;

public class Risco {

	private Ponto pontoInicial, pontoFinal;
	private float tamanho, forca;
	
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
	public float getTamanho() {
		return tamanho;
	}
	public void setTamanho(float tamanho) {
		this.tamanho = tamanho;
	}
	
	//get e set da forca
	public float getForca() {
		return forca;
	}
	public void setForca(float forca) {
		this.forca = forca;
	}

}
