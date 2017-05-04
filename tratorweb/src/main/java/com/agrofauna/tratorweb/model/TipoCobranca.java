package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tipo_cobranca")
public class TipoCobranca implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_cobranca")
	private long idTipoCobranca;
	
	@Column(name="nm_tipo_cobranca")
	private String nmTipoCobranca;
	
	@Column(name="nm_baixa")
	private String nmBaixa;
	
	@Column(name="nm_fluxo")
	private String nmFluxo;
	
	@Column(name="nm_abreviacao")
	private String nmAbreviacao;
	
	@Column(name="nm_atualizado")
	private String nmAtualizado;
	
	@Column(name="sn_status")
	private int snStatus;
	
	@OneToMany(mappedBy="tipoCobranca", fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();
	
	public long getIdTipoCobranca() {
		return idTipoCobranca;
	}

	public void setIdTipoCobranca(long idTipoCobranca) {
		this.idTipoCobranca = idTipoCobranca;
	}

	public String getNmTipoCobranca() {
		return nmTipoCobranca;
	}

	public void setNmTipoCobranca(String nmTipoCobranca) {
		this.nmTipoCobranca = nmTipoCobranca;
	}

	public String getNmBaixa() {
		return nmBaixa;
	}

	public void setNmBaixa(String nmBaixa) {
		this.nmBaixa = nmBaixa;
	}

	public String getNmFluxo() {
		return nmFluxo;
	}

	public void setNmFluxo(String nmFluxo) {
		this.nmFluxo = nmFluxo;
	}

	public String getNmAbreviacao() {
		return nmAbreviacao;
	}

	public void setNmAbreviacao(String nmAbreviacao) {
		this.nmAbreviacao = nmAbreviacao;
	}

	public String getNmAtualizado() {
		return nmAtualizado;
	}

	public void setNmAtualizado(String nmAtualizado) {
		this.nmAtualizado = nmAtualizado;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idTipoCobranca ^ (idTipoCobranca >>> 32));
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
		TipoCobranca other = (TipoCobranca) obj;
		if (idTipoCobranca != other.idTipoCobranca)
			return false;
		return true;
	}
}
