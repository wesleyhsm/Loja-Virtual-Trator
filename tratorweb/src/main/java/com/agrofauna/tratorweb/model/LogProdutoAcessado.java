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

@Entity
@Table(name="log_produto_acesso")
public class LogProdutoAcessado implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_acesso")
	private long idProdutoAcessado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_acesso")	
	private Date dtAcesso;
	
	@Column(name="nm_tipo_acesso")
	private String nmTipoAcesso; //encomenda, convencional, compre ganhe
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_pessoa_cliente")
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;

	public long getIdProdutoAcessado() {
		return idProdutoAcessado;
	}

	public void setIdProdutoAcessado(long idProdutoAcessado) {
		this.idProdutoAcessado = idProdutoAcessado;
	}

	public Date getDtAcesso() {
		return dtAcesso;
	}

	public void setDtAcesso(Date dtAcesso) {
		this.dtAcesso = dtAcesso;
	}

	public String getNmTipoAcesso() {
		return nmTipoAcesso;
	}

	public void setNmTipoAcesso(String nmTipoAcesso) {
		this.nmTipoAcesso = nmTipoAcesso;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProdutoAcessado ^ (idProdutoAcessado >>> 32));
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
		LogProdutoAcessado other = (LogProdutoAcessado) obj;
		if (idProdutoAcessado != other.idProdutoAcessado)
			return false;
		return true;
	}
}
