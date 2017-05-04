package com.agrofauna.tratorweb.filtro;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.PrincipioAtivo;

public class FiltroCampanhaProduto {

	private int primeiroRegistro;
	private int quantidadeRegistro;
	private String descricao;
	private String tipoConsulta;
	private int ordenacao;
	private int promocaoSmartMail;
	private Cliente cliente;
	private PrincipioAtivo principioAtivo = new PrincipioAtivo();	
	
	public int getPromocaoSmartMail() {
		return promocaoSmartMail;
	}
	public void setPromocaoSmartMail(int promocaoSmartMail) {
		this.promocaoSmartMail = promocaoSmartMail;
	}
	public int getOrdenacao() {
		return ordenacao;
	}
	public void setOrdenacao(int ordenacao) {
		this.ordenacao = ordenacao;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoConsulta() {
		return tipoConsulta;
	}
	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}
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
	public PrincipioAtivo getPrincipioAtivo() {
		return principioAtivo;
	}
	public void setPrincipioAtivo(PrincipioAtivo principioAtivo) {
		this.principioAtivo = principioAtivo;
	}
}
