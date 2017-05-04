package com.agrofauna.tratorweb.model;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;

@Entity
@Table(name="cliente_cultura")
@NamedQueries({
	@NamedQuery(name="ClienteCultura.existe", query="SELECT c FROM ClienteCultura c WHERE c.cliente.idPessoa=:idPessoa AND c.cultura.idCultura=:idCultura"),
	@NamedQuery(name="ClienteCultura.cadastrada", query="SELECT c FROM ClienteCultura c  WHERE c.cliente.idPessoa=:idPessoa order by c.cultura.nmCultura")
})
public class ClienteCultura implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cliente_cultura")
	private long idClienteCultura;
	
	@NotNull
	@Column(name="sn_principal")
	private boolean snPrincipal;
	
	//bi-directional many-to-one association to Campanha
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cultura")
	private Cultura cultura;

	public long getIdClienteCultura() {
		return idClienteCultura;
	}

	public void setIdClienteCultura(long idClienteCultura) {
		this.idClienteCultura = idClienteCultura;
	}

	public boolean getSnPrincipal() {
		return snPrincipal;
	}

	public void setSnPrincipal(boolean snPrincipal) {
		this.snPrincipal = snPrincipal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idClienteCultura ^ (idClienteCultura >>> 32));
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
		ClienteCultura other = (ClienteCultura) obj;
		if (idClienteCultura != other.idClienteCultura)
			return false;
		return true;
	}
}
