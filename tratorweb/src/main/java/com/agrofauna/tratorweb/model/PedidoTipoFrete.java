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
@Table(name="pedido_tipo_frete")
public class PedidoTipoFrete implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido_tipo_frete")
	private long idPedidoTipoFrete;
	
	@Column(name="nm_pedido_tipo_frete")
	private String nmPedidoTipoFrete;
	
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
	public long getIdPedidoTipoFrete() {
		return idPedidoTipoFrete;
	}
	public void setIdPedidoTipoFrete(long idPedidoTipoFrete) {
		this.idPedidoTipoFrete = idPedidoTipoFrete;
	}
	public String getNmPedidoTipoFrete() {
		return nmPedidoTipoFrete;
	}
	public void setNmPedidoTipoFrete(String nmPedidoTipoFrete) {
		this.nmPedidoTipoFrete = nmPedidoTipoFrete;
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
		result = prime * result + (int) (idPedidoTipoFrete ^ (idPedidoTipoFrete >>> 32));
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
		PedidoTipoFrete other = (PedidoTipoFrete) obj;
		if (idPedidoTipoFrete != other.idPedidoTipoFrete)
			return false;
		return true;
	}
}
