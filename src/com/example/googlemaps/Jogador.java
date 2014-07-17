package com.example.googlemaps;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
	
	String nome;
	//int do tipo 0xff223344
	//ff é a transparencia. 22 red, 33 green, 44 blue
	private int cor;
	private double pontuacao;
	private List<Ponto> listaPontos = new ArrayList<Ponto>();
	
	
	//construtor
	Jogador (String nome, int cor) {
		this.nome = nome;
		this.cor = cor;
		pontuacao = 0;
	}
	
	public void setNome (String nome) {
		this.nome = nome;
	}
	public String getNome () {
		return nome;
	}
	
	public void setCor (int cor) {
		this.cor = cor;
	}
	public int getCor () {
		return cor;
	}
	
	public void setPontuacao (double pontuacao) {
		this.pontuacao = pontuacao;
	}
	public void adicionaPontuacao (double pontos) {
		pontuacao = pontuacao + pontos;
	}
	public double getPontuacao () {
		return pontuacao;
	}
	
	public void setListaPontos (List<Ponto> listaPontos) {
		this.listaPontos = listaPontos;
	} 
	public void adicionaPonto (Ponto ponto) {
		
		if (listaPontos.size()>0) {
			adicionaPontuacao (ponto.distancia(listaPontos.get(listaPontos.size()-1)));
		}
		
		listaPontos.add(ponto);
	}
	public List<Ponto> getListaPontos () {
		return listaPontos;
	}
}
