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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;


/**
 * The persistent class for the telefone database table.
 * 
 */
@Entity
@Table(name="telefone")
@NamedQueries({
	@NamedQuery(name="Telefone.findAll", query="SELECT t FROM Telefone t"),
	@NamedQuery(name="Telefone.existeTelefone", query="SELECT t FROM Telefone t WHERE lower(t.nmTelefone) like :numeroTelefone"),
	@NamedQuery(name="Telefone.clienteTelefone", query="SELECT t FROM Cliente c inner join c.telefones t WHERE c.idPessoa=:idPessoa AND t.snStatus=1")
})	
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_telefone")
	private long idTelefone;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
	
	@NotBlank
	@Column(name="nm_contato")
	private String nmContato;
	
	@NotBlank
	@Column(name="nm_telefone")
	private String nmTelefone;
	
	@Column(name="nr_tipo")
	private int nrTipo;
	
	@NotNull
	@Column(name="sn_status")
	private int snStatus;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Pessoa pessoa;

	public long getIdTelefone() {
		return idTelefone;
	}

	public void setIdTelefone(long idTelefone) {
		this.idTelefone = idTelefone;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmContato() {
		return nmContato;
	}

	public void setNmContato(String nmContato) {
		this.nmContato = nmContato;
	}

	public String getNmTelefone() {
		return nmTelefone;
	}

	public void setNmTelefone(String nmTelefone) {
		this.nmTelefone = nmTelefone;
	}

	public int getNrTipo() {
		return nrTipo;
	}

	public void setNrTipo(int nrTipo) {
		this.nrTipo = nrTipo;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idTelefone ^ (idTelefone >>> 32));
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
		Telefone other = (Telefone) obj;
		if (idTelefone != other.idTelefone)
			return false;
		return true;
	}
}