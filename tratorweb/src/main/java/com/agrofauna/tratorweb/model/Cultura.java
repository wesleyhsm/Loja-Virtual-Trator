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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="cultura")
@NamedQueries({
	@NamedQuery(name="Cultura.todos", query="SELECT c FROM Cultura c WHERE c.snStatus=1 order by c.nmCultura")
})	
public class Cultura implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cultura")
	private long idCultura;
	
	@NotBlank
	@Column(name="nm_cultura")
	private String nmCultura;
	
	@Column(name="sn_status")	
	private int snStatus;

	@OneToMany(mappedBy="cultura", fetch = FetchType.LAZY)
	private List<ClienteCultura> clienteCulturas = new ArrayList<>();
			
	@OneToMany(mappedBy="cultura", fetch = FetchType.LAZY)
	private List<ProdutoCultura> produtoCulturas = new ArrayList<>();
	
	public List<ClienteCultura> getClienteCulturas() {
		return clienteCulturas;
	}

	public void setClienteCulturas(List<ClienteCultura> clienteCulturas) {
		this.clienteCulturas = clienteCulturas;
	}

	public long getIdCultura() {
		return idCultura;
	}

	public void setIdCultura(long idCultura) {
		this.idCultura = idCultura;
	}

	public String getNmCultura() {
		return nmCultura;
	}

	public void setNmCultura(String nmCultura) {
		this.nmCultura = nmCultura;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}
	
	public List<ProdutoCultura> getProdutoCulturas() {
		return produtoCulturas;
	}

	public void setProdutoCulturas(List<ProdutoCultura> produtoCulturas) {
		this.produtoCulturas = produtoCulturas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCultura ^ (idCultura >>> 32));
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
		Cultura other = (Cultura) obj;
		if (idCultura != other.idCultura)
			return false;
		return true;
	}
}
