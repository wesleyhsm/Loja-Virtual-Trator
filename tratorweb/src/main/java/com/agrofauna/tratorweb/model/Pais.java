package com.agrofauna.tratorweb.model;

import java.io.Serializable;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


/**
 * The persistent class for the pais database table.
 * 
 */
@Entity
@Table(name="pais")
@NamedQuery(name="Pais.findAll", query="SELECT p FROM Pais p")
public class Pais implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pais")
	private long idPais;
	
	@NotBlank @NotNull @Max(value=50)
	@Column(name="nm_pais", length=50)
	private String nmPais;

	@NotBlank @NotNull @Max(value=30)
	@Column(name="nm_codigo_pais",length=30)
	private String nmCodigoPais;
	
	//bi-directional many-to-one association to Estado
	@OneToMany(mappedBy="pais", fetch = FetchType.LAZY)
	private List<Estado> estados;

	public long getIdPais() {
		return idPais;
	}

	public void setIdPais(long idPais) {
		this.idPais = idPais;
	}

	public String getNmPais() {
		return nmPais;
	}

	public void setNmPais(String nmPais) {
		this.nmPais = nmPais;
	}

	public String getNmCodigoPais() {
		return nmCodigoPais;
	}

	public void setNmCodigoPais(String nmCodigoPais) {
		this.nmCodigoPais = nmCodigoPais;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPais ^ (idPais >>> 32));
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
		Pais other = (Pais) obj;
		if (idPais != other.idPais)
			return false;
		return true;
	}
}