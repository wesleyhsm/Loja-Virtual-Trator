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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="log_cliente_login")
public class LogClienteLogin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_log_cliente_login")
	private long idLogClienteLogin;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_log_entrada")
	private Date dtLogEntrada;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_log_saida")
	private Date dtLogSaida;
	
	@Column(name="sn_gero_pedido")
	private boolean snGeroPedido;
	
	@Column(name="nm_navegador")
	private String nmNavegador;
	
	@Column(name="nm_ip")
	private String nmIp;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;
	
	public String getNmNavegador() {
		return nmNavegador;
	}

	public void setNmNavegador(String nmNavegador) {
		this.nmNavegador = nmNavegador;
	}

	public long getIdLogClienteLogin() {
		return idLogClienteLogin;
	}

	public void setIdLogClienteLogin(long idLogClienteLogin) {
		this.idLogClienteLogin = idLogClienteLogin;
	}

	public Date getDtLogEntrada() {
		return dtLogEntrada;
	}

	public void setDtLogEntrada(Date dtLogEntrada) {
		this.dtLogEntrada = dtLogEntrada;
	}

	public Date getDtLogSaida() {
		return dtLogSaida;
	}

	public void setDtLogSaida(Date dtLogSaida) {
		this.dtLogSaida = dtLogSaida;
	}

	public boolean isSnGeroPedido() {
		return snGeroPedido;
	}

	public void setSnGeroPedido(boolean snGeroPedido) {
		this.snGeroPedido = snGeroPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
		
	public String getNmIp() {
		return nmIp;
	}

	public void setNmIp(String nmIp) {
		this.nmIp = nmIp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idLogClienteLogin ^ (idLogClienteLogin >>> 32));
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
		LogClienteLogin other = (LogClienteLogin) obj;
		if (idLogClienteLogin != other.idLogClienteLogin)
			return false;
		return true;
	}
	
}
