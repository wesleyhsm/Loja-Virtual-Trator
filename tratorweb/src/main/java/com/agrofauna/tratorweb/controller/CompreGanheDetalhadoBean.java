package com.agrofauna.tratorweb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.agrofauna.tratorweb.filtro.CarrinhoCompras;
import com.agrofauna.tratorweb.filtro.FiltroCampanhaProduto;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.LogProdutoAcessado;
import com.agrofauna.tratorweb.model.PrincipioAtivo;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoCultura;
import com.agrofauna.tratorweb.model.ProdutoImagem;
import com.agrofauna.tratorweb.repository.CampanhaImagemRepository;
import com.agrofauna.tratorweb.repository.LogProdutoAcessadoRepository;
import com.agrofauna.tratorweb.repository.PedidoProdutoRepository;
import com.agrofauna.tratorweb.repository.ProdutoCulturaRepository;
import com.agrofauna.tratorweb.repository.ProdutoRepository;
import com.agrofauna.tratorweb.service.CalcularPrecoProduto;

@Named
@ViewScoped
public class CompreGanheDetalhadoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CampanhaImagemRepository campanhaImagemRepository;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private CarrinhodeComprasCompreGanheBean carrinhodeComprasCompreGanheBean; 
	
	@Inject
	private ProdutoCulturaRepository produtoCulturaRepository;
	
	@Inject
	private PedidoProdutoRepository pedidoProdutoRepository;
	
	@Inject
	private CalcularPrecoProduto calcularPrecoProduto;
	
	@Inject	
	private LogProdutoAcessadoRepository logProdutoAcessadoRepository;
	
	private FiltroCampanhaProduto filtroCampanhaProduto = new FiltroCampanhaProduto();
	private Produto produto = new Produto();
	private PrincipioAtivo principioAtivo = new PrincipioAtivo();
	private int qtdeComprada = 0;
	private LazyDataModel<Produto> model;
	
	public CompreGanheDetalhadoBean(){
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			produto.setIdProduto( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
			
			model = new LazyDataModel<Produto>() {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public List<Produto> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {				
				
					filtroCampanhaProduto.setPrincipioAtivo(principioAtivo);										
					filtroCampanhaProduto.setPrimeiroRegistro(first);					
					filtroCampanhaProduto.setQuantidadeRegistro(pageSize);
																	
					setRowCount(produtoRepository.quantidadeFiltradosProdutoMesmoPrincipioAtivoCompreGanhe(filtroCampanhaProduto));	
					
					return produtoRepository.listaProdutoMesmoPrincipioAtivoCompreGanhe(filtroCampanhaProduto);
				}
			};
		} catch (Exception e) {
			try {
				context.getExternalContext().redirect("/TratorWeb/index.xhtml");
			} catch (IOException e1) {				
			}
		}
	}
	
	public void adicionarProdutoCarrinho(){	
		CarrinhoCompras carrinhoCompras = new CarrinhoCompras();		
		carrinhoCompras.setIdProduto(listaProdutoDetalhado().get(0).getIdProduto());
		carrinhoCompras.setNomeProduto(listaProdutoDetalhado().get(0).getNmProduto());
		carrinhoCompras.setQtdPorCaixa(listaProdutoDetalhado().get(0).getProdutoArmazenamento().getQtdEspecieMedida());
		carrinhoCompras.setValorUnitario(listaProdutoDetalhado().get(0).getNrPontoCompreGanhe());		
		carrinhoCompras.setQtdProduto(qtdeComprada);
		carrinhoCompras.setSubTotal( qtdeComprada * (carrinhoCompras.getValorUnitario() * carrinhoCompras.getQtdPorCaixa()));
		
		carrinhodeComprasCompreGanheBean.adicionaProdutoNoCarrinhoCompreGanhe(carrinhoCompras);
	}
	
	public List<ProdutoCultura> todasCulturaProduto(){
		return produtoCulturaRepository.buscarTodasCulturaProdutoCompreGanhe(produto);
	} 
	
	public List<ProdutoImagem> listaProdutoImagems() {		
		List<ProdutoImagem> listProdutoImagem = campanhaImagemRepository.buscarTodasImagensProdutoCompreGanhe(produto);		
		if(listProdutoImagem.isEmpty()){
			ProdutoImagem produtoImagem = new ProdutoImagem();
			produtoImagem.setNmLink("http://images.agro-fauna.com.br/imgtrator/semimagem.png");
			listProdutoImagem.add(produtoImagem);
		}		
		return listProdutoImagem;
	}
	
	public List<Produto> listaProdutoDetalhado() {	
		List<Produto> listProduto = produtoRepository.listProdutoDetalhadoCompreGanhe(produto);
		principioAtivo = listProduto.get(0).getProdutoDadosTecnicos().getPrincipioAtivo();
		
		if(loginBean.getFuncionario() == null){
			//salvar log produto acessado pelo cliente
			LogProdutoAcessado logProdutoAcessado = new LogProdutoAcessado();
			logProdutoAcessado.setCliente(loginBean.getCliente());
			logProdutoAcessado.setProduto(listProduto.get(0));
			logProdutoAcessado.setDtAcesso(new Date());		
			logProdutoAcessado.setNmTipoAcesso("COMPRE GANHE");
			logProdutoAcessadoRepository.salvarLogProdutoAcessado(logProdutoAcessado);
		}
		return listProduto;
	}
	
	public List<CampanhaProduto> listaUltimosProdutosComprado(){
		return calcularPrecoProduto.calculaPrecoProdutoList(pedidoProdutoRepository.listarProtudosUltimasCompras(loginBean.getCliente()));
	}
	
	public String paginaCarrinho(){
		return "/carrinhoDeCompras/carrinhoDeCompras.xhtml";
	}
	
	public String voltar() {
		return "compreGanhe.xhtml?faces-redirect=true";
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public PrincipioAtivo getPrincipioAtivo() {
		return principioAtivo;
	}

	public void setPrincipioAtivo(PrincipioAtivo principioAtivo) {
		this.principioAtivo = principioAtivo;
	}

	public int getQtdeComprada() {
		return qtdeComprada;
	}

	public void setQtdeComprada(int qtdeComprada) {
		this.qtdeComprada = qtdeComprada;
	}

	public LazyDataModel<Produto> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Produto> model) {
		this.model = model;
	}

	public FiltroCampanhaProduto getFiltroCampanhaProduto() {
		return filtroCampanhaProduto;
	}

	public void setFiltroCampanhaProduto(FiltroCampanhaProduto filtroCampanhaProduto) {
		this.filtroCampanhaProduto = filtroCampanhaProduto;
	}
}
