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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pedido_status")
public class PedidoStatus implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido_status")
	private long idPedidoStatus;
	
	@Column(name="nm_pedido_status")
	private String nmPedidoStatus;
	
	@Column(name="nm_sigla_pedido_status")
	private String nmSiglaPedidoStatus;

	@Column(name="sn_status")
	private int snStatus;
	
	@OneToMany(mappedBy="pedidoStatus", fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();
		
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public long getIdPedidoStatus() {
		return idPedidoStatus;
	}

	public void setIdPedidoStatus(long idPedidoStatus) {
		this.idPedidoStatus = idPedidoStatus;
	}

	public String getNmPedidoStatus() {
		return nmPedidoStatus;
	}

	public void setNmPedidoStatus(String nmPedidoStatus) {
		this.nmPedidoStatus = nmPedidoStatus;
	}

	public String getNmSiglaPedidoStatus() {
		return nmSiglaPedidoStatus;
	}

	public void setNmSiglaPedidoStatus(String nmSiglaPedidoStatus) {
		this.nmSiglaPedidoStatus = nmSiglaPedidoStatus;
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
		result = prime * result + (int) (idPedidoStatus ^ (idPedidoStatus >>> 32));
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
		PedidoStatus other = (PedidoStatus) obj;
		if (idPedidoStatus != other.idPedidoStatus)
			return false;
		return true;
	}
}
