package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="setor")
public class Setor implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_setor")
	private long idSetor;
	
	@NotNull
	@Column(name="nm_setor")
	private String nmSetor;
	
	@NotNull
	@Column(name="sn_status")
	private int snStatus;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
	
	@OneToMany(mappedBy="setor")	
	private List<ContatoLogado> contatoLogados = new ArrayList<>();
		
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	public List<ContatoLogado> getContatoLogados() {
		return contatoLogados;
	}
	public void setContatoLogados(List<ContatoLogado> contatoLogados) {
		this.contatoLogados = contatoLogados;
	}
	public long getIdSetor() {
		return idSetor;
	}
	public void setIdSetor(long idSetor) {
		this.idSetor = idSetor;
	}
	public String getNmSetor() {
		return nmSetor;
	}
	public void setNmSetor(String nmSetor) {
		this.nmSetor = nmSetor;
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
		result = prime * result + (int) (idSetor ^ (idSetor >>> 32));
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
		Setor other = (Setor) obj;
		if (idSetor != other.idSetor)
			return false;
		return true;
	}
}
