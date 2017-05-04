package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.agrofauna.tratorweb.filtro.FiltroPersonalizar;
import com.agrofauna.tratorweb.filtro.FiltroPersonalizarProduto;
import com.agrofauna.tratorweb.model.CampanhaImagem;
import com.agrofauna.tratorweb.model.ClienteProduto;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.repository.ClienteProdutoRepository;
import com.agrofauna.tratorweb.repository.ProdutoRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PersonalizarProdutoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private LoginBean loginBean;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private ClienteProdutoRepository clienteProdutoRepository;
	
	private ClienteProduto clienteProduto = new ClienteProduto();
	private FiltroPersonalizarProduto filtroPersonalizarProduto = new FiltroPersonalizarProduto();
	private FiltroPersonalizar filtroPersonalizar = new FiltroPersonalizar();
	private LazyDataModel<Produto> modelProduto;
	private LazyDataModel<ClienteProduto> modelClienteProduto;
	
	public PersonalizarProdutoBean(){
		modelProduto = new LazyDataModel<Produto>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Produto> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
								
				filtroPersonalizarProduto.setPrimeiroRegistro(first);
				filtroPersonalizarProduto.setQuantidadeRegistro(pageSize);
								
				setRowCount(produtoRepository.quantidadeFiltradosProdutoPersonalizar(filtroPersonalizarProduto));				
				return produtoRepository.listaProdutoPersonalizar(filtroPersonalizarProduto);
			}
		};
		
		modelClienteProduto = new LazyDataModel<ClienteProduto>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<ClienteProduto> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				filtroPersonalizar.setCliente(loginBean.getCliente());
				filtroPersonalizar.setPrimeiroRegistro(first);
				filtroPersonalizar.setQuantidadeRegistro(pageSize);
				
				setRowCount(clienteProdutoRepository.quantidadeFiltradosPersonalizar(filtroPersonalizar));				
				return clienteProdutoRepository.listaPersonalizar(filtroPersonalizar);
			}
		};
	}	
	
	public void mensagemFiltro(){
		FacesUtil.addInfoMessage("Produtos filtrados com sucesso.");
	}
	
	public void salvarClienteProduto(Produto produto){
		clienteProduto.setCliente(loginBean.getCliente());
		clienteProduto.setProduto(produto);
		clienteProduto.setSnPrincipal(false);
		
		if(clienteProdutoRepository.buscarClienteProdutoExiste(clienteProduto).isEmpty()){
			clienteProdutoRepository.salvarClienteProdutos(clienteProduto);
			FacesUtil.addInfoMessage("Produto salvo com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Erro produto ja existe.");
		}
	}
	
	public void atualizaClienteProduto(ClienteProduto clienteProduto){
		clienteProduto.setCliente(loginBean.getCliente());
		
		if(clienteProdutoRepository.salvarClienteProdutos(clienteProduto)){
			FacesUtil.addInfoMessage("Produto atualizado com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Erro ao atualizar produto.");
		}
	}
	
	public void removerClienteProduto(ClienteProduto clienteProduto){
		if(listClienteProdutoCadastrada().size() >= 2){
			clienteProdutoRepository.removerClienteProduto(clienteProduto);			
			FacesUtil.addInfoMessage("Produto removido com sucesso");
		}else{
			FacesUtil.addErrorMessage("Produto não pode ser removido.");
		}	
	}
	
	public List<ClienteProduto> listClienteProdutoCadastrada(){
		return clienteProdutoRepository.buscarClienteProduto(loginBean.getCliente());
	}
	
	public LazyDataModel<Produto> getModelProduto() {
		return modelProduto;
	}

	public void setModelProduto(LazyDataModel<Produto> modelProduto) {
		this.modelProduto = modelProduto;
	}

	public LazyDataModel<ClienteProduto> getModelClienteProduto() {
		return modelClienteProduto;
	}

	public void setModelClienteProduto(LazyDataModel<ClienteProduto> modelClienteProduto) {
		this.modelClienteProduto = modelClienteProduto;
	}

	public FiltroPersonalizarProduto getFiltroPersonalizarProduto() {
		return filtroPersonalizarProduto;
	}

	public void setFiltroPersonalizarProduto(FiltroPersonalizarProduto filtroPersonalizarProduto) {
		this.filtroPersonalizarProduto = filtroPersonalizarProduto;
	}

	public FiltroPersonalizar getFiltroPersonalizar() {
		return filtroPersonalizar;
	}

	public void setFiltroPersonalizar(FiltroPersonalizar filtroPersonalizar) {
		this.filtroPersonalizar = filtroPersonalizar;
	}
}
