package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the campanha_cliente database table.
 * 
 */
@Entity
@Table(name="campanha_cliente")
@NamedQuery(name="CampanhaCliente.findAll", query="SELECT c FROM CampanhaCliente c")
public class CampanhaCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_campanha_cliente")
	private long idCampanhaCliente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_cricao")
	private Date dtCricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;

	//bi-directional many-to-one association to Campanha
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_campanha")
	private Campanha campanha;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public long getIdCampanhaCliente() {
		return this.idCampanhaCliente;
	}

	public void setIdCampanhaCliente(long idCampanhaCliente) {
		this.idCampanhaCliente = idCampanhaCliente;
	}

	public Date getDtCricao() {
		return this.dtCricao;
	}

	public void setDtCricao(Date dtCricao) {
		this.dtCricao = dtCricao;
	}

	

	public Campanha getCampanha() {
		return this.campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idCampanhaCliente ^ (idCampanhaCliente >>> 32));
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
		CampanhaCliente other = (CampanhaCliente) obj;
		if (idCampanhaCliente != other.idCampanhaCliente)
			return false;
		return true;
	}
}