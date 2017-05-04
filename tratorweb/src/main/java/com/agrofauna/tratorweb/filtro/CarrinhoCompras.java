package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

public class CarrinhoCompras implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long idProdutoCampanha;
	private long idCampanha;
	private long idProduto;
	private String nomeProduto;
	private double valorUnitario;
	private int qtdPorCaixa;
	private int qtdProduto;	
	private double subTotal;
	private double classificaPrecoEncomenda;		
		
	public double getClassificaPrecoEncomenda() {
		return classificaPrecoEncomenda;
	}
	public void setClassificaPrecoEncomenda(double classificaPrecoEncomenda) {
		this.classificaPrecoEncomenda = classificaPrecoEncomenda;
	}
	public long getIdCampanha() {
		return idCampanha;
	}
	public void setIdCampanha(long idCampanha) {
		this.idCampanha = idCampanha;
	}
	public long getIdProdutoCampanha() {
		return idProdutoCampanha;
	}
	public void setIdProdutoCampanha(long idProdutoCampanha) {
		this.idProdutoCampanha = idProdutoCampanha;
	}
	public long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(long idProduto) {
		this.idProduto = idProduto;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public int getQtdPorCaixa() {
		return qtdPorCaixa;
	}
	public void setQtdPorCaixa(int qtdPorCaixa) {
		this.qtdPorCaixa = qtdPorCaixa;
	}
	public int getQtdProduto() {
		return qtdProduto;
	}
	public void setQtdProduto(int qtdProduto) {
		this.qtdProduto = qtdProduto;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
}
