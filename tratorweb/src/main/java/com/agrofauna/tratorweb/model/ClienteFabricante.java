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
@Table(name="cliente_fabricante")
@NamedQueries({
	@NamedQuery(name="ClienteFabricante.existe", query="SELECT c FROM ClienteFabricante c WHERE c.cliente.idPessoa=:idPessoa AND c.fabricante.idFabricante=:idFabricante"),
	@NamedQuery(name="ClienteFabricante.cadastrada", query="SELECT c FROM ClienteFabricante c inner join c.fabricante f WHERE c.cliente.idPessoa=:idPessoa order by f.nmRazaoSocial")
})
public class ClienteFabricante implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cliente_fabricante")
	private long idClienteFabricante;
	
	@NotNull
	@Column(name="sn_principal")
	private boolean snPrincipal;
	
	//bi-directional many-to-one association to Campanha
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_fabricante")
	private Fabricante fabricante;

	public long getIdClienteFabricante() {
		return idClienteFabricante;
	}

	public void setIdClienteFabricante(long idClienteFabricante) {
		this.idClienteFabricante = idClienteFabricante;
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

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idClienteFabricante ^ (idClienteFabricante >>> 32));
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
		ClienteFabricante other = (ClienteFabricante) obj;
		if (idClienteFabricante != other.idClienteFabricante)
			return false;
		return true;
	}
}
