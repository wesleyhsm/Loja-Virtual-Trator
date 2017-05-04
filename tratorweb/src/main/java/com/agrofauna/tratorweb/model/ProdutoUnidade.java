package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the produto_unidade database table.
 * 
 */
@Entity
@Table(name="produto_unidade")
@NamedQuery(name="ProdutoUnidade.findAll", query="SELECT p FROM ProdutoUnidade p")
public class ProdutoUnidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_unidade_medida")
	private String idProdutoUnidadeMedida;

	@Column(name="nm_unidade_medida")
	private String nmUnidadeMedida;

	//bi-directional many-to-one association to ProdutoArmazenamento
	@OneToMany(mappedBy="produtoUnidade", fetch = FetchType.LAZY)
	private List<ProdutoArmazenamento> produtoArmazenamentos;

	public ProdutoUnidade() {
	}

	public String getIdProdutoUnidadeMedida() {
		return this.idProdutoUnidadeMedida;
	}

	public void setIdProdutoUnidadeMedida(String idProdutoUnidadeMedida) {
		this.idProdutoUnidadeMedida = idProdutoUnidadeMedida;
	}

	public String getNmUnidadeMedida() {
		return this.nmUnidadeMedida;
	}

	public void setNmUnidadeMedida(String nmUnidadeMedida) {
		this.nmUnidadeMedida = nmUnidadeMedida;
	}

	public List<ProdutoArmazenamento> getProdutoArmazenamentos() {
		return this.produtoArmazenamentos;
	}

	public void setProdutoArmazenamentos(List<ProdutoArmazenamento> produtoArmazenamentos) {
		this.produtoArmazenamentos = produtoArmazenamentos;
	}

	public ProdutoArmazenamento addProdutoArmazenamento(ProdutoArmazenamento produtoArmazenamento) {
		getProdutoArmazenamentos().add(produtoArmazenamento);
		produtoArmazenamento.setProdutoUnidade(this);

		return produtoArmazenamento;
	}

	public ProdutoArmazenamento removeProdutoArmazenamento(ProdutoArmazenamento produtoArmazenamento) {
		getProdutoArmazenamentos().remove(produtoArmazenamento);
		produtoArmazenamento.setProdutoUnidade(null);

		return produtoArmazenamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idProdutoUnidadeMedida == null) ? 0
						: idProdutoUnidadeMedida.hashCode());
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
		ProdutoUnidade other = (ProdutoUnidade) obj;
		if (idProdutoUnidadeMedida == null) {
			if (other.idProdutoUnidadeMedida != null)
				return false;
		} else if (!idProdutoUnidadeMedida.equals(other.idProdutoUnidadeMedida))
			return false;
		return true;
	}

}