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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="nota_fiscal_eletronica")
public class NotaFiscalEletronica implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_nfe")
	private long idNotaFiscalEletronica;
	
	@Column(name="nr_lote")
	private int nrLote;
	
	@Column(name="nm_recibo")
	private String nmRecibo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_envio")
	private Date dtEnvio;
	
	@Column(name="nr_status")
	private int nrStatus;
	
	@Column(name="nr_chave")
	private String nrChave;
	
	@Column(name="nr_protocolo")
	private String nrProtocolo;
	
	@Column(name="nr_numero_nota")
	private int nrNumeroNota;
	
	@Column(name="nr_ambiente")
	private int nrAmbiente;
	
	@Column(name="nm_recepcao_xml", length=2000)
	private String nmRecepcaoXml;
	
	@Column(name="nm_xml")
	private String nmXml;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pedido")
	private Pedido pedido;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_funcionario")
	private Funcionario funcionario;

	public long getIdNotaFiscalEletronica() {
		return idNotaFiscalEletronica;
	}

	public void setIdNotaFiscalEletronica(long idNotaFiscalEletronica) {
		this.idNotaFiscalEletronica = idNotaFiscalEletronica;
	}

	public int getNrLote() {
		return nrLote;
	}

	public void setNrLote(int nrLote) {
		this.nrLote = nrLote;
	}

	public String getNmRecibo() {
		return nmRecibo;
	}

	public void setNmRecibo(String nmRecibo) {
		this.nmRecibo = nmRecibo;
	}

	public Date getDtEnvio() {
		return dtEnvio;
	}

	public void setDtEnvio(Date dtEnvio) {
		this.dtEnvio = dtEnvio;
	}

	public int getNrStatus() {
		return nrStatus;
	}

	public void setNrStatus(int nrStatus) {
		this.nrStatus = nrStatus;
	}

	public String getNrChave() {
		return nrChave;
	}

	public void setNrChave(String nrChave) {
		this.nrChave = nrChave;
	}

	public String getNrProtocolo() {
		return nrProtocolo;
	}

	public void setNrProtocolo(String nrProtocolo) {
		this.nrProtocolo = nrProtocolo;
	}

	public int getNrNumeroNota() {
		return nrNumeroNota;
	}

	public void setNrNumeroNota(int nrNumeroNota) {
		this.nrNumeroNota = nrNumeroNota;
	}

	public int getNrAmbiente() {
		return nrAmbiente;
	}

	public void setNrAmbiente(int nrAmbiente) {
		this.nrAmbiente = nrAmbiente;
	}

	public String getNmRecepcaoXml() {
		return nmRecepcaoXml;
	}

	public void setNmRecepcaoXml(String nmRecepcaoXml) {
		this.nmRecepcaoXml = nmRecepcaoXml;
	}

	public String getNmXml() {
		return nmXml;
	}

	public void setNmXml(String nmXml) {
		this.nmXml = nmXml;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idNotaFiscalEletronica ^ (idNotaFiscalEletronica >>> 32));
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
		NotaFiscalEletronica other = (NotaFiscalEletronica) obj;
		if (idNotaFiscalEletronica != other.idNotaFiscalEletronica)
			return false;
		return true;
	}
}
