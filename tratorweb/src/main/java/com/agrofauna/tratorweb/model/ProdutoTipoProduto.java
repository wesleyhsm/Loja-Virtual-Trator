package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the produto_tipo_produto database table.
 * 
 */
@Entity
@Table(name="produto_tipo_produto")
@NamedQuery(name="ProdutoTipoProduto.findAll", query="SELECT p FROM ProdutoTipoProduto p")
public class ProdutoTipoProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_tipo_produto")
	private long idProdutoTipoProduto;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;

	//bi-directional many-to-one association to ProdutoTipo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto_tipo")
	private ProdutoTipo produtoTipo;

	public ProdutoTipoProduto() {
	}

	public long getIdProdutoTipoProduto() {
		return this.idProdutoTipoProduto;
	}

	public void setIdProdutoTipoProduto(long idProdutoTipoProduto) {
		this.idProdutoTipoProduto = idProdutoTipoProduto;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ProdutoTipo getProdutoTipo() {
		return this.produtoTipo;
	}

	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idProdutoTipoProduto ^ (idProdutoTipoProduto >>> 32));
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
		ProdutoTipoProduto other = (ProdutoTipoProduto) obj;
		if (idProdutoTipoProduto != other.idProdutoTipoProduto)
			return false;
		return true;
	}

}