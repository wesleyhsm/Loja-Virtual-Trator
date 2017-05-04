package com.agrofauna.tratorweb.model;

import java.io.Serializable;



import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the campanha_produto database table.
 * 
 */
@Entity
@Table(name="campanha_produto")

@NamedQueries({
	@NamedQuery(name="CampanhaProduto.findAll", query="SELECT c FROM CampanhaProduto c")	
	//@NamedQuery(name="CampanhaProduto.produtoPromocao", query="SELECT p FROM CampanhaCliente c inner join c.campanha.campanhaProdutos p inner join p.campanha.campanhaTipoCampanhas t WHERE t.campanhaTipo.idCampanhaTipo=1 and p.produto.snDisponivelVenda='S' and c.idCampanhaClientePessoa=:idPessoa and p.campanha.dtInicial<=:dtInicio and p.campanha.dtFinal>=:dtFim GROUP BY p.produto.idProduto ORDER BY p.produto.nmProduto"),		
	//@NamedQuery(name="CampanhaProduto.detalhado", query="SELECT cp FROM CampanhaProduto cp inner join cp.campanha c inner join c.campanhaClientes cc inner join cp.produto p inner join p.fabricante f inner join p.produtoEstoque e inner join p.produtoLote l inner join p.produtoArmazenamento a where cc.cliente.idPessoa=:idPessoa AND c.dtInicial<=:dtInicio AND c.dtFinal>=:dtFim AND cp.idProdutoCampanha=:idProdutoCampanha AND e.nrEstoqueAtual>0 AND l.snStatusLote=1")	
})

public class CampanhaProduto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_campanha")
	private long idProdutoCampanha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;

	@Column(name="nr_indice")
	private int nrIndice;

	@Column(name="sn_pagamento_antecipado")
	private int snPagamentoAntecipado;

	@Column(name="sn_ultimas_unidades")
	private int snUltimasUnidades;

	@Column(name="vl_preco_campanha")
	private double vlPrecoCampanha;

	@Column(name="nr_classifica_preco_encomenda")
	private double nrClassificaPrecoEncomenda;
	
	//bi-directional many-to-one association to Campanha
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_campanha")
	private Campanha campanha;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
			
	@OneToMany(mappedBy="campanhaProduto", fetch = FetchType.LAZY)
	private List<PedidoProduto> pedidoProdutos;
	
	public List<PedidoProduto> getPedidoProdutos() {
		return pedidoProdutos;
	}

	public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
		this.pedidoProdutos = pedidoProdutos;
	}

	public double getNrClassificaPrecoEncomenda() {
		return nrClassificaPrecoEncomenda;
	}

	public void setNrClassificaPrecoEncomenda(double nrClassificaPrecoEncomenda) {
		this.nrClassificaPrecoEncomenda = nrClassificaPrecoEncomenda;
	}
	
	public long getIdProdutoCampanha() {
		return this.idProdutoCampanha;
	}

	public void setIdProdutoCampanha(long idProdutoCampanha) {
		this.idProdutoCampanha = idProdutoCampanha;
	}

	public Date getDtCriacao() {
		return this.dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public int getNrIndice() {
		return this.nrIndice;
	}

	public void setNrIndice(int nrIndice) {
		this.nrIndice = nrIndice;
	}

	public int getSnPagamentoAntecipado() {
		return this.snPagamentoAntecipado;
	}

	public void setSnPagamentoAntecipado(int snPagamentoAntecipado) {
		this.snPagamentoAntecipado = snPagamentoAntecipado;
	}

	public int getSnUltimasUnidades() {
		return this.snUltimasUnidades;
	}

	public void setSnUltimasUnidades(int snUltimasUnidades) {
		this.snUltimasUnidades = snUltimasUnidades;
	}

	public double getVlPrecoCampanha() {
		return this.vlPrecoCampanha;
	}

	public void setVlPrecoCampanha(double vlPrecoCampanha) {
		this.vlPrecoCampanha = vlPrecoCampanha;
	}

	public Campanha getCampanha() {
		return this.campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idProdutoCampanha ^ (idProdutoCampanha >>> 32));
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
		CampanhaProduto other = (CampanhaProduto) obj;
		if (idProdutoCampanha != other.idProdutoCampanha)
			return false;
		return true;
	}
}