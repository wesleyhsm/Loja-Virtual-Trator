package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the produto database table.
 * 
 */
@Entity
@Table(name="produto")
@NamedQueries({
	@NamedQuery(name="Produto.findAll", query="SELECT p FROM Produto p"),
	@NamedQuery(name="Produto.todos", query="SELECT p FROM Produto p WHERE p.snDisponivelVenda='S' AND p.snStatus=1 order by p.nmProduto"),
	@NamedQuery(name="Produto.nomeProduto", query="SELECT p FROM Produto p WHERE p.nmProduto like :nomeProduto AND p.snDisponivelVenda='S' AND p.snStatus=1 order by p.nmProduto")
})	
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto")
	private long idProduto;

	@Column(name="desc_aspecto")
	private String descAspecto;

	@Column(name="desc_classificacao_fiscal")
	private String descClassificacaoFiscal;

	@Column(name="desc_concentracao_quimica")
	private String descConcentracaoQuimica;

	@Column(name="desc_observacao")
	private String descObservacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dtAlteracao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;

	@Column(name="nm_produto")
	private String nmProduto;

	@Column(name="sn_especial")
	private String snEspecial;
	
	@Column(name="nr_ponto_compre_ganhe")
	private int nrPontoCompreGanhe;
	
	@Column(name="nr_ponto_compre_cliente_ganha")
	private int nrPontoCompreClienteGanhe;
	
	@Column(name="sn_compre_ganhe")
	private boolean snCompreGanhe;	

	@Column(name="sn_disponivel_venda")
	private String snDisponivelVenda;

	@Column(name="sn_epi")
	private String snEpi;

	@Column(name="sn_receita_agronomica")
	private String snReceitaAgronomica;

	@Column(name="sn_status")
	private int snStatus;
	
	//bi-directional many-to-one association to CampanhaProduto
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<CampanhaProduto> campanhaProdutos;

	//bi-directional many-to-one association to Fabricante
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_fabricante")
	private Fabricante fabricante;

	//bi-directional many-to-one association to ProdutoImagem
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<ProdutoImagem> produtoImagems;
	
	//bi-directional many-to-one association to ProdutoDadosTecnico
	@OneToOne(mappedBy="produto", fetch = FetchType.LAZY)
	private ProdutoDadosTecnico produtoDadosTecnicos;
	
	//bi-directional many-to-one association to ProdutoTipoProduto
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<ProdutoTipoProduto>  produtoTipoProduto;
	
	//bi-directional many-to-one association to ProdutoBanner
	@OneToMany(mappedBy="produto", fetch = FetchType.EAGER)
	private List<ProdutoBanner>  produtoBanner;
	
	//bi-directional many-to-one association to ProdutoLote
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<ProdutoLote> produtoLote;
		
	//bi-directional many-to-one association to ProdutoEstoque
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<ProdutoEstoque>  produtoEstoque;
	
	//bi-directional many-to-one association to ProdutoArmazenamento
	@OneToOne(mappedBy="produto", fetch = FetchType.LAZY)
	private ProdutoArmazenamento produtoArmazenamento;
	
	//bi-directional many-to-one association to Funcionario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	private Funcionario funcionario;
	
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<ProdutoTributoEspecial> produtoTributoEspecials = new ArrayList<>();
	
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<ProdutoCultura> produtoCulturas = new ArrayList<>();
		
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<PedidoProduto> pedidoProdutos = new ArrayList<>();
	
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<LogProdutoAcessado> logProdutoAcessados;
	
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
	private List<CotacaoProduto> cotacaoProdutos;
	
	@Transient
	private ProdutoImagem imagemPrincipal;
			
	public List<CotacaoProduto> getCotacaoProdutos() {
		return cotacaoProdutos;
	}

	public void setCotacaoProdutos(List<CotacaoProduto> cotacaoProdutos) {
		this.cotacaoProdutos = cotacaoProdutos;
	}

	public List<LogProdutoAcessado> getLogProdutoAcessados() {
		return logProdutoAcessados;
	}

	public void setLogProdutoAcessados(List<LogProdutoAcessado> logProdutoAcessados) {
		this.logProdutoAcessados = logProdutoAcessados;
	}

	public int getNrPontoCompreClienteGanhe() {
		return nrPontoCompreClienteGanhe;
	}

	public void setNrPontoCompreClienteGanhe(int nrPontoCompreClienteGanhe) {
		this.nrPontoCompreClienteGanhe = nrPontoCompreClienteGanhe;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<PedidoProduto> getPedidoProdutos() {
		return pedidoProdutos;
	}

	public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
		this.pedidoProdutos = pedidoProdutos;
	}

	public void setImagemPrincipal(ProdutoImagem imagemPrincipal) {
		this.imagemPrincipal = imagemPrincipal;
	}

	public String getSnEspecial() {
		return snEspecial;
	}

	public void setSnEspecial(String snEspecial) {
		this.snEspecial = snEspecial;
	}
	
	public void setNrPontoCompreGanhe(int nrPontoCompreGanhe) {
		this.nrPontoCompreGanhe = nrPontoCompreGanhe;
	}

	public List<ProdutoTributoEspecial> getProdutoTributoEspecials() {
		return produtoTributoEspecials;
	}

	public void setProdutoTributoEspecials(List<ProdutoTributoEspecial> produtoTributoEspecials) {
		this.produtoTributoEspecials = produtoTributoEspecials;
	}
			
	public ProdutoArmazenamento getProdutoArmazenamento() {
		return produtoArmazenamento;
	}

	public void setProdutoArmazenamento(ProdutoArmazenamento produtoArmazenamento) {
		this.produtoArmazenamento = produtoArmazenamento;
	}
	
	public List<ProdutoEstoque> getProdutoEstoque() {
		return produtoEstoque;
	}

	public void setProdutoEstoque(List<ProdutoEstoque> produtoEstoque) {
		this.produtoEstoque = produtoEstoque;
	}

	public List<ProdutoLote> getProdutoLote() {
		return produtoLote;
	}

	public void setProdutoLote(List<ProdutoLote> produtoLote) {
		this.produtoLote = produtoLote;
	}

	public ProdutoDadosTecnico getProdutoDadosTecnicos() {
		return produtoDadosTecnicos;
	}

	public void setProdutoDadosTecnicos(ProdutoDadosTecnico produtoDadosTecnicos) {
		this.produtoDadosTecnicos = produtoDadosTecnicos;
	}

	public List<ProdutoBanner> getProdutoBanner() {
		return produtoBanner;
	}

	public void setProdutoBanner(List<ProdutoBanner> produtoBanner) {
		this.produtoBanner = produtoBanner;
	}

	public List<ProdutoTipoProduto> getProdutoTipoProduto() {
		return produtoTipoProduto;
	}

	public void setProdutoTipoProduto(List<ProdutoTipoProduto> produtoTipoProduto) {
		this.produtoTipoProduto = produtoTipoProduto;
	}

	public long getIdProduto() {
		return this.idProduto;
	}

	public void setIdProduto(long idProduto) {
		this.idProduto = idProduto;
	}

	public String getDescAspecto() {
		return this.descAspecto;
	}

	public void setDescAspecto(String descAspecto) {
		this.descAspecto = descAspecto;
	}

	public String getDescClassificacaoFiscal() {
		return this.descClassificacaoFiscal;
	}

	public void setDescClassificacaoFiscal(String descClassificacaoFiscal) {
		this.descClassificacaoFiscal = descClassificacaoFiscal;
	}

	public String getDescConcentracaoQuimica() {
		return this.descConcentracaoQuimica;
	}

	public void setDescConcentracaoQuimica(String descConcentracaoQuimica) {
		this.descConcentracaoQuimica = descConcentracaoQuimica;
	}

	public String getDescObservacao() {
		return this.descObservacao;
	}

	public void setDescObservacao(String descObservacao) {
		this.descObservacao = descObservacao;
	}

	public Date getDtAlteracao() {
		return this.dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	public Date getDtCriacao() {
		return this.dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmProduto() {
		return this.nmProduto;
	}

	public void setNmProduto(String nmProduto) {
		this.nmProduto = nmProduto;
	}

	public int getNrPontoCompreGanhe() {
		return this.nrPontoCompreGanhe;
	}

	public String getSnDisponivelVenda() {
		return this.snDisponivelVenda;
	}

	public void setSnDisponivelVenda(String snDisponivelVenda) {
		this.snDisponivelVenda = snDisponivelVenda;
	}

	public String getSnEpi() {
		return this.snEpi;
	}

	public void setSnEpi(String snEpi) {
		this.snEpi = snEpi;
	}

	public String getSnReceitaAgronomica() {
		return this.snReceitaAgronomica;
	}

	public void setSnReceitaAgronomica(String snReceitaAgronomica) {
		this.snReceitaAgronomica = snReceitaAgronomica;
	}

	public int getSnStatus() {
		return this.snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}
	
	public List<CampanhaProduto> getCampanhaProdutos() {
		return this.campanhaProdutos;
	}

	public void setCampanhaProdutos(List<CampanhaProduto> campanhaProdutos) {
		this.campanhaProdutos = campanhaProdutos;
	}

	public CampanhaProduto addCampanhaProduto(CampanhaProduto campanhaProduto) {
		getCampanhaProdutos().add(campanhaProduto);
		campanhaProduto.setProduto(this);

		return campanhaProduto;
	}

	public CampanhaProduto removeCampanhaProduto(CampanhaProduto campanhaProduto) {
		getCampanhaProdutos().remove(campanhaProduto);
		campanhaProduto.setProduto(null);

		return campanhaProduto;
	}

	public Fabricante getFabricante() {
		return this.fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public List<ProdutoImagem> getProdutoImagems() {
		return this.produtoImagems;
	}
	
	public boolean getSnCompreGanhe() {
		return snCompreGanhe;
	}

	public void setSnCompreGanhe(boolean snCompreGanhe) {
		this.snCompreGanhe = snCompreGanhe;
	}

	public ProdutoImagem getImagemPrincipal() {
		List<ProdutoImagem> listaImagens = this.getProdutoImagems();
		if(listaImagens!=null && listaImagens.size()>0) {
			this.imagemPrincipal = listaImagens.get(0);			
		} else {
			// TODO criar objeto ProdutoImagem com uma imagem padrão
			this.imagemPrincipal.setNmProdutoImagem("ok");
		}

		return imagemPrincipal;
	}

	public void setProdutoImagems(List<ProdutoImagem> produtoImagems) {
		this.produtoImagems = produtoImagems;
	}

	public ProdutoImagem addProdutoImagem(ProdutoImagem produtoImagem) {
		getProdutoImagems().add(produtoImagem);
		produtoImagem.setProduto(this);

		return produtoImagem;
	}

	public ProdutoImagem removeProdutoImagem(ProdutoImagem produtoImagem) {
		getProdutoImagems().remove(produtoImagem);
		produtoImagem.setProduto(null);

		return produtoImagem;
	}
		
	public List<ProdutoCultura> getProdutoCulturas() {
		return produtoCulturas;
	}

	public void setProdutoCulturas(List<ProdutoCultura> produtoCulturas) {
		this.produtoCulturas = produtoCulturas;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProduto ^ (idProduto >>> 32));
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
		Produto other = (Produto) obj;
		if (idProduto != other.idProduto)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nmProduto;
	}
}