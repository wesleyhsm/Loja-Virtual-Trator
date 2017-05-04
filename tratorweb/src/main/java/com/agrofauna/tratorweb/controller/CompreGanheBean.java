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

import com.agrofauna.tratorweb.filtro.FiltroProduto;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.ClienteCompreGanhe;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoImagem;
import com.agrofauna.tratorweb.repository.ClienteCompreGanheRepository;
import com.agrofauna.tratorweb.repository.ProdutoImagemRepository;
import com.agrofauna.tratorweb.repository.ProdutoRepository;

@Named
@ViewScoped
public class CompreGanheBean  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteCompreGanheRepository clienteCompreGanheRepository;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private ProdutoImagemRepository produtoImagemRepository;
	
	private ClienteCompreGanhe clienteCompreGanhe = new ClienteCompreGanhe();
	private FiltroProduto filtroProduto = new FiltroProduto();
		
	public CompreGanheBean(){
		pegaUrl();
		
		/*model = new LazyDataModel<Produto>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Produto> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
								
				filtroProduto.setPrimeiroRegistro(first);
				filtroProduto.setQuantidadeRegistro(pageSize);
																
				setRowCount(produtoRepository.quantidadeFiltradosProdutoCompreGanhe(filtroProduto));	
				
				return produtoRepository.listaProdutoCompreGanhe(filtroProduto);
			}
		};*/	
	}
	
	public List<Produto> produtoJDBC(){		
		return produtoRepository.listaProdutoCompreGanheManual(filtroProduto);
	}
	
	public void pegaUrl(){		
		try{
			String filtro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filtro");
			
			if(filtro.length() >= 3 && filtro.length() <= 14){
				filtroProduto.setTipoConsulta(filtro);
			}
		}catch (Exception e) {			
		}		
	}
	
	public String buscarImagemProduto(Produto produto){
		ProdutoImagem produtoImagem = produtoImagemRepository.buscarImagemProdutoPrincipal(produto);
		if(produtoImagem == null){
			return "http://images.agro-fauna.com.br/fotos_novo_trator/indisponivel.png";
		}else{
			return produtoImagem.getNmLink();
		}
	}
	
	public void setTodosProdutos(){
		filtroProduto = new FiltroProduto();
	}
	
	public double pontosCompreGanheCliente(){
		clienteCompreGanhe = clienteCompreGanheRepository.buscarClienteCompreGanhe(loginBean.getCliente());
		if(clienteCompreGanhe != null){
			return clienteCompreGanhe.getNrQuantidadePontos();
		}else{
			return 0;
		}
	}
		
	public void setTipoProduto(String tipoProduto){
		filtroProduto.setTipoConsulta(tipoProduto);
	}
	
	public FiltroProduto getFiltroProduto() {
		return filtroProduto;
	}

	public void setFiltroProduto(FiltroProduto filtroProduto) {
		this.filtroProduto = filtroProduto;
	}

	public ClienteCompreGanhe getClienteCompreGanhe() {
		return clienteCompreGanhe;
	}

	public void setClienteCompreGanhe(ClienteCompreGanhe clienteCompreGanhe) {
		this.clienteCompreGanhe = clienteCompreGanhe;
	}
}	
