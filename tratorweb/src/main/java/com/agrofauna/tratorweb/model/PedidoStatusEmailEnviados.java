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

@Entity
@Table(name="pedido_status_email_enviados")
@NamedQueries({
	@NamedQuery(name="PedidoStatusEmailEnviados.todos", query="SELECT see FROM PedidoStatusEmailEnviados see")
})
public class PedidoStatusEmailEnviados implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido_status_email_enviados")
	private long idPedidoStatusEmailEnviados;
	
	@Column(name="nm_obs_pedido_status_email_enviados")	
	private String nmObsPedidoStatusEmailEnviados;
	
	@Column(name="nm_obs_interno")	
	private String nmObsInterno;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
			
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pedido")
	@NotNull
	private Pedido pedido;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pedido_status_email")
	@NotNull
	private PedidoStatusEmail pedidoStatusEmail;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Funcionario funcionario;
	
	@Column(name="sn_status")
	private int snStatus;
		
	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public long getIdPedidoStatusEmailEnviados() {
		return idPedidoStatusEmailEnviados;
	}

	public void setIdPedidoStatusEmailEnviados(long idPedidoStatusEmailEnviados) {
		this.idPedidoStatusEmailEnviados = idPedidoStatusEmailEnviados;
	}

	public String getNmObsPedidoStatusEmailEnviados() {
		return nmObsPedidoStatusEmailEnviados;
	}

	public void setNmObsPedidoStatusEmailEnviados(String nmObsPedidoStatusEmailEnviados) {
		this.nmObsPedidoStatusEmailEnviados = nmObsPedidoStatusEmailEnviados;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PedidoStatusEmail getPedidoStatusEmail() {
		return pedidoStatusEmail;
	}

	public void setPedidoStatusEmail(PedidoStatusEmail pedidoStatusEmail) {
		this.pedidoStatusEmail = pedidoStatusEmail;
	}
		
	public String getNmObsInterno() {
		return nmObsInterno;
	}

	public void setNmObsInterno(String nmObsInterno) {
		this.nmObsInterno = nmObsInterno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPedidoStatusEmailEnviados ^ (idPedidoStatusEmailEnviados >>> 32));
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
		PedidoStatusEmailEnviados other = (PedidoStatusEmailEnviados) obj;
		if (idPedidoStatusEmailEnviados != other.idPedidoStatusEmailEnviados)
			return false;
		return true;
	}
}
