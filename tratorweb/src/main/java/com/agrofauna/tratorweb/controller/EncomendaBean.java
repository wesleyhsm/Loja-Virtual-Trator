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
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoImagem;
import com.agrofauna.tratorweb.repository.CampanhaProdutoRepository;
import com.agrofauna.tratorweb.repository.ProdutoImagemRepository;

@Named
@ViewScoped
public class EncomendaBean implements Serializable{

	private static final long serialVersionUID = 1L;	
	
	@Inject
	private CampanhaProdutoRepository campanhaProdutoRepository;
			
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private ProdutoImagemRepository produtoImagemRepository;
		
	private FiltroCampanhaProduto filtroCampanhaProduto = new FiltroCampanhaProduto();
	private CampanhaProduto campanhaProduto = new CampanhaProduto();
	private LazyDataModel<CampanhaProduto> campanhaProdutoLazy;
	
	public EncomendaBean(){	
		pegaUrl();	
	}
	
	public void pegaUrl(){		
		try{
			String filtro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filtro");
			
			if(filtro.length() >= 3 && filtro.length() <= 14){
				filtroCampanhaProduto.setTipoConsulta(filtro);
			}	
		}catch (Exception e) {			
		}
		
		campanhaProdutoLazy = new LazyDataModel<CampanhaProduto>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<CampanhaProduto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {			
				//System.out.println("WESLEY size control:" + first);
                //System.out.println("limit control:" + pageSize);
				filtroCampanhaProduto.setCliente(loginBean.getCliente());
				
                setRowCount( campanhaProdutoRepository.produtoCampanhaEncomendaManualContador(filtroCampanhaProduto) );
				return campanhaProdutoRepository.produtoCampanhaEncomendaManualLazy(filtroCampanhaProduto, first, pageSize); 
			}
			
		};
		
	}
	
	//public List<CampanhaProduto> produtoJDBC(){
	//	filtroCampanhaProduto.setCliente(loginBean.getCliente());
	//	return  campanhaProdutoRepository.produtoCampanhaEncomendaManual(filtroCampanhaProduto);
	//}
	
	public String buscarImagemProduto(Produto produto){		
		ProdutoImagem produtoImagem = produtoImagemRepository.buscarImagemProdutoPrincipal(produto);		
		if(produtoImagem == null){			
			return "http://images.agro-fauna.com.br/imgtrator/semimagem.png";
		}else{			
			return produtoImagem.getNmLink();						
		}
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
