package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

public class FiltroPersonalizarProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int primeiroRegistro;
	private int quantidadeRegistro;
	private String nomePesquisa;
	private int tipoPesquisa;	
	
	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}
	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}
	public int getQuantidadeRegistro() {
		return quantidadeRegistro;
	}
	public void setQuantidadeRegistro(int quantidadeRegistro) {
		this.quantidadeRegistro = quantidadeRegistro;
	}
	public String getNomePesquisa() {
		return nomePesquisa;
	}
	public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}
	public int getTipoPesquisa() {
		return tipoPesquisa;
	}
	public void setTipoPesquisa(int tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}
}
