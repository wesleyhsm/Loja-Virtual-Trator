package com.agrofauna.tratorweb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.filtro.CarrinhoCompras;
import com.agrofauna.tratorweb.filtro.FiltroCampanhaProduto;
import com.agrofauna.tratorweb.filtro.ProdutoDetalhado;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.LogProdutoAcessado;
import com.agrofauna.tratorweb.model.PrincipioAtivo;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoCultura;
import com.agrofauna.tratorweb.model.ProdutoImagem;
import com.agrofauna.tratorweb.repository.CampanhaImagemRepository;
import com.agrofauna.tratorweb.repository.CampanhaProdutoRepository;
import com.agrofauna.tratorweb.repository.LogProdutoAcessadoRepository;
import com.agrofauna.tratorweb.repository.PedidoProdutoRepository;
import com.agrofauna.tratorweb.repository.ProdutoCulturaRepository;
import com.agrofauna.tratorweb.service.CalcularPrecoProduto;

@Named
@ViewScoped
public class EncomendaDetalhadaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CampanhaProdutoRepository campanhaProdutoRepository;
	
	@Inject
	private CampanhaImagemRepository campanhaImagemRepository;
	
	@Inject
	private CarrinhoDeComprasEncomendaBean carrinhoDeComprasEncomendaBean;
		
	@Inject
	private ProdutoCulturaRepository produtoCulturaRepository;
	
	@Inject
	private PedidoProdutoRepository pedidoProdutoRepository;
	
	@Inject
	private CalcularPrecoProduto calcularPrecoProduto;
	
	@Inject	
	private LogProdutoAcessadoRepository logProdutoAcessadoRepository;
		
	private FiltroCampanhaProduto filtroCampanhaProduto = new FiltroCampanhaProduto();
	private PrincipioAtivo principioAtivo = new PrincipioAtivo();
	private CampanhaProduto campanhaProduto = new CampanhaProduto();
	private ProdutoDetalhado produtoDetalhado = new ProdutoDetalhado();
	private int qtdeComprada = 0;
	
	public EncomendaDetalhadaBean(){
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			campanhaProduto.setIdProdutoCampanha( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
						
		} catch (Exception e) {
			try {
				context.getExternalContext().redirect("/tratorweb/index.xhtml");
			} catch (IOException e1) {				
			}
		}
	}

	public List<CampanhaProduto> produtoMesmoPrincioAtivoJDBC(){
		filtroCampanhaProduto.setCliente(loginBean.getCliente());
		filtroCampanhaProduto.setPrincipioAtivo(principioAtivo);
		return campanhaProdutoRepository.produtoCampanhaMesmoPricipioAtivoEncomendaManual(filtroCampanhaProduto);
	}
	
	public void adicionarProdutoCarrinho(){		
		CarrinhoCompras carrinhoCompras = new CarrinhoCompras();
		carrinhoCompras.setIdCampanha(produtoDetalhado.getId_campanha());
		carrinhoCompras.setIdProdutoCampanha(produtoDetalhado.getId_produto_campanha());
		carrinhoCompras.setIdProduto(produtoDetalhado.getId_produto());
		carrinhoCompras.setNomeProduto(produtoDetalhado.getNm_produto());
		carrinhoCompras.setQtdPorCaixa(produtoDetalhado.getQtd_especie_medida());
		carrinhoCompras.setValorUnitario(produtoDetalhado.getVl_preco_campanha());				
		carrinhoCompras.setClassificaPrecoEncomenda(produtoDetalhado.getNr_classifica_preco_encomenda());
		carrinhoCompras.setQtdProduto(qtdeComprada);
		carrinhoCompras.setSubTotal(qtdeComprada * (carrinhoCompras.getValorUnitario() * carrinhoCompras.getQtdPorCaixa()) );
		
		carrinhoDeComprasEncomendaBean.adicionaProdutoNoCarrinho(carrinhoCompras);
	}
		
	public List<ProdutoCultura> todasCulturaProduto(){
		return produtoCulturaRepository.buscarTodasCulturaProduto(campanhaProduto);
	} 
		
	public List<ProdutoImagem> listaProdutoImagems() {	
		listaProdutoDetalhado();
		List<ProdutoImagem> listProdutoImagem = campanhaImagemRepository.buscarTodasImagensProduto(campanhaProduto);
		if(listProdutoImagem.isEmpty()){
			ProdutoImagem produtoImagem = new ProdutoImagem();
			produtoImagem.setNmLink("http://images.agro-fauna.com.br/imgtrator/semimagem.png");
			listProdutoImagem.add(produtoImagem);
		}		
		return listProdutoImagem;
	}

	public void listaProdutoDetalhado() {	
		produtoDetalhado = campanhaProdutoRepository.listaCampanhaProdutoEncomendaDetalhado(campanhaProduto, loginBean.getCliente());
		principioAtivo.setIdPrincipioAtivo(produtoDetalhado.getId_principio_ativo());
		
		if(loginBean.getFuncionario() == null){
			Produto produto = new Produto();
			produto.setIdProduto(produtoDetalhado.getId_produto());
			
			//salvar log produto acessado pelo cliente
			LogProdutoAcessado logProdutoAcessado = new LogProdutoAcessado();
			logProdutoAcessado.setCliente(loginBean.getCliente());		
			logProdutoAcessado.setProduto(produto);
			logProdutoAcessado.setDtAcesso(new Date());		
			logProdutoAcessado.setNmTipoAcesso("ENCOMENDA");
			logProdutoAcessadoRepository.salvarLogProdutoAcessado(logProdutoAcessado);
		}
	}
	
	public List<CampanhaProduto> listaUltimosProdutosComprado(){
		return calcularPrecoProduto.calculaPrecoProdutoList(pedidoProdutoRepository.listarProtudosUltimasCompras(loginBean.getCliente()));
	}
	
	public String paginaCarrinho(){		
		return "/carrinhoDeCompras/carrinhoDeCompras.xhtml";
	}
	
	public String voltar() {
		return "encomenda.xhtml?faces-redirect=true";
	}

	public int getQtdeComprada() {
		return qtdeComprada;
	}

	public void setQtdeComprada(int qtdeComprada) {
		this.qtdeComprada = qtdeComprada;
	}

	public FiltroCampanhaProduto getFiltroCampanhaProduto() {
		return filtroCampanhaProduto;
	}

	public void setFiltroCampanhaProduto(FiltroCampanhaProduto filtroCampanhaProduto) {
		this.filtroCampanhaProduto = filtroCampanhaProduto;
	}

	public PrincipioAtivo getPrincipioAtivo() {
		return principioAtivo;
	}

	public void setPrincipioAtivo(PrincipioAtivo principioAtivo) {
		this.principioAtivo = principioAtivo;
	}

	public ProdutoDetalhado getProdutoDetalhado() {
		return produtoDetalhado;
	}

	public void setProdutoDetalhado(ProdutoDetalhado produtoDetalhado) {
		this.produtoDetalhado = produtoDetalhado;
	}
}
