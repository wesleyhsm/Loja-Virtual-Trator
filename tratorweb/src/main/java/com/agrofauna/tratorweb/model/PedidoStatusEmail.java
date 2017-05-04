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

@Entity
@Table(name="pedido_status_email")
@NamedQueries({
	@NamedQuery(name="PedidoStatusEmail.todos", query="SELECT se FROM PedidoStatusEmail se")
})
public class PedidoStatusEmail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido_status_email")
	private long idPedidoStatusEmail;
		
	@Column(name="nm_pedido_status_email")
	@NotNull @NotBlank
	private String nmPedidoStatusEmail;
	
	@Column(name="nm_obs_padrao")	
	private String nmObsPadrao;
	
	@Column(name="sn_status")
	@NotNull @NotBlank
	private boolean snStatus;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dtAlteracao;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Funcionario funcionario;
	
	@OneToMany(mappedBy="pedidoStatusEmail",fetch = FetchType.LAZY)
	private List<PedidoStatusEmailEnviados> pedidoStatusEmailEnviados = new ArrayList<PedidoStatusEmailEnviados>();
		
	public String getNmObsPadrao() {
		return nmObsPadrao;
	}

	public void setNmObsPadrao(String nmObsPadrao) {
		this.nmObsPadrao = nmObsPadrao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<PedidoStatusEmailEnviados> getPedidoStatusEmailEnviados() {
		return pedidoStatusEmailEnviados;
	}

	public void setPedidoStatusEmailEnviados(List<PedidoStatusEmailEnviados> pedidoStatusEmailEnviados) {
		this.pedidoStatusEmailEnviados = pedidoStatusEmailEnviados;
	}

	public long getIdPedidoStatusEmail() {
		return idPedidoStatusEmail;
	}

	public void setIdPedidoStatusEmail(long idPedidoStatusEmail) {
		this.idPedidoStatusEmail = idPedidoStatusEmail;
	}

	public String getNmPedidoStatusEmail() {
		return nmPedidoStatusEmail;
	}

	public void setNmPedidoStatusEmail(String nmPedidoStatusEmail) {
		this.nmPedidoStatusEmail = nmPedidoStatusEmail;
	}

	public boolean isSnStatus() {
		return snStatus;
	}

	public void setSnStatus(boolean snStatus) {
		this.snStatus = snStatus;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPedidoStatusEmail ^ (idPedidoStatusEmail >>> 32));
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
		PedidoStatusEmail other = (PedidoStatusEmail) obj;
		if (idPedidoStatusEmail != other.idPedidoStatusEmail)
			return false;
		return true;
	}
}
