package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

public class FiltroPesquisaProdutoIndex implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int primeiroRegistro;
	private int quantidadeRegistro;
	private String nomeProduto;
	private String nomePrincipioAtivo;
	private int ordenacao;
	
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
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getNomePrincipioAtivo() {
		return nomePrincipioAtivo;
	}
	public void setNomePrincipioAtivo(String nomePrincipioAtivo) {
		this.nomePrincipioAtivo = nomePrincipioAtivo;
	}
	public int getOrdenacao() {
		return ordenacao;
	}
	public void setOrdenacao(int ordenacao) {
		this.ordenacao = ordenacao;
	}
}
