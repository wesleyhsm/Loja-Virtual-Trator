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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="contato_logado")
public class ContatoLogado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_contato_logado")
	private long idContatoLogado;
	
	@NotBlank
	@Column(name="nm_titulo_assunto")
	private String nmTituloAssunto;
	
	@NotBlank
	@Column(name="nm_mensagem")
	private String nmMensagem;
	
	//bi-directional many-to-one association to 
	@ManyToOne
	@JoinColumn(name="id_setor")
	private Setor setor;
		
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
		
	@Column(name="nm_descricao_funcionario")
	private String nmDescricaoFuncionario;
	
	@NotNull
	@Column(name="sn_status")
	private int snStatus;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dtAlteracao;
	
	//bi-directional many-to-one association to 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_funcionario")
	private Funcionario funcionario;

	public long getIdContatoLogado() {
		return idContatoLogado;
	}

	public void setIdContatoLogado(long idContatoLogado) {
		this.idContatoLogado = idContatoLogado;
	}

	public String getNmTituloAssunto() {
		return nmTituloAssunto;
	}

	public void setNmTituloAssunto(String nmTituloAssunto) {
		this.nmTituloAssunto = nmTituloAssunto;
	}

	public String getNmMensagem() {
		return nmMensagem;
	}

	public void setNmMensagem(String nmMensagem) {
		this.nmMensagem = nmMensagem;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public final Date getDtCriacao() {
		return dtCriacao;
	}

	public final void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public final String getNmDescricaoFuncionario() {
		return nmDescricaoFuncionario;
	}

	public final void setNmDescricaoFuncionario(String nmDescricaoFuncionario) {
		this.nmDescricaoFuncionario = nmDescricaoFuncionario;
	}

	public final Date getDtAlteracao() {
		return dtAlteracao;
	}

	public final void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}
	
	public final int getSnStatus() {
		return snStatus;
	}

	public final void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idContatoLogado ^ (idContatoLogado >>> 32));
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
		ContatoLogado other = (ContatoLogado) obj;
		if (idContatoLogado != other.idContatoLogado)
			return false;
		return true;
	}
}	
