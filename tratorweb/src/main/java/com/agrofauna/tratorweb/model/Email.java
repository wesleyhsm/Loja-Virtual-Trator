package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.Date;

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

/**
 * The persistent class for the email database table.
 * wesley jefferson
 */
@Entity
@Table(name="email")
@NamedQueries({
	@NamedQuery(name="Email.findAll", query="SELECT e FROM Email e"),
	@NamedQuery(name="Email.existeEmail", query="SELECT e FROM Email e WHERE lower(e.nmEmail) like :email"),
	@NamedQuery(name="Email.emailCliente", query="SELECT e FROM Cliente c inner join c.emails e WHERE c.idPessoa=:idPessoa AND e.snStatus=1")	
})	
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_email")
	private long idEmail;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;

	@Column(name="nm_contato")
	private String nmContato;
	
	@NotBlank
	@org.hibernate.validator.constraints.Email(message="não é e-mail válido.")
	@Column(name="nm_email")
	private String nmEmail;
	
	@NotNull
	@Column(name="sn_status")
	private int snStatus;

	//bi-directional many-to-one association to CategoriaEmail
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_categoria_email")
	private CategoriaEmail categoriaEmail;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Pessoa pessoa;

	public long getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(long idEmail) {
		this.idEmail = idEmail;
	}

	public Date getDtCricao() {
		return this.dtCriacao;
	}

	public void setDtCricao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmContato() {
		return this.nmContato;
	}

	public void setNmContato(String nmContato) {
		this.nmContato = nmContato;
	}

	public String getNmEmail() {
		return this.nmEmail;
	}

	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	public int getSnStatus() {
		return this.snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	public CategoriaEmail getCategoriaEmail() {
		return this.categoriaEmail;
	}

	public void setCategoriaEmail(CategoriaEmail categoriaEmail) {
		this.categoriaEmail = categoriaEmail;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idEmail ^ (idEmail >>> 32));
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
		Email other = (Email) obj;
		if (idEmail != other.idEmail)
			return false;
		return true;
	}
}