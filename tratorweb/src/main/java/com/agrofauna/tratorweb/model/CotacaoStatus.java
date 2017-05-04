package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the cotacao_status database table.
 * wesley jefferson
 */

@Entity
@Table(name="cotacao_status")
@NamedQueries({
	@NamedQuery(name="CotacaoStatus.findAll", query="SELECT e FROM CotacaoStatus e")		
})	
public class CotacaoStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cotacao_status")
	private long idCotacaoStatus;
	
	@NotBlank
	@Column(name="nm_cotacao_status")
	private String nmCotacaoStatus;
	
	@NotNull
	@Column(name="sn_cotacao_status")
	private boolean snCotacaoStatus;
		
	@OneToMany(mappedBy="cotacaoStatus", fetch = FetchType.LAZY)
	private List<Cotacao> cotacoes;
			
	@OneToMany(mappedBy="cotacaoStatus", fetch = FetchType.LAZY)
	private List<CotacaoFornecedor> cotacaoFornecedor;
		
	public List<CotacaoFornecedor> getCotacaoFornecedor() {
		return cotacaoFornecedor;
	}

	public void setCotacaoFornecedor(List<CotacaoFornecedor> cotacaoFornecedor) {
		this.cotacaoFornecedor = cotacaoFornecedor;
	}

	public List<Cotacao> getCotacoes() {
		return cotacoes;
	}

	public void setCotacoes(List<Cotacao> cotacoes) {
		this.cotacoes = cotacoes;
	}

	public long getIdCotacaoStatus() {
		return idCotacaoStatus;
	}
	
	public void setIdCotacaoStatus(long idCotacaoStatus) {
		this.idCotacaoStatus = idCotacaoStatus;
	}
	
	public String getNmCotacaoStatus() {
		return nmCotacaoStatus;
	}
	
	public void setNmCotacaoStatus(String nmCotacaoStatus) {
		this.nmCotacaoStatus = nmCotacaoStatus;
	}
	
	public boolean isSnCotacaoStatus() {
		return snCotacaoStatus;
	}
	
	public void setSnCotacaoStatus(boolean snCotacaoStatus) {
		this.snCotacaoStatus = snCotacaoStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCotacaoStatus ^ (idCotacaoStatus >>> 32));
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
		CotacaoStatus other = (CotacaoStatus) obj;
		if (idCotacaoStatus != other.idCotacaoStatus)
			return false;
		return true;
	}
}
