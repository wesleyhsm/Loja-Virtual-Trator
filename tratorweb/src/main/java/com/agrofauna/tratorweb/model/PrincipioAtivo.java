package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the principio_ativo database table.
 * 
 */
@Entity
@Table(name="principio_ativo")
@NamedQuery(name="PrincipioAtivo.findAll", query="SELECT p FROM PrincipioAtivo p")
public class PrincipioAtivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_principio_ativo")
	private long idPrincipioAtivo;

	@Column(name="nm_principio_ativo")
	private String nmPrincipioAtivo;
	
	//bi-directional many-to-one association to ProdutoDadosTecnico
	@OneToMany(mappedBy="principioAtivo", fetch = FetchType.LAZY)
	private List<ProdutoDadosTecnico> produtoDadosTecnicos;

	public PrincipioAtivo() {
	}
		
	public List<ProdutoDadosTecnico> getProdutoDadosTecnicos() {
		return produtoDadosTecnicos;
	}

	public void setProdutoDadosTecnicos(
			List<ProdutoDadosTecnico> produtoDadosTecnicos) {
		this.produtoDadosTecnicos = produtoDadosTecnicos;
	}

	public long getIdPrincipioAtivo() {
		return this.idPrincipioAtivo;
	}

	public void setIdPrincipioAtivo(long idPrincipioAtivo) {
		this.idPrincipioAtivo = idPrincipioAtivo;
	}

	public String getNmPrincipioAtivo() {
		return this.nmPrincipioAtivo;
	}

	public void setNmPrincipioAtivo(String nmPrincipioAtivo) {
		this.nmPrincipioAtivo = nmPrincipioAtivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idPrincipioAtivo ^ (idPrincipioAtivo >>> 32));
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
		PrincipioAtivo other = (PrincipioAtivo) obj;
		if (idPrincipioAtivo != other.idPrincipioAtivo)
			return false;
		return true;
	}
	
}