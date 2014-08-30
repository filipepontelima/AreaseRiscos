package com.example.googlemaps;

import java.util.ArrayList;
import java.util.List;

import servidor.Area;

public class Jogador {
	
	String nome;
	//int do tipo 0xff223344
	//ff é a transparencia. 22 red, 33 green, 44 blue
	private int cor;
	private double pontuacaoRiscos;
	private double pontuacaoAreas;
	private int quantPontos;
	private int quantAreas;
	private List<Ponto> listaPontos = new ArrayList<Ponto>();
	private List<Area> listaAreas = new ArrayList<Area>();
	
	
	//construtor
	public Jogador (String nome, int cor) {
		this.nome = nome;
		this.cor = cor;
		pontuacaoRiscos = 0;
		pontuacaoAreas = 0;
		quantPontos = 0;
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
	
	public void setPontuacaoRiscos (double pontuacao) {
		this.pontuacaoRiscos = pontuacao;
	}
	public void adicionaPontuacaoRiscos (double pontos) {
		pontuacaoRiscos = pontuacaoRiscos + pontos;
	}
	public double getPontuacaoRiscos () {
		return pontuacaoRiscos;
	}
	
	public void setPontuacaoAreas (double pontuacao) {
		this.pontuacaoAreas = pontuacao;
	}
	public void adicionaPontuacaoAreas (double pontos) {
		pontuacaoAreas = pontuacaoAreas + pontos;
	}
	public double getPontuacaoAreas () {
		return pontuacaoAreas;
	}
	
	public void setListaPontos (List<Ponto> listaPontos) {
		this.listaPontos = listaPontos;
	} 
	public void adicionaPonto (Ponto ponto) {
		
		if (listaPontos.size()>0) {
			adicionaPontuacaoRiscos (ponto.distancia(listaPontos.get(listaPontos.size()-1)));
		}
		
		listaPontos.add(ponto);
		quantPontos ++;
	}
	public List<Ponto> getListaPontos () {
		return listaPontos;
	}
	
	public void setListaAreas (List<Area> listaAreas) {
		this.listaAreas = listaAreas;
	} 
	public void adicionaArea (Area area) {
				
		listaAreas.add(area);
		quantAreas ++;
	}
	public List<Area> getListaAreas () {
		return listaAreas;
	}
	
	public void setQuantPontos (int n) {
		quantPontos = n;
	}
	public void incrementaQuantPontos () {
		quantPontos ++;
	}
	public void decrementaQuantPontos () {
		quantPontos --;
	}
	public int getQuantPontos () {
		return quantPontos;
	}
	
	public void setQuantAreas (int n) {
		quantAreas = n;
	}
	public void incrementaQuantAreas () {
		quantAreas ++;
	}
	public void decrementaQuantAreas () {
		quantAreas --;
	}
	public int getQuantAreas () {
		return quantAreas;
	}
}
