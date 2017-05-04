package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the Cotacao database table.
 * wesley jefferson
 */

@Entity
@Table(name="cotacao")
@NamedQueries({
	@NamedQuery(name="Cotacao.findAll", query="SELECT e FROM Cotacao e")		
})	
public class Cotacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cotacao")
	private long idCotacao;
	
	@NotBlank
	@Column(name="nm_cotacao")
	private String nmCotacao;
	
	@NotNull
	@Column(name="nr_prioridade")
	private int nrPrioridade;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dtAlteracao;
			
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_cotacao_status")
	private CotacaoStatus cotacaoStatus;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_criacao")
	private Funcionario funcionarioCriacao;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_alteracao")
	private Funcionario funcionarioAlteracao;	
	
	@OneToMany(mappedBy="cotacao", targetEntity=com.agrofauna.tratorweb.model.CotacaoProduto.class, cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private List<CotacaoProduto> cotacaoProdutos = new ArrayList<>();
			
	public List<CotacaoProduto> getCotacaoProdutos() {
		return cotacaoProdutos;
	}

	public void setCotacaoProdutos(List<CotacaoProduto> cotacaoProdutos) {
		this.cotacaoProdutos = cotacaoProdutos;
	}

	public long getIdCotacao() {
		return idCotacao;
	}

	public void setIdCotacao(long idCotacao) {
		this.idCotacao = idCotacao;
	}

	public String getNmCotacao() {
		return nmCotacao;
	}

	public void setNmCotacao(String nmCotacao) {
		this.nmCotacao = nmCotacao;
	}

	public int getNrPrioridade() {
		return nrPrioridade;
	}

	public void setNrPrioridade(int nrPrioridade) {
		this.nrPrioridade = nrPrioridade;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	public CotacaoStatus getCotacaoStatus() {
		return cotacaoStatus;
	}

	public void setCotacaoStatus(CotacaoStatus cotacaoStatus) {
		this.cotacaoStatus = cotacaoStatus;
	}

	public Funcionario getFuncionarioCriacao() {
		return funcionarioCriacao;
	}

	public void setFuncionarioCriacao(Funcionario funcionarioCriacao) {
		this.funcionarioCriacao = funcionarioCriacao;
	}

	public Funcionario getFuncionarioAlteracao() {
		return funcionarioAlteracao;
	}

	public void setFuncionarioAlteracao(Funcionario funcionarioAlteracao) {
		this.funcionarioAlteracao = funcionarioAlteracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCotacao ^ (idCotacao >>> 32));
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
		Cotacao other = (Cotacao) obj;
		if (idCotacao != other.idCotacao)
			return false;
		return true;
	}
}
