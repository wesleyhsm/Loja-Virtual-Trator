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
@Table(name="pedido_produto")
@NamedQueries({
	@NamedQuery(name="PedidoProduto.todos", query="SELECT p FROM PedidoProduto p"),	
})
public class PedidoProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido_produto")
	private long idPedidoProduto;
	
	@Column(name="nr_qtd_produto")
	private int nrQuantidadeProduto;
	
	@Column(name="nr_preco_venda")
	private double nrPrecoVenda;
	
	@Column(name="nr_preco_custo")
	private double nrPrecoCusto;
	
	@Column(name="nr_classifica_preco_encomenda")
	private double nrClassificaPrecoEncomenda;
	
	@Column(name="nr_compre_ganhe")
	private double nrCompreGanhe;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto_campanha")
	private CampanhaProduto campanhaProduto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pedido")
	private Pedido pedido;
	
	public long getIdPedidoProduto() {
		return idPedidoProduto;
	}

	public void setIdPedidoProduto(long idPedidoProduto) {
		this.idPedidoProduto = idPedidoProduto;
	}

	public int getNrQuantidadeProduto() {
		return nrQuantidadeProduto;
	}

	public void setNrQuantidadeProduto(int nrQuantidadeProduto) {
		this.nrQuantidadeProduto = nrQuantidadeProduto;
	}

	public double getNrPrecoVenda() {
		return nrPrecoVenda;
	}

	public void setNrPrecoVenda(double nrPrecoVenda) {
		this.nrPrecoVenda = nrPrecoVenda;
	}

	public double getNrPrecoCusto() {
		return nrPrecoCusto;
	}

	public void setNrPrecoCusto(double nrPrecoCusto) {
		this.nrPrecoCusto = nrPrecoCusto;
	}

	public double getNrCompreGanhe() {
		return nrCompreGanhe;
	}

	public void setNrCompreGanhe(double nrCompreGanhe) {
		this.nrCompreGanhe = nrCompreGanhe;
	}

	public CampanhaProduto getCampanhaProduto() {
		return campanhaProduto;
	}

	public void setCampanhaProduto(CampanhaProduto campanhaProduto) {
		this.campanhaProduto = campanhaProduto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public double getNrClassificaPrecoEncomenda() {
		return nrClassificaPrecoEncomenda;
	}

	public void setNrClassificaPrecoEncomenda(double nrClassificaPrecoEncomenda) {
		this.nrClassificaPrecoEncomenda = nrClassificaPrecoEncomenda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPedidoProduto ^ (idPedidoProduto >>> 32));
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
		PedidoProduto other = (PedidoProduto) obj;
		if (idPedidoProduto != other.idPedidoProduto)
			return false;
		return true;
	}
}
