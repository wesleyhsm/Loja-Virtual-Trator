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
public class PromocaoDetalhadoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CampanhaProdutoRepository campanhaProdutoRepository;
	
	@Inject
	private CampanhaImagemRepository campanhaImagemRepository;
	
	@Inject
	private CarrinhoDeComprasBean carrinhoDeComprasBean;
	
	@Inject
	private CalcularPrecoProduto calcularPrecoProduto; 
	
	@Inject
	private ProdutoCulturaRepository produtoCulturaRepository;
		
	@Inject
	private PedidoProdutoRepository pedidoProdutoRepository;
	
	@Inject	
	private LogProdutoAcessadoRepository logProdutoAcessadoRepository;
			
	private FiltroCampanhaProduto filtroCampanhaProduto = new FiltroCampanhaProduto();
	private PrincipioAtivo principioAtivo = new PrincipioAtivo();
	private CampanhaProduto campanhaProduto = new CampanhaProduto();	
	private ProdutoDetalhado produtoDetalhado = new ProdutoDetalhado();
	private int qtdeComprada = 0;
	FacesContext context = FacesContext.getCurrentInstance();
	
	public PromocaoDetalhadoBean(){		
		try{
			campanhaProduto.setIdProdutoCampanha( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));			
			
		}catch (Exception e) {
			try {
				context.getExternalContext().redirect("/tratorweb/index.xhtml");
			} catch (IOException e1) {				
			}
		}		
	}
	
	public void pegaUrl(){			
	}
	
	public List<CampanhaProduto> produtoMesmoPrincioAtivoJDBC(){
		filtroCampanhaProduto.setCliente(loginBean.getCliente());
		filtroCampanhaProduto.setPrincipioAtivo(principioAtivo);
		return calcularPrecoProduto.calculaPrecoProdutoList( campanhaProdutoRepository.produtoCampanhaMesmoPricipioAtivoManual(filtroCampanhaProduto) );
	}
	
	public String paginaCarrinho(){		
		return "/carrinhoDeCompras/carrinhoDeCompras.xhtml";
	}
	
	public void adicionarProdutoCarrinho(){				
		CarrinhoCompras carrinhoCompras = new CarrinhoCompras();
		carrinhoCompras.setIdCampanha(produtoDetalhado.getId_campanha());
		carrinhoCompras.setIdProdutoCampanha(produtoDetalhado.getId_produto_campanha());
		carrinhoCompras.setIdProduto(produtoDetalhado.getId_produto());
		carrinhoCompras.setNomeProduto(produtoDetalhado.getNm_produto());
		carrinhoCompras.setQtdPorCaixa(produtoDetalhado.getQtd_especie_medida());
		carrinhoCompras.setValorUnitario(produtoDetalhado.getVl_preco_campanha());
		carrinhoCompras.setQtdProduto(qtdeComprada);
		carrinhoCompras.setSubTotal( qtdeComprada * (carrinhoCompras.getValorUnitario() * carrinhoCompras.getQtdPorCaixa()) );
				
		carrinhoDeComprasBean.adicionaProdutoNoCarrinho(carrinhoCompras);
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
		produtoDetalhado = calcularPrecoProduto.calculaPrecoProdutoNormal( campanhaProdutoRepository.listaCampanhaProdutoDetalhado(campanhaProduto, loginBean.getCliente()) );
		principioAtivo.setIdPrincipioAtivo(produtoDetalhado.getId_principio_ativo());
		
		//List<CampanhaProduto> listampanhaProduto = campanhaProdutoRepository.listaCampanhaProdutoDetalhado(campanhaProduto, loginBean.getCliente());
		//principioAtivo = listampanhaProduto.get(0).getProduto().getProdutoDadosTecnicos().getPrincipioAtivo();
		//listProdutoLote = produtoLoteRepository.buscaLoteProdutoAtivo(listampanhaProduto.get(0).getProduto().getIdProduto());
		//for(int cont = 0;cont < listampanhaProduto.size(); cont++){
		//	CampanhaProduto cp = calcularPrecoProduto.calculaPrecoProdutoNormal(listampanhaProduto.get(cont));
		//	listampanhaProduto.get(cont).setVlPrecoCampanha(cp.getVlPrecoCampanha());
		//}
		
		if(loginBean.getFuncionario() == null){
			Produto produto = new Produto();
			produto.setIdProduto(produtoDetalhado.getId_produto());		
			//salvar log produto acessado pelo cliente
			LogProdutoAcessado logProdutoAcessado = new LogProdutoAcessado();
			logProdutoAcessado.setProduto(produto);
			logProdutoAcessado.setCliente(loginBean.getCliente());				
			logProdutoAcessado.setDtAcesso(new Date());		
			logProdutoAcessado.setNmTipoAcesso("PROMOCAO");
			logProdutoAcessadoRepository.salvarLogProdutoAcessado(logProdutoAcessado);	
		}
	}
		
	public List<ProdutoCultura> todasCulturaProduto(){
		return produtoCulturaRepository.buscarTodasCulturaProduto(campanhaProduto);
	} 
	
	public List<CampanhaProduto> listaUltimosProdutosComprado(){
		return calcularPrecoProduto.calculaPrecoProdutoList(pedidoProdutoRepository.listarProtudosUltimasCompras(loginBean.getCliente()));
	}
	
	public CampanhaProduto getCampanhaProduto() {
		return campanhaProduto;
	}
	
	public String voltar() {
		return "produtoindex.xhtml?faces-redirect=true";
	}
	
	public void setCampanhaProduto(CampanhaProduto campanhaProduto) {
		this.campanhaProduto = campanhaProduto;
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
