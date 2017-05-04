package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the grupo_quimico database table.
 * 
 */
@Entity
@Table(name="grupo_quimico")
@NamedQuery(name="GrupoQuimico.findAll", query="SELECT g FROM GrupoQuimico g")
public class GrupoQuimico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_grupo_quimico")
	private long idGrupoQuimico;

	@Column(name="nm_grupo_quimico")
	private String nmGrupoQuimico;

	//bi-directional many-to-one association to ProdutoDadosTecnico
	@OneToMany(mappedBy="grupoQuimico", fetch = FetchType.LAZY)
	private List<ProdutoDadosTecnico> produtoDadosTecnicos;

	public GrupoQuimico() {
	}
		
	public List<ProdutoDadosTecnico> getProdutoDadosTecnicos() {
		return produtoDadosTecnicos;
	}

	public void setProdutoDadosTecnicos(
			List<ProdutoDadosTecnico> produtoDadosTecnicos) {
		this.produtoDadosTecnicos = produtoDadosTecnicos;
	}

	public long getIdGrupoQuimico() {
		return this.idGrupoQuimico;
	}

	public void setIdGrupoQuimico(long idGrupoQuimico) {
		this.idGrupoQuimico = idGrupoQuimico;
	}

	public String getNmGrupoQuimico() {
		return this.nmGrupoQuimico;
	}

	public void setNmGrupoQuimico(String nmGrupoQuimico) {
		this.nmGrupoQuimico = nmGrupoQuimico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idGrupoQuimico ^ (idGrupoQuimico >>> 32));
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
		GrupoQuimico other = (GrupoQuimico) obj;
		if (idGrupoQuimico != other.idGrupoQuimico)
			return false;
		return true;
	}	
	
}