package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="transportadora")
@NamedQueries({
	@NamedQuery(name="Cliente.todos", query="SELECT t FROM Transportadora t")
})	
public class Transportadora implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_transportadora")
	private long idtransportadora;
	
	@Column(name="nm_nome_fantasia")
	private String nmNomeFantasia;
		
	@Column(name="nm_razao_social")
	private String nmRazaoSocial;
	
	@Column(name="nm_endereco")
	private String nmEndereco;
	
	@Column(name="nm_bairro")
	private String nmBairro;
	
	@Column(name="nm_numero")
	private String nmNumero;
	
	@Column(name="nm_cidade")
	private String nmCidade;
	
	@Column(name="nm_cep")
	private String nmCep;
	
	@Column(name="id_estado")
	private long idEstado;
	
	@Column(name="nm_fone1")
	private String nmFone1;
	
	@Column(name="nm_fone2")
	private String nmFone2;
	
	@Column(name="nm_contato")
	private String nmContato;
	
	@Column(name="nm_observacao")
	private String nmObservacao;
	
	@Column(name="nm_cnpj")
	private String nmCnpj;
	
	@Column(name="nm_inscricao_estadual")
	private String nmInscricaoEstadual;
	
	@Column(name="nm_email")
	private String nmEmail;
	
	@Column(name="nm_site")
	private String nmSite;
	
	@Column(name="sn_status")
	private String snStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_atualizacao")
	private Date dtAtualizacao;
		
	@OneToMany(mappedBy="transportadora",fetch = FetchType.LAZY)
	private List<PedidoTransportadora> pedidoTransportadoras = new ArrayList<>();

	public long getIdtransportadora() {
		return idtransportadora;
	}

	public void setIdtransportadora(long idtransportadora) {
		this.idtransportadora = idtransportadora;
	}

	public String getNmNomeFantasia() {
		return nmNomeFantasia;
	}

	public void setNmNomeFantasia(String nmNomeFantasia) {
		this.nmNomeFantasia = nmNomeFantasia;
	}

	public String getNmRazaoSocial() {
		return nmRazaoSocial;
	}

	public void setNmRazaoSocial(String nmRazaoSocial) {
		this.nmRazaoSocial = nmRazaoSocial;
	}

	public String getNmEndereco() {
		return nmEndereco;
	}

	public void setNmEndereco(String nmEndereco) {
		this.nmEndereco = nmEndereco;
	}

	public String getNmBairro() {
		return nmBairro;
	}

	public void setNmBairro(String nmBairro) {
		this.nmBairro = nmBairro;
	}

	public String getNmNumero() {
		return nmNumero;
	}

	public void setNmNumero(String nmNumero) {
		this.nmNumero = nmNumero;
	}

	public String getNmCidade() {
		return nmCidade;
	}

	public void setNmCidade(String nmCidade) {
		this.nmCidade = nmCidade;
	}

	public String getNmCep() {
		return nmCep;
	}

	public void setNmCep(String nmCep) {
		this.nmCep = nmCep;
	}

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public String getNmFone1() {
		return nmFone1;
	}

	public void setNmFone1(String nmFone1) {
		this.nmFone1 = nmFone1;
	}

	public String getNmFone2() {
		return nmFone2;
	}

	public void setNmFone2(String nmFone2) {
		this.nmFone2 = nmFone2;
	}

	public String getNmContato() {
		return nmContato;
	}

	public void setNmContato(String nmContato) {
		this.nmContato = nmContato;
	}

	public String getNmObservacao() {
		return nmObservacao;
	}

	public void setNmObservacao(String nmObservacao) {
		this.nmObservacao = nmObservacao;
	}

	public String getNmCnpj() {
		return nmCnpj;
	}

	public void setNmCnpj(String nmCnpj) {
		this.nmCnpj = nmCnpj;
	}

	public String getNmInscricaoEstadual() {
		return nmInscricaoEstadual;
	}

	public void setNmInscricaoEstadual(String nmInscricaoEstadual) {
		this.nmInscricaoEstadual = nmInscricaoEstadual;
	}

	public String getNmEmail() {
		return nmEmail;
	}

	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	public String getNmSite() {
		return nmSite;
	}

	public void setNmSite(String nmSite) {
		this.nmSite = nmSite;
	}

	public String getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(String snStatus) {
		this.snStatus = snStatus;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public List<PedidoTransportadora> getPedidoTransportadoras() {
		return pedidoTransportadoras;
	}

	public void setPedidoTransportadoras(List<PedidoTransportadora> pedidoTransportadoras) {
		this.pedidoTransportadoras = pedidoTransportadoras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idtransportadora ^ (idtransportadora >>> 32));
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
		Transportadora other = (Transportadora) obj;
		if (idtransportadora != other.idtransportadora)
			return false;
		return true;
	}		
}
