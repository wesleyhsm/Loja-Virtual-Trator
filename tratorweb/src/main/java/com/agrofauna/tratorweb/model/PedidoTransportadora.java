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

@Entity
@Table(name="pedido_transportadora")
@NamedQueries({
	@NamedQuery(name="PedidoTransportadora.todos", query="SELECT p FROM PedidoTransportadora p")
})
public class PedidoTransportadora implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido_transportadora")
	private long idPedidoTransportadora;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pedido")
	private Pedido pedido;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_transportadora")
	private Transportadora transportadora;
	
	@Column(name="nm_via_transporte")
	private String nmViaTransporte;
	
	@Column(name="nr_valor_frete")
	private double nrValorFrete;
	
	@Column(name="nr_tipo_despacho")
	private int nrTipoDespacho; //1=despacho, 2=redespacho

	public long getIdPedidoTransportadora() {
		return idPedidoTransportadora;
	}

	public void setIdPedidoTransportadora(long idPedidoTransportadora) {
		this.idPedidoTransportadora = idPedidoTransportadora;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public String getNmViaTransporte() {
		return nmViaTransporte;
	}

	public void setNmViaTransporte(String nmViaTransporte) {
		this.nmViaTransporte = nmViaTransporte;
	}

	public double getNrValorFrete() {
		return nrValorFrete;
	}

	public void setNrValorFrete(double nrValorFrete) {
		this.nrValorFrete = nrValorFrete;
	}

	public int getNrTipoDespacho() {
		return nrTipoDespacho;
	}

	public void setNrTipoDespacho(int nrTipoDespacho) {
		this.nrTipoDespacho = nrTipoDespacho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPedidoTransportadora ^ (idPedidoTransportadora >>> 32));
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
		PedidoTransportadora other = (PedidoTransportadora) obj;
		if (idPedidoTransportadora != other.idPedidoTransportadora)
			return false;
		return true;
	}
}
