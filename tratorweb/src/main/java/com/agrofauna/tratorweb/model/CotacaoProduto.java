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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="cotacao_produto")
@NamedQueries({
	@NamedQuery(name="CotacaoProduto.findAll", query="SELECT e FROM CotacaoProduto e")		
})
public class CotacaoProduto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cotacao_produto")
	private long idCotacaoProduto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_cotacao")
	private Cotacao cotacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@Column(name="nr_qtd_produto")
	private int nrQtdProduto;

	public long getIdCotacaoProduto() {
		return idCotacaoProduto;
	}

	public void setIdCotacaoProduto(long idCotacaoProduto) {
		this.idCotacaoProduto = idCotacaoProduto;
	}

	public Cotacao getCotacao() {
		return cotacao;
	}

	public void setCotacao(Cotacao cotacao) {
		this.cotacao = cotacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getNrQtdProduto() {
		return nrQtdProduto;
	}

	public void setNrQtdProduto(int nrQtdProduto) {
		this.nrQtdProduto = nrQtdProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCotacaoProduto ^ (idCotacaoProduto >>> 32));
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
		CotacaoProduto other = (CotacaoProduto) obj;
		if (idCotacaoProduto != other.idCotacaoProduto)
			return false;
		return true;
	}	
}
