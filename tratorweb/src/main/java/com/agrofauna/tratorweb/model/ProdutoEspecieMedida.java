package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the produto_especie_medida database table.
 * 
 */
@Entity
@Table(name="produto_especie_medida")
@NamedQuery(name="ProdutoEspecieMedida.findAll", query="SELECT p FROM ProdutoEspecieMedida p")
public class ProdutoEspecieMedida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_especie_medida")
	private String idProdutoEspecieMedida;

	@Column(name="nm_produto_especie_medida")
	private String nmProdutoEspecieMedida;

	//bi-directional many-to-one association to ProdutoArmazenamento
	@OneToMany(mappedBy="produtoEspecieMedida", fetch = FetchType.LAZY)
	private List<ProdutoArmazenamento> produtoArmazenamentos;

	public ProdutoEspecieMedida() {
	}

	public String getIdProdutoEspecieMedida() {
		return this.idProdutoEspecieMedida;
	}

	public void setIdProdutoEspecieMedida(String idProdutoEspecieMedida) {
		this.idProdutoEspecieMedida = idProdutoEspecieMedida;
	}

	public String getNmProdutoEspecieMedida() {
		return this.nmProdutoEspecieMedida;
	}

	public void setNmProdutoEspecieMedida(String nmProdutoEspecieMedida) {
		this.nmProdutoEspecieMedida = nmProdutoEspecieMedida;
	}

	public List<ProdutoArmazenamento> getProdutoArmazenamentos() {
		return this.produtoArmazenamentos;
	}

	public void setProdutoArmazenamentos(List<ProdutoArmazenamento> produtoArmazenamentos) {
		this.produtoArmazenamentos = produtoArmazenamentos;
	}

	public ProdutoArmazenamento addProdutoArmazenamento(ProdutoArmazenamento produtoArmazenamento) {
		getProdutoArmazenamentos().add(produtoArmazenamento);
		produtoArmazenamento.setProdutoEspecieMedida(this);

		return produtoArmazenamento;
	}

	public ProdutoArmazenamento removeProdutoArmazenamento(ProdutoArmazenamento produtoArmazenamento) {
		getProdutoArmazenamentos().remove(produtoArmazenamento);
		produtoArmazenamento.setProdutoEspecieMedida(null);

		return produtoArmazenamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idProdutoEspecieMedida == null) ? 0
						: idProdutoEspecieMedida.hashCode());
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
		ProdutoEspecieMedida other = (ProdutoEspecieMedida) obj;
		if (idProdutoEspecieMedida == null) {
			if (other.idProdutoEspecieMedida != null)
				return false;
		} else if (!idProdutoEspecieMedida.equals(other.idProdutoEspecieMedida))
			return false;
		return true;
	}
	
}