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

/**
 * The persistent class for the CotacaoFornecedor database table.
 * wesley jefferson
 */

@Entity
@Table(name="cotacao_fornecedor")
@NamedQueries({
	@NamedQuery(name="CotacaoFornecedor.findAll", query="SELECT cf FROM CotacaoFornecedor cf")		
})
public class CotacaoFornecedor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cotacao_fornecedor")
	private long idCotacaoFornecedor;
	
	@Column(name="nm_observacao")
	private String nmObservacao;
	
	@Column(name="id_cotacao_fornecedor_md5")
	private String IdCotacaoFornecedorMd5;
	
	@Column(name="nr_quantidade_acesso")
	private int nrQuantidadeAcesso;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dtAlteracao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_cotacao_status")
	private CotacaoStatus cotacaoStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;
	
	@OneToMany(mappedBy="cotacaoFornecedor", targetEntity=com.agrofauna.tratorweb.model.CotacaoProdutoPreco.class, cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private List<CotacaoProdutoPreco> cotacaoProdutoPrecos = new ArrayList<>();
		
	public List<CotacaoProdutoPreco> getCotacaoProdutoPrecos() {
		return cotacaoProdutoPrecos;
	}

	public void setCotacaoProdutoPrecos(List<CotacaoProdutoPreco> cotacaoProdutoPrecos) {
		this.cotacaoProdutoPrecos = cotacaoProdutoPrecos;
	}

	public long getIdCotacaoFornecedor() {
		return idCotacaoFornecedor;
	}

	public void setIdCotacaoFornecedor(long idCotacaoFornecedor) {
		this.idCotacaoFornecedor = idCotacaoFornecedor;
	}

	public String getNmObservacao() {
		return nmObservacao;
	}

	public void setNmObservacao(String nmObservacao) {
		this.nmObservacao = nmObservacao;
	}

	public String getIdCotacaoFornecedorMd5() {
		return IdCotacaoFornecedorMd5;
	}

	public void setIdCotacaoFornecedorMd5(String idCotacaoFornecedorMd5) {
		IdCotacaoFornecedorMd5 = idCotacaoFornecedorMd5;
	}

	public int getNrQuantidadeAcesso() {
		return nrQuantidadeAcesso;
	}

	public void setNrQuantidadeAcesso(int nrQuantidadeAcesso) {
		this.nrQuantidadeAcesso = nrQuantidadeAcesso;
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
		result = prime * result + ((IdCotacaoFornecedorMd5 == null) ? 0 : IdCotacaoFornecedorMd5.hashCode());
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
		CotacaoFornecedor other = (CotacaoFornecedor) obj;
		if (IdCotacaoFornecedorMd5 == null) {
			if (other.IdCotacaoFornecedorMd5 != null)
				return false;
		} else if (!IdCotacaoFornecedorMd5.equals(other.IdCotacaoFornecedorMd5))
			return false;
		return true;
	}
}
