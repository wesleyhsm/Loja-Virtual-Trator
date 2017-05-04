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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="cfop")
@NamedQueries({
	@NamedQuery(name="Cfop.findAll", query="SELECT c FROM Cfop c")	
})	
public class Cfop implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cfop")
	private long idCfop;
	
	@NotBlank  @Max(value=5) 
	@Column(name="nr_cfop", length=5)
	private String nrCfop;
	
	@NotBlank  @Max(value=150) 
	@Column(name="nm_cfop", length=150)
	private String nmCfop;
	
	@NotBlank  @Max(value=1) 
	@Column(name="nm_atualizado", length=1)
	private String nmAtualizado;
	
	@NotNull
	@Column(name="sn_status")
	private int snStatus;
	
	@OneToMany(mappedBy="cfop", fetch = FetchType.LAZY)
	private List<ProdutoTributoEspecial> produtoTributoEspecials = new ArrayList<>();
		
	@OneToMany(mappedBy="cfop", fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<ProdutoTributoEspecial> getProdutoTributoEspecials() {
		return produtoTributoEspecials;
	}

	public void setProdutoTributoEspecials(List<ProdutoTributoEspecial> produtoTributoEspecials) {
		this.produtoTributoEspecials = produtoTributoEspecials;
	}

	public long getIdCfop() {
		return idCfop;
	}

	public void setIdCfop(long idCfop) {
		this.idCfop = idCfop;
	}

	public String getNrCfop() {
		return nrCfop;
	}

	public void setNrCfop(String nrCfop) {
		this.nrCfop = nrCfop;
	}

	public String getNmCfop() {
		return nmCfop;
	}

	public void setNmCfop(String nmCfop) {
		this.nmCfop = nmCfop;
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
		result = prime * result + (int) (idCfop ^ (idCfop >>> 32));
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
		Cfop other = (Cfop) obj;
		if (idCfop != other.idCfop)
			return false;
		return true;
	}
}
