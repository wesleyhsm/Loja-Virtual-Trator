package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

import com.agrofauna.tratorweb.model.Cliente;

public class FiltroPedido implements Serializable{

	private static final long serialVersionUID = 1L;

	private int primeiroRegistro;
	private int quantidadeRegistro;
	private Cliente cliente;
		
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
}
