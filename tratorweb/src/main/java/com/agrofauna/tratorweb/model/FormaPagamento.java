package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="forma_pagamento")
public class FormaPagamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_forma_pagamento")
	private long idFormaPagamento;
	
	@Column(name="nm_forma_pagamento")
	private String nmFormaPagamento;
	
	@Column(name="sn_status")
	private int snStatus;

	@OneToMany(mappedBy="formaPagamento", fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public long getIdFormaPagamento() {
		return idFormaPagamento;
	}

	public void setIdFormaPagamento(long idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}

	public String getNmFormaPagamento() {
		return nmFormaPagamento;
	}

	public void setNmFormaPagamento(String nmFormaPagamento) {
		this.nmFormaPagamento = nmFormaPagamento;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idFormaPagamento ^ (idFormaPagamento >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormaPagamento other = (FormaPagamento) obj;
		if (idFormaPagamento != other.idFormaPagamento)
			return false;
		return true;
	}
}
