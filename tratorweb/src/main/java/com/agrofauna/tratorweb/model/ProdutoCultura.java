package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="produto_cultura")
public class ProdutoCultura implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_cultura")
	private long idProdutoCultura;
	
	@Column(name="nm_pragas1")
	private String nmPragas1;
	
	@Column(name="nm_pragas2")
	private String nmPragas2;
	
	@Column(name="sn_status")
	private int snStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cultura")
	private Cultura cultura;

	public long getIdProdutoCultura() {
		return idProdutoCultura;
	}

	public void setIdProdutoCultura(long idProdutoCultura) {
		this.idProdutoCultura = idProdutoCultura;
	}

	public String getNmPragas1() {
		return nmPragas1;
	}

	public void setNmPragas1(String nmPragas1) {
		this.nmPragas1 = nmPragas1;
	}

	public String getNmPragas2() {
		return nmPragas2;
	}

	public void setNmPragas2(String nmPragas2) {
		this.nmPragas2 = nmPragas2;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProdutoCultura ^ (idProdutoCultura >>> 32));
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
		ProdutoCultura other = (ProdutoCultura) obj;
		if (idProdutoCultura != other.idProdutoCultura)
			return false;
		return true;
	}
}
