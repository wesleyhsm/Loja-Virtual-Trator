package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="produto_tributo_especial")
@NamedQueries({
	@NamedQuery(name="ProdutoTributoEspecial.findAll", query="SELECT t FROM ProdutoTributoEspecial t"),
	@NamedQuery(name="ProdutoTributoEspecial.produto", query="SELECT t FROM ProdutoTributoEspecial t WHERE t.produto.idProduto=:idProduto")
})	
public class ProdutoTributoEspecial implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_tributo_especial")
	private long idProdutoTributoEspecial;
	
	@Column(name="nr_alicota")
	private double nrAlicota;
	
	@Column(name="nr_base_calculo")
	private double nrBaseCalculo;
	
	@Column(name="sn_status")
	private int snStatus;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_inicio")
	private Date dtInicio;
	
	@ManyToOne
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name="id_cfop")
	private Cfop cfop;

	public long getIdProdutoTributoEspecial() {
		return idProdutoTributoEspecial;
	}

	public void setIdProdutoTributoEspecial(long idProdutoTributoEspecial) {
		this.idProdutoTributoEspecial = idProdutoTributoEspecial;
	}

	public double getNrAlicota() {
		return nrAlicota;
	}

	public void setNrAlicota(double nrAlicota) {
		this.nrAlicota = nrAlicota;
	}

	public double getNrBaseCalculo() {
		return nrBaseCalculo;
	}

	public void setNrBaseCalculo(double nrBaseCalculo) {
		this.nrBaseCalculo = nrBaseCalculo;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Cfop getCfop() {
		return cfop;
	}

	public void setCfop(Cfop cfop) {
		this.cfop = cfop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProdutoTributoEspecial ^ (idProdutoTributoEspecial >>> 32));
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
		ProdutoTributoEspecial other = (ProdutoTributoEspecial) obj;
		if (idProdutoTributoEspecial != other.idProdutoTributoEspecial)
			return false;
		return true;
	}
}
