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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;


/**
 * The persistent class for the estado database table.
 * 
 */
@Entity
@Table(name="estado")
@NamedQueries({
	@NamedQuery(name="Estado.findAll", query="SELECT e FROM Estado e"),
	@NamedQuery(name="Estado.cliente", query="SELECT s FROM Cliente c inner join c.enderecos e inner join e.estado s where c.idPessoa=:idPessoa"),
	@NamedQuery(name="Estado.origem", query="SELECT e FROM Estado e where e.idEstado=:idEstado")
})	
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_estado")
	private long idEstado;
	
	@NotBlank @NotNull @Max(value=60) 
	@Column(name="nm_estado", length=60)
	private String nmEstado;
	
	@NotBlank @NotNull @Max(value=2)
	@Column(name="sg_estado", length=2)
	private String sgEstado;
	
	@NotBlank @NotNull @Max(value=15)
	@Column(name="nm_ibge_estado", length=15)
	private String nmIbgeEstato;

	@NotNull
	@Column(name="nr_icms")
	private double nrIcms;
	
	//bi-directional many-to-one association to Cidade
	@OneToMany(mappedBy="estado", fetch = FetchType.LAZY)
	private List<Endereco> enderecos;
	
	@OneToMany(mappedBy="estadoOrigem", fetch = FetchType.LAZY)
	private List<IcmsEstado> icmsEstadoOrigems;
	
	@OneToMany(mappedBy="estadoDestino", fetch = FetchType.LAZY)
	private List<IcmsEstado> icmsEstadoDestinos;

	//bi-directional many-to-one association to Pai
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pais")
	private Pais pais;
	
	public double getNrIcms() {
		return nrIcms;
	}

	public void setNrIcms(double nrIcms) {
		this.nrIcms = nrIcms;
	}

	public List<IcmsEstado> getIcmsEstadoOrigems() {
		return icmsEstadoOrigems;
	}

	public void setIcmsEstadoOrigems(List<IcmsEstado> icmsEstadoOrigems) {
		this.icmsEstadoOrigems = icmsEstadoOrigems;
	}

	public List<IcmsEstado> getIcmsEstadoDestinos() {
		return icmsEstadoDestinos;
	}

	public void setIcmsEstadoDestinos(List<IcmsEstado> icmsEstadoDestinos) {
		this.icmsEstadoDestinos = icmsEstadoDestinos;
	}

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public String getNmEstado() {
		return nmEstado;
	}

	public void setNmEstado(String nmEstado) {
		this.nmEstado = nmEstado;
	}

	public String getSgEstado() {
		return sgEstado;
	}

	public void setSgEstado(String sgEstado) {
		this.sgEstado = sgEstado;
	}

	public String getNmIbgeEstato() {
		return nmIbgeEstato;
	}

	public void setNmIbgeEstato(String nmIbgeEstato) {
		this.nmIbgeEstato = nmIbgeEstato;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idEstado ^ (idEstado >>> 32));
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
		Estado other = (Estado) obj;
		if (idEstado != other.idEstado)
			return false;
		return true;
	}
}