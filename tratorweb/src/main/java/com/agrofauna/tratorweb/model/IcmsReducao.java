package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="icms_reducao")
@NamedQueries({
	@NamedQuery(name="IcmsReducao.findAll", query="SELECT r FROM IcmsReducao r")	
})
public class IcmsReducao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_icms_reducao")	
	private long idIcmsReducao;
	
	@Column(name="nr_base_calculo")
	private double nrBaseCalculo;
	
	@Column(name="nm_mensagem")
	private String nmMensagem;

	@OneToMany(mappedBy="icmsReducao", fetch = FetchType.LAZY)
	private List<IcmsEstado> icmsEstados = new ArrayList<>();
	
	public List<IcmsEstado> getIcmsEstados() {
		return icmsEstados;
	}

	public void setIcmsEstados(List<IcmsEstado> icmsEstados) {
		this.icmsEstados = icmsEstados;
	}

	public long getIdIcmsReducao() {
		return idIcmsReducao;
	}

	public void setIdIcmsReducao(long idIcmsReducao) {
		this.idIcmsReducao = idIcmsReducao;
	}

	public double getNrBaseCalculo() {
		return nrBaseCalculo;
	}

	public void setNrBaseCalculo(double nrBaseCalculo) {
		this.nrBaseCalculo = nrBaseCalculo;
	}

	public String getNmMensagem() {
		return nmMensagem;
	}

	public void setNmMensagem(String nmMensagem) {
		this.nmMensagem = nmMensagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idIcmsReducao ^ (idIcmsReducao >>> 32));
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
		IcmsReducao other = (IcmsReducao) obj;
		if (idIcmsReducao != other.idIcmsReducao)
			return false;
		return true;
	}
}
