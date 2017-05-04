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
@Table(name="log_funcionario_login")
public class LogFuncionarioLogin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_log_funcionario_login")
	private long idLogFuncionarioLogin;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_log_entrada")
	private Date dtLogEntrada;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_log_saida")
	private Date dtLogSaida;
	
	@Column(name="sn_gero_pedido")
	private boolean snGeroPedido;
	
	@Column(name="nm_navegador")
	private String nmNavegador;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Funcionario funcionario;
	
	public boolean isSnGeroPedido() {
		return snGeroPedido;
	}

	public void setSnGeroPedido(boolean snGeroPedido) {
		this.snGeroPedido = snGeroPedido;
	}

	public long getIdLogFuncionarioLogin() {
		return idLogFuncionarioLogin;
	}

	public void setIdLogFuncionarioLogin(long idLogFuncionarioLogin) {
		this.idLogFuncionarioLogin = idLogFuncionarioLogin;
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public String isNmNavegador() {
		return nmNavegador;
	}

	public void setNmNavegador(String nmNavegador) {
		this.nmNavegador = nmNavegador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idLogFuncionarioLogin ^ (idLogFuncionarioLogin >>> 32));
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
		LogFuncionarioLogin other = (LogFuncionarioLogin) obj;
		if (idLogFuncionarioLogin != other.idLogFuncionarioLogin)
			return false;
		return true;
	}
}
