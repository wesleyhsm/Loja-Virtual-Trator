package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the produto_armazenamento database table.
 * 
 */
@Entity
@Table(name="produto_armazenamento")
@NamedQuery(name="ProdutoArmazenamento.findAll", query="SELECT p FROM ProdutoArmazenamento p")
public class ProdutoArmazenamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_armazenamento")
	private long idProdutoArmazenamento;

	@Column(name="nr_peso_bruto")
	private double nrPesoBruto;

	@Column(name="nr_peso_liquido")
	private double nrPesoLiquido;

	@Column(name="qtd_especie_medida")
	private int qtdEspecieMedida;

	@Column(name="qtd_palete")
	private int qtdPalete;

	@Column(name="qtd_produto_volume")
	private double qtdProdutoVolume;

	@Column(name="vl_cubagem")
	private double vlCubagem;

	//bi-directional many-to-one association to ProdutoUnidade
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto_unidade_medida")
	private ProdutoUnidade produtoUnidade;

	//bi-directional many-to-one association to ProdutoEspecieMedida
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto_especie_medida")
	private ProdutoEspecieMedida produtoEspecieMedida;

	//bi-directional many-to-one association to ProdutoMedida
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto_medida")
	private ProdutoMedida produtoMedida;
	
	//bi-directional many-to-one association to Produto
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
		
	public ProdutoArmazenamento() {
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public long getIdProdutoArmazenamento() {
		return this.idProdutoArmazenamento;
	}

	public void setIdProdutoArmazenamento(long idProdutoArmazenamento) {
		this.idProdutoArmazenamento = idProdutoArmazenamento;
	}

	public double getNrPesoBruto() {
		return this.nrPesoBruto;
	}

	public void setNrPesoBruto(double nrPesoBruto) {
		this.nrPesoBruto = nrPesoBruto;
	}

	public double getNrPesoLiquido() {
		return this.nrPesoLiquido;
	}

	public void setNrPesoLiquido(double nrPesoLiquido) {
		this.nrPesoLiquido = nrPesoLiquido;
	}

	public int getQtdEspecieMedida() {
		return this.qtdEspecieMedida;
	}

	public void setQtdEspecieMedida(int qtdEspecieMedida) {
		this.qtdEspecieMedida = qtdEspecieMedida;
	}

	public int getQtdPalete() {
		return this.qtdPalete;
	}

	public void setQtdPalete(int qtdPalete) {
		this.qtdPalete = qtdPalete;
	}

	public double getQtdProdutoVolume() {
		return this.qtdProdutoVolume;
	}

	public void setQtdProdutoVolume(double qtdProdutoVolume) {
		this.qtdProdutoVolume = qtdProdutoVolume;
	}

	public double getVlCubagem() {
		return this.vlCubagem;
	}

	public void setVlCubagem(double vlCubagem) {
		this.vlCubagem = vlCubagem;
	}

	public ProdutoUnidade getProdutoUnidade() {
		return this.produtoUnidade;
	}

	public void setProdutoUnidade(ProdutoUnidade produtoUnidade) {
		this.produtoUnidade = produtoUnidade;
	}

	public ProdutoEspecieMedida getProdutoEspecieMedida() {
		return this.produtoEspecieMedida;
	}

	public void setProdutoEspecieMedida(ProdutoEspecieMedida produtoEspecieMedida) {
		this.produtoEspecieMedida = produtoEspecieMedida;
	}

	public ProdutoMedida getProdutoMedida() {
		return this.produtoMedida;
	}

	public void setProdutoMedida(ProdutoMedida produtoMedida) {
		this.produtoMedida = produtoMedida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ (int) (idProdutoArmazenamento ^ (idProdutoArmazenamento >>> 32));
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
		ProdutoArmazenamento other = (ProdutoArmazenamento) obj;
		if (idProdutoArmazenamento != other.idProdutoArmazenamento)
			return false;
		return true;
	}

}