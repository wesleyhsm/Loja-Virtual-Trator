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

/**
 * The persistent class for the CotacaoProdutoPreco database table.
 * wesley jefferson
 */

@Entity
@Table(name="cotacao_produto_preco")
@NamedQueries({
	@NamedQuery(name="CotacaoProdutoPreco.findAll", query="SELECT cf FROM CotacaoProdutoPreco cf")		
})
public class CotacaoProdutoPreco implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cotacao_produto_preco")
	private long idCotacaoProdutoPreco;
	
	@Column(name="nr_preco_produto")
	private double nrPrecoProduto;
	
	@Column(name="nr_quantidade_produto")
	private double nrQuantidadeProduto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_cotacao_fornecedor")
	private CotacaoFornecedor cotacaoFornecedor;
	
	public double getNrQuantidadeProduto() {
		return nrQuantidadeProduto;
	}

	public void setNrQuantidadeProduto(double nrQuantidadeProduto) {
		this.nrQuantidadeProduto = nrQuantidadeProduto;
	}

	public long getIdCotacaoProdutoPreco() {
		return idCotacaoProdutoPreco;
	}

	public void setIdCotacaoProdutoPreco(long idCotacaoProdutoPreco) {
		this.idCotacaoProdutoPreco = idCotacaoProdutoPreco;
	}

	public double getNrPrecoProduto() {
		return nrPrecoProduto;
	}

	public void setNrPrecoProduto(double nrPrecoProduto) {
		this.nrPrecoProduto = nrPrecoProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public CotacaoFornecedor getCotacaoFornecedor() {
		return cotacaoFornecedor;
	}

	public void setCotacaoFornecedor(CotacaoFornecedor cotacaoFornecedor) {
		this.cotacaoFornecedor = cotacaoFornecedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCotacaoProdutoPreco ^ (idCotacaoProdutoPreco >>> 32));
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
		CotacaoProdutoPreco other = (CotacaoProdutoPreco) obj;
		if (idCotacaoProdutoPreco != other.idCotacaoProdutoPreco)
			return false;
		return true;
	}	
}
