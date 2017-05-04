package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="compre_ganhe")
@NamedQueries({
	@NamedQuery(name="ClienteCompreGanhe.pontos", query="SELECT c FROM ClienteCompreGanhe c WHERE c.cliente.idPessoa=:idPessoa")	
})
public class ClienteCompreGanhe implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_compre_ganhe")
	private long idCompreGanhe;
	
	@Column(name="nr_quantidade_pontos")
	private double nrQuantidadePontos;
	
	@Column(name="sn_status")
	private boolean snStatus;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;

	public long getIdCompreGanhe() {
		return idCompreGanhe;
	}

	public void setIdCompreGanhe(long idCompreGanhe) {
		this.idCompreGanhe = idCompreGanhe;
	}
	
	public double getNrQuantidadePontos() {
		return nrQuantidadePontos;
	}

	public void setNrQuantidadePontos(double nrQuantidadePontos) {
		this.nrQuantidadePontos = nrQuantidadePontos;
	}

	public boolean getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(boolean snStatus) {
		this.snStatus = snStatus;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCompreGanhe ^ (idCompreGanhe >>> 32));
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
		ClienteCompreGanhe other = (ClienteCompreGanhe) obj;
		if (idCompreGanhe != other.idCompreGanhe)
			return false;
		return true;
	}
}
