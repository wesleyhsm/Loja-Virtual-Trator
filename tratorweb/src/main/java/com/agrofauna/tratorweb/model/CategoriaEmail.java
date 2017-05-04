package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;


/**
 * The persistent class for the categoria_email database table.
 wesley jefferson
 */
@Entity
@Table(name="categoria_email")
@NamedQuery(name="CategoriaEmail.findAll", query="SELECT c FROM CategoriaEmail c")
public class CategoriaEmail implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_categoria_email")
	private long idCategoriaEmail;
	
	@NotNull
	@Column(name="nm_dominio", length=60 )
	private String nmDominio;

	@NotNull
	@Column(name="nm_sevidor", length=60)
	private String nmSevidor;

	//bi-directional many-to-one association to Email
	@OneToMany(mappedBy="categoriaEmail")
	private List<Email> emails;

	public long getIdCategoriaEmail() {
		return this.idCategoriaEmail;
	}

	public void setIdCategoriaEmail(long idCategoriaEmail) {
		this.idCategoriaEmail = idCategoriaEmail;
	}

	public String getNmDominio() {
		return nmDominio;
	}

	public void setNmDominio(String nmDominio) {
		this.nmDominio = nmDominio;
	}

	public String getNmSevidor() {
		return this.nmSevidor;
	}

	public void setNmSevidor(String nmSevidor) {
		this.nmSevidor = nmSevidor;
	}

	public List<Email> getEmails() {
		return this.emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public Email addEmail(Email email) {
		getEmails().add(email);
		email.setCategoriaEmail(this);

		return email;
	}

	public Email removeEmail(Email email) {
		getEmails().remove(email);
		email.setCategoriaEmail(null);

		return email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idCategoriaEmail ^ (idCategoriaEmail >>> 32));
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
		CategoriaEmail other = (CategoriaEmail) obj;
		if (idCategoriaEmail != other.idCategoriaEmail)
			return false;
		return true;
	}
}