package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the produto_tipo database table.
 * 
 */
@Entity
@Table(name="produto_tipo")
@NamedQuery(name="ProdutoTipo.findAll", query="SELECT p FROM ProdutoTipo p")
public class ProdutoTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_tipo")
	private long idProdutoTipo;

	@Column(name="nm_produto_tipo")
	private String nmProdutoTipo;

	//bi-directional many-to-one association to ProdutoTipoProduto
	@OneToMany(mappedBy="produtoTipo", fetch = FetchType.LAZY)
	private List<ProdutoTipoProduto> produtoTipoProdutos;

	public ProdutoTipo() {
	}

	public long getIdProdutoTipo() {
		return this.idProdutoTipo;
	}

	public void setIdProdutoTipo(long idProdutoTipo) {
		this.idProdutoTipo = idProdutoTipo;
	}

	public String getNmProdutoTipo() {
		return this.nmProdutoTipo;
	}

	public void setNmProdutoTipo(String nmProdutoTipo) {
		this.nmProdutoTipo = nmProdutoTipo;
	}

	public List<ProdutoTipoProduto> getProdutoTipoProdutos() {
		return this.produtoTipoProdutos;
	}

	public void setProdutoTipoProdutos(List<ProdutoTipoProduto> produtoTipoProdutos) {
		this.produtoTipoProdutos = produtoTipoProdutos;
	}

	public ProdutoTipoProduto addProdutoTipoProduto(ProdutoTipoProduto produtoTipoProduto) {
		getProdutoTipoProdutos().add(produtoTipoProduto);
		produtoTipoProduto.setProdutoTipo(this);

		return produtoTipoProduto;
	}

	public ProdutoTipoProduto removeProdutoTipoProduto(ProdutoTipoProduto produtoTipoProduto) {
		getProdutoTipoProdutos().remove(produtoTipoProduto);
		produtoTipoProduto.setProdutoTipo(null);

		return produtoTipoProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idProdutoTipo ^ (idProdutoTipo >>> 32));
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
		ProdutoTipo other = (ProdutoTipo) obj;
		if (idProdutoTipo != other.idProdutoTipo)
			return false;
		return true;
	}
	
}