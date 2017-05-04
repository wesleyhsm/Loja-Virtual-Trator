package com.agrofauna.tratorweb.model;

import java.io.Serializable;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the categoria_cliente database table.
 * 
 */
@Entity
@Table(name="categoria_cliente")

@NamedQueries({
	@NamedQuery(name="CategoriaCliente.findAll", query="SELECT c FROM CategoriaCliente c ORDER BY c.nmCategoriaCliente")
})

public class CategoriaCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_categoria_cliente")
	private long idCategoriaCliente;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
	
	@NotNull
	@Column(name="nm_categoria_cliente")
	private String nmCategoriaCliente;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="categoriaCliente", fetch = FetchType.LAZY)
	private List<Cliente> clientes;

	public CategoriaCliente() {
	}

	public long getIdCategoriaCliente() {
		return this.idCategoriaCliente;
	}

	public void setIdCategoriaCliente(long idCategoriaCliente) {
		this.idCategoriaCliente = idCategoriaCliente;
	}

	public Date getDtCriacao() {
		return this.dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmCategoriaCliente() {
		return this.nmCategoriaCliente;
	}

	public void setNmCategoriaCliente(String nmCategoriaCliente) {
		this.nmCategoriaCliente = nmCategoriaCliente;
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setCategoriaCliente(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setCategoriaCliente(null);

		return cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idCategoriaCliente ^ (idCategoriaCliente >>> 32));
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
		CategoriaCliente other = (CategoriaCliente) obj;
		if (idCategoriaCliente != other.idCategoriaCliente)
			return false;
		return true;
	}
}