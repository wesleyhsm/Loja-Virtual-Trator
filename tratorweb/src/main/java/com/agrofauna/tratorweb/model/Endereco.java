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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the endereco database table.
 * 
 */
@Entity
@Table(name="endereco")
@NamedQueries({	
	@NamedQuery(name="Endereco.findAll", query="SELECT e FROM Endereco e"),
	@NamedQuery(name="Endereco.endereco", query="SELECT e FROM Cliente c inner join c.enderecos e WHERE c.idPessoa=:idPessoa AND e.nrTipoEndereco=1")
})

public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_endereco")
	private long idEndereco;
	
	@NotBlank
	@Column(name="nm_bairro")
	private String nmBairro;
	
	@Column(name="nm_caixa_postal")
	private String nmCaixaPostal;
	
	@NotBlank
	@Column(name="nm_cep")
	private String nmCep;
	
	@NotBlank
	@Column(name="nm_rua")
	private String nmRua;
	
	@NotBlank
	@Column(name="nm_cidade")
	private String nmCidade;
	
	@NotBlank
	@Column(name="nm_numero")
	private String nmNumero;
	
	@NotNull
	@Column(name="nr_tipo_endereco")
	private int nrTipoEndereco;
	
	@Column(name="nm_complemento")
	private String nmComplemento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Pessoa pessoa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_estado")
	private Estado estado;

	public long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(long idEndereco) {
		this.idEndereco = idEndereco;
	}
	
	public int getNrTipoEndereco() {
		return nrTipoEndereco;
	}

	public void setNrTipoEndereco(int nrTipoEndereco) {
		this.nrTipoEndereco = nrTipoEndereco;
	}

	public String getNmBairro() {
		return nmBairro;
	}

	public void setNmBairro(String nmBairro) {
		this.nmBairro = nmBairro;
	}

	public String getNmCaixaPostal() {
		return nmCaixaPostal;
	}

	public void setNmCaixaPostal(String nmCaixaPostal) {
		this.nmCaixaPostal = nmCaixaPostal;
	}

	public String getNmCep() {
		return nmCep;
	}

	public void setNmCep(String nmCep) {
		this.nmCep = nmCep;
	}

	public String getNmRua() {
		return nmRua;
	}

	public void setNmRua(String nmRua) {
		this.nmRua = nmRua;
	}
		
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNmCidade() {
		return nmCidade;
	}

	public void setNmCidade(String nmCidade) {
		this.nmCidade = nmCidade;
	}

	public String getNmNumero() {
		return nmNumero;
	}

	public void setNmNumero(String nmNumero) {
		this.nmNumero = nmNumero;
	}

	public String getNmComplemento() {
		return nmComplemento;
	}

	public void setNmComplemento(String nmComplemento) {
		this.nmComplemento = nmComplemento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idEndereco ^ (idEndereco >>> 32));
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
		Endereco other = (Endereco) obj;
		if (idEndereco != other.idEndereco)
			return false;
		return true;
	}
}