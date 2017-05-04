package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the produto_estoque_tipo database table.
 * 
 */
@Entity
@Table(name="produto_estoque_tipo")
@NamedQuery(name="ProdutoEstoqueTipo.findAll", query="SELECT p FROM ProdutoEstoqueTipo p")
public class ProdutoEstoqueTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_estoque_tipo")
	private long idProdutoEstoqueTipo;

	@Column(name="nm_produto_estoque")
	private String nmProdutoEstoque;

	//bi-directional many-to-one association to ProdutoEstoque
	@OneToMany(mappedBy="produtoEstoqueTipo", fetch = FetchType.LAZY)
	private List<ProdutoEstoque> produtoEstoques;

	public ProdutoEstoqueTipo() {
	}

	public long getIdProdutoEstoqueTipo() {
		return this.idProdutoEstoqueTipo;
	}

	public void setIdProdutoEstoqueTipo(long idProdutoEstoqueTipo) {
		this.idProdutoEstoqueTipo = idProdutoEstoqueTipo;
	}

	public String getNmProdutoEstoque() {
		return this.nmProdutoEstoque;
	}

	public void setNmProdutoEstoque(String nmProdutoEstoque) {
		this.nmProdutoEstoque = nmProdutoEstoque;
	}

	public List<ProdutoEstoque> getProdutoEstoques() {
		return this.produtoEstoques;
	}

	public void setProdutoEstoques(List<ProdutoEstoque> produtoEstoques) {
		this.produtoEstoques = produtoEstoques;
	}

	public ProdutoEstoque addProdutoEstoque(ProdutoEstoque produtoEstoque) {
		getProdutoEstoques().add(produtoEstoque);
		produtoEstoque.setProdutoEstoqueTipo(this);

		return produtoEstoque;
	}

	public ProdutoEstoque removeProdutoEstoque(ProdutoEstoque produtoEstoque) {
		getProdutoEstoques().remove(produtoEstoque);
		produtoEstoque.setProdutoEstoqueTipo(null);

		return produtoEstoque;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idProdutoEstoqueTipo ^ (idProdutoEstoqueTipo >>> 32));
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
		ProdutoEstoqueTipo other = (ProdutoEstoqueTipo) obj;
		if (idProdutoEstoqueTipo != other.idProdutoEstoqueTipo)
			return false;
		return true;
	}

}