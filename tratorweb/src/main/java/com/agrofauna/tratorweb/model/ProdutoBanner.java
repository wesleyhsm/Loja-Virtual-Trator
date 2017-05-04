package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the produto_banner database table.
 * 
 */
@Entity
@Table(name="produto_banner")
@NamedQueries({
	@NamedQuery(name="ProdutoBanner.findAll", query="SELECT p FROM ProdutoBanner p"),	
	@NamedQuery(name="ProdutoBanner.BannerLocal1", query="SELECT p FROM ProdutoBanner p inner join p.produto.campanhaProdutos pc inner join pc.campanha.campanhaClientes cc WHERE cc.cliente.idPessoa=:idPessoa AND p.produto.snDisponivelVenda='S' AND pc.campanha.dtInicial<=:dtInicio AND pc.campanha.dtFinal>=:dtFim AND p.bannerPosicao.idBannerPosicao=1 GROUP BY p.idProdutoBanner ORDER BY p.nmProdutoBanner"),
	@NamedQuery(name="ProdutoBanner.BannerLocal2", query="SELECT p FROM ProdutoBanner p inner join p.produto.campanhaProdutos pc inner join pc.campanha.campanhaClientes cc WHERE cc.cliente.idPessoa=:idPessoa AND p.produto.snDisponivelVenda='S' AND pc.campanha.dtInicial<=:dtInicio AND pc.campanha.dtFinal>=:dtFim AND p.bannerPosicao.idBannerPosicao=2 GROUP BY p.idProdutoBanner ORDER BY p.nmProdutoBanner desc"),
	@NamedQuery(name="ProdutoBanner.BannerLocal3", query="SELECT p FROM ProdutoBanner p inner join p.produto.campanhaProdutos pc inner join pc.campanha.campanhaClientes cc WHERE cc.cliente.idPessoa=:idPessoa AND p.produto.snDisponivelVenda='S' AND pc.campanha.dtInicial<=:dtInicio AND pc.campanha.dtFinal>=:dtFim AND p.bannerPosicao.idBannerPosicao=3 GROUP BY p.idProdutoBanner ORDER BY p.nmProdutoBanner"),
	@NamedQuery(name="ProdutoBanner.BannerLocal4", query="SELECT p FROM ProdutoBanner p inner join p.produto.campanhaProdutos pc inner join pc.campanha.campanhaClientes cc WHERE cc.cliente.idPessoa=:idPessoa AND p.produto.snDisponivelVenda='S' AND pc.campanha.dtInicial<=:dtInicio AND pc.campanha.dtFinal>=:dtFim AND p.bannerPosicao.idBannerPosicao=4 GROUP BY p.idProdutoBanner ORDER BY p.nmProdutoBanner desc")
})	
public class ProdutoBanner implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_banner")
	private long idProdutoBanner;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_atualizacao")
	private Date dtAtualizacao;
	
	@Column(name="nm_produto_banner")
	private String nmProdutoBanner;
	
	@Column(name="nm_link")
	private String nmLink;

	@Column(name="sn_status")
	private boolean snStatus;

	//bi-directional many-to-one association to BannerPosicao
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_banner_posicao")
	private BannerPosicao bannerPosicao;
	
	@ManyToOne(fetch = FetchType.EAGER) // TODO: Verificar
	@JoinColumn(name="id_produto")
	private Produto produto;
				
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_funcionario")
	private Funcionario funcionario;
		
	public String getNmLink() {
		return nmLink;
	}

	public void setNmLink(String nmLink) {
		this.nmLink = nmLink;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public long getIdProdutoBanner() {
		return this.idProdutoBanner;
	}

	public void setIdProdutoBanner(long idProdutoBanner) {
		this.idProdutoBanner = idProdutoBanner;
	}

	public Date getDtCriacao() {
		return this.dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	
	public String getNmProdutoBanner() {
		return this.nmProdutoBanner;
	}

	public void setNmProdutoBanner(String nmProdutoBanner) {
		this.nmProdutoBanner = nmProdutoBanner;
	}

	public boolean getSnStatus() {
		return this.snStatus;
	}

	public void setSnStatus(boolean snStatus) {
		this.snStatus = snStatus;
	}

	public BannerPosicao getBannerPosicao() {
		return this.bannerPosicao;
	}

	public void setBannerPosicao(BannerPosicao bannerPosicao) {
		this.bannerPosicao = bannerPosicao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idProdutoBanner ^ (idProdutoBanner >>> 32));
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
		ProdutoBanner other = (ProdutoBanner) obj;
		if (idProdutoBanner != other.idProdutoBanner)
			return false;
		return true;
	}	
}