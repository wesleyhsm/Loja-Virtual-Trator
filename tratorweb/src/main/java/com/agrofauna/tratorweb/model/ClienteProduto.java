package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="cliente_produto")
@NamedQueries({
	@NamedQuery(name="ClienteProduto.existe", query="SELECT c FROM ClienteProduto c WHERE c.cliente.idPessoa=:idPessoa AND c.produto.idProduto=:idProduto"),
	@NamedQuery(name="ClienteProduto.cadastrada", query="SELECT c FROM ClienteProduto c WHERE c.cliente.idPessoa=:idPessoa order by c.produto.nmProduto")
})
public class ClienteProduto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cliente_produto")
	private long idClienteProduto;
	
	@NotNull
	@Column(name="sn_principal")
	private boolean snPrincipal;
	
	//bi-directional many-to-one association to Campanha
	@ManyToOne
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="id_produto")
	private Produto produto;

	public long getIdClienteProduto() {
		return idClienteProduto;
	}

	public void setIdClienteProduto(long idClienteProduto) {
		this.idClienteProduto = idClienteProduto;
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
		result = prime * result + (int) (idClienteProduto ^ (idClienteProduto >>> 32));
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
		ClienteProduto other = (ClienteProduto) obj;
		if (idClienteProduto != other.idClienteProduto)
			return false;
		return true;
	}
}
