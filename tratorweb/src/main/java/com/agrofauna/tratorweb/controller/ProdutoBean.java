package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.agrofauna.tratorweb.filtro.FiltroPesquisaProdutoIndex;
import com.agrofauna.tratorweb.model.PrincipioAtivo;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoImagem;
import com.agrofauna.tratorweb.repository.PrincipioAtivoRepository;
import com.agrofauna.tratorweb.repository.ProdutoImagemRepository;
import com.agrofauna.tratorweb.repository.ProdutoRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProdutoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private ProdutoImagemRepository produtoImagemRepository;
		
	@Inject
	private PrincipioAtivoRepository principioAtivoRepository;
	
	private List<ProdutoImagem> listProdutoImagens = new ArrayList<>();
	private LazyDataModel<Produto> model;
	private FiltroPesquisaProdutoIndex filtroPesquisaProdutoIndex = new FiltroPesquisaProdutoIndex();
	private Produto produto = new Produto();
	
	public ProdutoBean(){
		model = new LazyDataModel<Produto>() {
					
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Produto> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
										
				filtroPesquisaProdutoIndex.setPrimeiroRegistro(first);
				filtroPesquisaProdutoIndex.setQuantidadeRegistro(pageSize);												
								
				setRowCount(produtoRepository.quantidadeFiltrados(filtroPesquisaProdutoIndex));								
				return produtoRepository.listaProduto(filtroPesquisaProdutoIndex);			
			}
		};
	}

	public List<String> completeNomeProduto(String nomeProduto) {
        List<String> results = new ArrayList<String>();        
        List<Produto> produtos = produtoRepository.buscarProdutosProNome(nomeProduto);        
        for(int i = 0; i < produtos.size(); i++) {
            results.add(produtos.get(i).getNmProduto());
        }
         
        return results;
    }
	
	public void mensagemFiltro(){
		FacesUtil.addInfoMessage("Produtos filtrados com sucesso.");
	}
	
	public List<String> completeNomePrincipioAtivo(String nmPrincipioAtivo) {
        List<String> results = new ArrayList<String>();        
        List<PrincipioAtivo> principioAtivos = principioAtivoRepository.buscarPincipioAtivo(nmPrincipioAtivo);        
        for(int i = 0; i < principioAtivos.size(); i++) {
            results.add(principioAtivos.get(i).getNmPrincipioAtivo());
        }
         
        return results;
    }
	
	public void buscarImagemProduto(Produto produto){
		listProdutoImagens = produtoImagemRepository.buscarImagemPrduto(produto);
	}
		
	public LazyDataModel<Produto> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Produto> model) {
		this.model = model;
	}
		
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public FiltroPesquisaProdutoIndex getFiltroPesquisaProdutoIndex() {
		return filtroPesquisaProdutoIndex;
	}

	public void setFiltroPesquisaProdutoIndex(FiltroPesquisaProdutoIndex filtroPesquisaProdutoIndex) {
		this.filtroPesquisaProdutoIndex = filtroPesquisaProdutoIndex;
	}

	public List<ProdutoImagem> getListProdutoImagens() {
		return listProdutoImagens;
	}

	public void setListProdutoImagens(List<ProdutoImagem> listProdutoImagens) {
		this.listProdutoImagens = listProdutoImagens;
	}
}
