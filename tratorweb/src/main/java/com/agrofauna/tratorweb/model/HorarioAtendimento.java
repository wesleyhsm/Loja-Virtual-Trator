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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;


/**
 * The persistent class for the horario_atendimento database table.
 * 
 */
@Entity
@Table(name="horario_atendimento")
@NamedQuery(name="HorarioAtendimento.findAll", query="SELECT h FROM HorarioAtendimento h")
public class HorarioAtendimento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_horario_atendimento")
	private long idHorarioAtendimento;
	
	@Temporal(TemporalType.TIME)
	@Column(name="hr_final")
	private Date hrFinal;

	@Temporal(TemporalType.TIME)
	@Column(name="hr_inicial")
	private Date hrInicial;
	
	
	@Column(name="nm_responsavel", length=60)
	private String nmResponsavel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;

	public long getIdHorarioAtendimento(){
		return idHorarioAtendimento;
	}

	public void setIdHorarioAtendimento(long idHorarioAtendimento) {
		this.idHorarioAtendimento = idHorarioAtendimento;
	}

	public Date getHrFinal() {
		return hrFinal;
	}

	public void setHrFinal(Date hrFinal) {
		this.hrFinal = hrFinal;
	}

	public Date getHrInicial() {
		return hrInicial;
	}

	public void setHrInicial(Date hrInicial) {
		this.hrInicial = hrInicial;
	}

	public String getNmResponsavel() {
		return nmResponsavel;
	}

	public void setNmResponsavel(String nmResponsavel) {
		this.nmResponsavel = nmResponsavel;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idHorarioAtendimento ^ (idHorarioAtendimento >>> 32));
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
		HorarioAtendimento other = (HorarioAtendimento) obj;
		if (idHorarioAtendimento != other.idHorarioAtendimento)
			return false;
		return true;
	}
}