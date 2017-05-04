package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the produto_medida database table.
 * 
 */
@Entity
@Table(name="produto_medida")
@NamedQuery(name="ProdutoMedida.findAll", query="SELECT p FROM ProdutoMedida p")
public class ProdutoMedida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_medida")
	private String idProdutoMedida;

	@Column(name="nm_produto_medida")
	private String nmProdutoMedida;

	//bi-directional many-to-one association to ProdutoArmazenamento
	@OneToMany(mappedBy="produtoMedida", fetch = FetchType.LAZY)
	private List<ProdutoArmazenamento> produtoArmazenamentos;

	public ProdutoMedida() {
	}

	public String getIdProdutoMedida() {
		return this.idProdutoMedida;
	}

	public void setIdProdutoMedida(String idProdutoMedida) {
		this.idProdutoMedida = idProdutoMedida;
	}

	public String getNmProdutoMedida() {
		return this.nmProdutoMedida;
	}

	public void setNmProdutoMedida(String nmProdutoMedida) {
		this.nmProdutoMedida = nmProdutoMedida;
	}

	public List<ProdutoArmazenamento> getProdutoArmazenamentos() {
		return this.produtoArmazenamentos;
	}

	public void setProdutoArmazenamentos(List<ProdutoArmazenamento> produtoArmazenamentos) {
		this.produtoArmazenamentos = produtoArmazenamentos;
	}

	public ProdutoArmazenamento addProdutoArmazenamento(ProdutoArmazenamento produtoArmazenamento) {
		getProdutoArmazenamentos().add(produtoArmazenamento);
		produtoArmazenamento.setProdutoMedida(this);

		return produtoArmazenamento;
	}

	public ProdutoArmazenamento removeProdutoArmazenamento(ProdutoArmazenamento produtoArmazenamento) {
		getProdutoArmazenamentos().remove(produtoArmazenamento);
		produtoArmazenamento.setProdutoMedida(null);

		return produtoArmazenamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idProdutoMedida == null) ? 0 : idProdutoMedida.hashCode());
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
		ProdutoMedida other = (ProdutoMedida) obj;
		if (idProdutoMedida == null) {
			if (other.idProdutoMedida != null)
				return false;
		} else if (!idProdutoMedida.equals(other.idProdutoMedida))
			return false;
		return true;
	}

}