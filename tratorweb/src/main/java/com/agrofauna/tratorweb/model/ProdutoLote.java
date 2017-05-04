package com.agrofauna.tratorweb.model;

import java.io.Serializable;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the produto_lote database table.
 * 
 */
@Entity
@Table(name="produto_lote")
@NamedQuery(name="ProdutoLote.findAll", query="SELECT p FROM ProdutoLote p")
public class ProdutoLote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_lote")
	private long idProdutoLote;

	@Temporal(TemporalType.DATE)
	@Column(name="dt_fabricacao")
	private Date dtFabricacao;

	@Temporal(TemporalType.DATE)
	@Column(name="dt_vencimento")
	private Date dtVencimento;
	
	@Column(name="nr_lote_digito")
	private int nrLoteDigito;

	@Column(name="nr_lote_frabricante")
	private String nrLoteFrabricante;

	@Column(name="nr_qtd_produto")
	private int nrQtdProduto;

	@Column(name="sn_status_lote")
	private int snStatusLote;
		
	@Column(name="nr_desconto_lote")
	private double nrDescontoLote;
	
	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	public ProdutoLote() {
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public long getIdProdutoLote() {
		return this.idProdutoLote;
	}

	public void setIdProdutoLote(long idProdutoLote) {
		this.idProdutoLote = idProdutoLote;
	}

	public Date getDtFabricacao() {
		return this.dtFabricacao;
	}

	public void setDtFabricacao(Date dtFabricacao) {
		this.dtFabricacao = dtFabricacao;
	}

	public Date getDtVencimento() {
		return this.dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public int getNrLoteDigito() {
		return this.nrLoteDigito;
	}

	public void setNrLoteDigito(int nrLoteDigito) {
		this.nrLoteDigito = nrLoteDigito;
	}

	public String getNrLoteFrabricante() {
		return this.nrLoteFrabricante;
	}

	public void setNrLoteFrabricante(String nrLoteFrabricante) {
		this.nrLoteFrabricante = nrLoteFrabricante;
	}

	public int getNrQtdProduto() {
		return this.nrQtdProduto;
	}

	public void setNrQtdProduto(int nrQtdProduto) {
		this.nrQtdProduto = nrQtdProduto;
	}

	public int getSnStatusLote() {
		return this.snStatusLote;
	}

	public void setSnStatusLote(int snStatusLote) {
		this.snStatusLote = snStatusLote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idProdutoLote ^ (idProdutoLote >>> 32));
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
		ProdutoLote other = (ProdutoLote) obj;
		if (idProdutoLote != other.idProdutoLote)
			return false;
		return true;
	}

}