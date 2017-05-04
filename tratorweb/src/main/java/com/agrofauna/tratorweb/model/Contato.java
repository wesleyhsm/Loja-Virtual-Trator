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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the contato database table.
 * 
 */
@Entity
@Table(name="contato")
@NamedQueries({
	@NamedQuery(name="Contato.findAll", query="SELECT c FROM Contato c")
})
public class Contato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_contato")
	private long idContato;
	
	@NotBlank
	@Column(name="nm_descricao")
	private String nmDescricao;
	
	@NotBlank
	@Column(name="nm_nome")
	private String nmNome;

	@NotBlank
	@Column(name="nm_telefone")
	private String nmTelefone;
		
	@Column(name="nm_celular")
	private String nmCelular;
	
	@Email @NotBlank
	@Column(name="nm_email")
	private String nmEmail;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
		
	@Column(name="nm_descricao_funcionario")
	private String nmDescricaoFuncionario;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dtAlteracao;
	
	@NotNull
	@Column(name="sn_status")
	private int snStatus;
	
	//bi-directional many-to-one association to 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_funcionario")
	private Funcionario funcionario;

	public long getIdContato() {
		return this.idContato;
	}

	public void setIdContato(long idContato) {
		this.idContato = idContato;
	}

	public String getNmDescricao() {
		return this.nmDescricao;
	}

	public void setNmDescricao(String nmDescricao) {
		this.nmDescricao = nmDescricao;
	}

	public String getNmNome() {
		return this.nmNome;
	}

	public void setNmNome(String nmNome) {
		this.nmNome = nmNome;
	}

	public String getNmTelefone() {
		return this.nmTelefone;
	}

	public void setNmTelefone(String nmTelefone) {
		this.nmTelefone = nmTelefone;
	}

	public String getNmEmail() {
		return nmEmail;
	}

	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
		
	public String getNmCelular() {
		return nmCelular;
	}

	public void setNmCelular(String nmCelular) {
		this.nmCelular = nmCelular;
	}
	
	public final String getNmDescricaoFuncionario() {
		return nmDescricaoFuncionario;
	}

	public final void setNmDescricaoFuncionario(String nmDescricaoFuncionario) {
		this.nmDescricaoFuncionario = nmDescricaoFuncionario;
	}

	public final Date getDtAlteracao() {
		return dtAlteracao;
	}

	public final void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idContato ^ (idContato >>> 32));
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
		Contato other = (Contato) obj;
		if (idContato != other.idContato)
			return false;
		return true;
	}
}