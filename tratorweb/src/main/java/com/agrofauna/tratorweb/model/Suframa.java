package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;


/**
 * The persistent class for the suframa database table.
 * 
 */
@Entity
@Table(name="suframa")
@NamedQuery(name="Suframa.clienteSuframa", query="SELECT s FROM Cliente c inner join c.suframas s WHERE c.idPessoa=:idPessoa")
public class Suframa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_suframa")
	private long idSuframa;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
		
	@Column(name="nm_suframa")
	private String nmSuframa;
		
	@Column(name="sn_regular")
	private int snRegular;
	
	@Column(name="sn_isencao")
	private int snIsencao;
	
	@Column(name="sn_status")
	private int snStatus;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;

	public long getIdSuframa() {
		return idSuframa;
	}

	public void setIdSuframa(long idSuframa) {
		this.idSuframa = idSuframa;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmSuframa() {
		return nmSuframa;
	}

	public void setNmSuframa(String nmSuframa) {
		this.nmSuframa = nmSuframa;
	}

	public int getSnRegular() {
		return snRegular;
	}

	public void setSnRegular(int snRegular) {
		this.snRegular = snRegular;
	}

	public int getSnIsencao() {
		return snIsencao;
	}

	public void setSnIsencao(int snIsencao) {
		this.snIsencao = snIsencao;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
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
		result = prime * result + (int) (idSuframa ^ (idSuframa >>> 32));
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
		Suframa other = (Suframa) obj;
		if (idSuframa != other.idSuframa)
			return false;
		return true;
	}
}