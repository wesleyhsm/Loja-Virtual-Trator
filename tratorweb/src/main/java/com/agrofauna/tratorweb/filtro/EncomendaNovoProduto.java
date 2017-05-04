package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class EncomendaNovoProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String nomeProduto;
	private int qtdProduto = 0;
	private double precoInicio = 0.00;
	private double precoFinal = 0.00;
	private String msgObservacao;
	
	public String getNomeProduto() {
		return nomeProduto;
	}
	
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	
	public int getQtdProduto() {
		return qtdProduto;
	}
	
	public void setQtdProduto(int qtdProduto) {
		this.qtdProduto = qtdProduto;
	}
	
	public double getPrecoInicio() {
		return precoInicio;
	}
	
	public void setPrecoInicio(double precoInicio) {
		this.precoInicio = precoInicio;
	}
	
	public double getPrecoFinal() {
		return precoFinal;
	}
	
	public void setPrecoFinal(double precoFinal) {
		this.precoFinal = precoFinal;
	}
	
	public String getMsgObservacao() {
		return msgObservacao;
	}
	
	public void setMsgObservacao(String msgObservacao) {
		this.msgObservacao = msgObservacao;
	}
}
