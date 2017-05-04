package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the produto_estoque database table.
 * 
 */
@Entity
@Table(name="produto_estoque")
@NamedQuery(name="ProdutoEstoque.findAll", query="SELECT p FROM ProdutoEstoque p")
public class ProdutoEstoque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_estoque")
	private long idProdutoEstoque;

	@Column(name="nr_estoque_atual")
	private int nrEstoqueAtual;

	@Column(name="nr_estoque_ideal")
	private int nrEstoqueIdeal;

	@Column(name="nr_estoque_maximo")
	private int nrEstoqueMaximo;

	@Column(name="nr_estoque_minimo")
	private int nrEstoqueMinimo;

	@Column(name="nr_reserva")
	private int nrReserva;

	@Column(name="nr_troca")
	private int nrTroca;

	//bi-directional many-to-one association to ProdutoEstoqueTipo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto_estoque_tipo")
	private ProdutoEstoqueTipo produtoEstoqueTipo;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
		
	public ProdutoEstoque() {
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public long getIdProdutoEstoque() {
		return this.idProdutoEstoque;
	}

	public void setIdProdutoEstoque(long idProdutoEstoque) {
		this.idProdutoEstoque = idProdutoEstoque;
	}
	
	public int getNrEstoqueAtual() {
		return this.nrEstoqueAtual;
	}

	public void setNrEstoqueAtual(int nrEstoqueAtual) {
		this.nrEstoqueAtual = nrEstoqueAtual;
	}

	public int getNrEstoqueIdeal() {
		return this.nrEstoqueIdeal;
	}

	public void setNrEstoqueIdeal(int nrEstoqueIdeal) {
		this.nrEstoqueIdeal = nrEstoqueIdeal;
	}

	public int getNrEstoqueMaximo() {
		return this.nrEstoqueMaximo;
	}

	public void setNrEstoqueMaximo(int nrEstoqueMaximo) {
		this.nrEstoqueMaximo = nrEstoqueMaximo;
	}

	public int getNrEstoqueMinimo() {
		return this.nrEstoqueMinimo;
	}

	public void setNrEstoqueMinimo(int nrEstoqueMinimo) {
		this.nrEstoqueMinimo = nrEstoqueMinimo;
	}

	public int getNrReserva() {
		return this.nrReserva;
	}

	public void setNrReserva(int nrReserva) {
		this.nrReserva = nrReserva;
	}

	public int getNrTroca() {
		return this.nrTroca;
	}

	public void setNrTroca(int nrTroca) {
		this.nrTroca = nrTroca;
	}

	public ProdutoEstoqueTipo getProdutoEstoqueTipo() {
		return this.produtoEstoqueTipo;
	}

	public void setProdutoEstoqueTipo(ProdutoEstoqueTipo produtoEstoqueTipo) {
		this.produtoEstoqueTipo = produtoEstoqueTipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idProdutoEstoque ^ (idProdutoEstoque >>> 32));
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
		ProdutoEstoque other = (ProdutoEstoque) obj;
		if (idProdutoEstoque != other.idProdutoEstoque)
			return false;
		return true;
	}
	
}