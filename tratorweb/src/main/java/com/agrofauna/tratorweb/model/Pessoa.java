package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pessoa database table.
 * 
 */
@Entity
@Table(name="pessoa")
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name="Pessoa.findAll", query="SELECT p FROM Pessoa p"),
	@NamedQuery(name="Pessoa.existeCnpjCpf", query="SELECT p FROM Pessoa p WHERE p.nmCnpjCpf=:cnpjCpf")
})	
public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pessoa")
	private long idPessoa;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
	
	@NotBlank @NotNull
	@Column(name="nm_cnpj_cpf", length=14)
	private String nmCnpjCpf;
	
	@NotNull
	@Column(name="nr_tipo")
	private int nrTipo;
	
	@NotNull
	@Column(name="sn_status")
	private int snStatus;
			
	@Column(name="id_fatfclie")
	private long idFatfClie;
	
	@OneToMany(mappedBy="pessoa", targetEntity=com.agrofauna.tratorweb.model.Endereco.class, cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@OneToMany(mappedBy="pessoa", targetEntity=com.agrofauna.tratorweb.model.Email.class, cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private List<Email> emails = new ArrayList<>();

	@OneToMany(mappedBy="pessoa", targetEntity=com.agrofauna.tratorweb.model.Telefone.class, cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private List<Telefone> telefones = new ArrayList<>();

	@OneToOne(mappedBy="pessoa", targetEntity=com.agrofauna.tratorweb.model.Login.class, cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private Login login;	
		
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmCnpjCpf() {
		return nmCnpjCpf;
	}

	public void setNmCnpjCpf(String nmCnpjCpf) {
		this.nmCnpjCpf = nmCnpjCpf;
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

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Email addEmail(Email email) {
		getEmails().add(email);
		email.setPessoa(this);

		return email;
	}

	public Email removeEmail(Email email) {
		getEmails().remove(email);
		email.setPessoa(null);

		return email;
	}
	
	public Endereco addEnderecoe(Endereco endereco) {
		getEnderecos().add(endereco);
		endereco.setPessoa(this);

		return endereco;
	}

	public Endereco removeEndereco(Endereco endereco) {
		getEnderecos().remove(endereco);
		endereco.setPessoa(null);

		return endereco;
	}
	
	public Telefone addTelefone(Telefone telefone) {
		getTelefones().add(telefone);
		telefone.setPessoa(this);

		return telefone;
	}

	public Telefone removeTelefone(Telefone telefone) {
		getTelefones().remove(telefone);
		telefone.setPessoa(null);

		return telefone;
	}
			
	public long getIdFatfClie() {
		return idFatfClie;
	}

	public void setIdFatfClie(long idFatfClie) {
		this.idFatfClie = idFatfClie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPessoa ^ (idPessoa >>> 32));
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
		Pessoa other = (Pessoa) obj;
		if (idPessoa != other.idPessoa)
			return false;
		return true;
	}
}