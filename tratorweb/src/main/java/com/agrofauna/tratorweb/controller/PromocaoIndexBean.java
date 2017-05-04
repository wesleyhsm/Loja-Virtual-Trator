package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.agrofauna.tratorweb.filtro.FiltroCampanhaProduto;
import com.agrofauna.tratorweb.model.CampanhaImagem;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoImagem;
import com.agrofauna.tratorweb.repository.CampanhaBannerRepository;
import com.agrofauna.tratorweb.repository.CampanhaProdutoRepository;
import com.agrofauna.tratorweb.repository.ProdutoImagemRepository;
import com.agrofauna.tratorweb.service.CalcularPrecoProduto;

@Named
@ViewScoped
public class PromocaoIndexBean implements Serializable{

	private static final long serialVersionUID = 1L;	
			
	@Inject
	private CampanhaProdutoRepository campanhaProdutoRepository;
	
	@Inject
	private CampanhaBannerRepository campanhaBannerRepository;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CalcularPrecoProduto calcalarPrecoProduto; 
	
	@Inject
	private ProdutoImagemRepository produtoImagemRepository;
		
	private FiltroCampanhaProduto filtroCampanhaProduto = new FiltroCampanhaProduto();
	private CampanhaProduto campanhaProduto = new CampanhaProduto();
	private LazyDataModel<CampanhaProduto> campanhaProdutoLazy;
	
	public PromocaoIndexBean(){
		pegaUrl();
		
		campanhaProdutoLazy = new LazyDataModel<CampanhaProduto>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<CampanhaProduto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {			
				//System.out.println("WESLEY size control:" + first);
                //System.out.println("limit control:" + pageSize);
				filtroCampanhaProduto.setCliente(loginBean.getCliente());
				
                setRowCount( campanhaProdutoRepository.produtoCampanhaPromocaoManualContador(filtroCampanhaProduto, first, pageSize) );
				return calcalarPrecoProduto.calculaPrecoProdutoList( campanhaProdutoRepository.produtoCampanhaPromocaoManualLazy(filtroCampanhaProduto, first, pageSize) );
			}
			
		};
	}
	
	//public List<CampanhaProduto> produtoJDBC(){
	//	filtroCampanhaProduto.setCliente(loginBean.getCliente());
	//	return calcalarPrecoProduto.calculaPrecoProdutoList( campanhaProdutoRepository.produtoCampanhaPromocaoManual(filtroCampanhaProduto) );		
	//}
	
	public void pegaUrl(){		
		try{
			String filtro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filtro");			
			filtroCampanhaProduto.setTipoConsulta(filtro);			
		}catch (Exception e) {			
		}		
	}
				
	public String buscarImagemProduto(Produto produto){		
		ProdutoImagem produtoImagem = produtoImagemRepository.buscarImagemProdutoPrincipal(produto);		
		if(produtoImagem == null){			
			return "http://images.agro-fauna.com.br/imgtrator/semimagem.png";
		}else{			
			return produtoImagem.getNmLink();						
		}
	}
	
	public String linkBanner(CampanhaImagem campanhaImagem){		
		if(campanhaImagem.getNmTipoImagem().equalsIgnoreCase("encomenda") && campanhaImagem.getNrIdProduto() > 0){				
			return "/encomenda/encomendaDetalhado.xhtml?id="+ campanhaImagem.getIdCampanhaProduto();				
		}else if(campanhaImagem.getNmTipoImagem().equalsIgnoreCase("promocao") && campanhaImagem.getNrIdProduto() > 0){
			return "/promocao/promocaoDetalhado.xhtml?id="+ campanhaImagem.getIdCampanhaProduto();			
		}else if(campanhaImagem.getNmTipoImagem().equalsIgnoreCase("encomenda") && campanhaImagem.getNrIdProduto() == 0){
			return "/encomenda/encomendaProduto.xhtml";
		}else{
			return "/promocao/promocaoIndex.xhtml";
		}		
	}
	
	public List<CampanhaImagem> buscaTodosBannerCampanha(){
		return campanhaBannerRepository.buscarTodasImagensCampanha(loginBean.getCliente());
	}
	
	public void setTodosProdutos(){
		filtroCampanhaProduto = new FiltroCampanhaProduto();
	}
		
	public void setTipoProduto(String tipoProduto){
		filtroCampanhaProduto.setTipoConsulta(tipoProduto);
	}
	
	public CampanhaProduto getCampanhaProduto() {
		return campanhaProduto;
	}
		
	public void setCampanhaProduto(CampanhaProduto campanhaProduto) {
		this.campanhaProduto = campanhaProduto;
	}	
		
	public FiltroCampanhaProduto getFiltroCampanhaProduto() {
		return filtroCampanhaProduto;
	}

	public void setFiltroCampanhaProduto(FiltroCampanhaProduto filtroCampanhaProduto) {
		this.filtroCampanhaProduto = filtroCampanhaProduto;
	}

	public LazyDataModel<CampanhaProduto> getCampanhaProdutoLazy() {
		return campanhaProdutoLazy;
	}

	public void setCampanhaProdutoLazy(LazyDataModel<CampanhaProduto> campanhaProdutoLazy) {
		this.campanhaProdutoLazy = campanhaProdutoLazy;
	}	
}
