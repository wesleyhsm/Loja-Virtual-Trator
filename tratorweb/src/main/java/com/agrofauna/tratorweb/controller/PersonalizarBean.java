package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.model.ClienteCultura;
import com.agrofauna.tratorweb.model.ClienteFabricante;
import com.agrofauna.tratorweb.model.ClienteProduto;
import com.agrofauna.tratorweb.model.Cultura;
import com.agrofauna.tratorweb.model.Fabricante;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.repository.ClienteCulturaRepository;
import com.agrofauna.tratorweb.repository.ClienteFabricanteRepository;
import com.agrofauna.tratorweb.repository.ClienteProdutoRepository;
import com.agrofauna.tratorweb.repository.CulturaRepository;
import com.agrofauna.tratorweb.repository.FabricanteRepository;
import com.agrofauna.tratorweb.repository.ProdutoRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PersonalizarBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CulturaRepository culturaRepository;
	
	@Inject
	private ClienteCulturaRepository clienteCulturaRepository;
	
	@Inject
	private FabricanteRepository fabricanteRepository;
	
	@Inject
	private ClienteFabricanteRepository clienteFabricanteRepository;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private ClienteProdutoRepository clienteProdutoRepository;
	
	private Cultura cultura = new Cultura();
	private ClienteCultura clienteCultura = new ClienteCultura();
	
	private Fabricante fabricante = new Fabricante();
	private ClienteFabricante clienteFabricante = new ClienteFabricante();
	
	private Produto produto = new Produto();
	private ClienteProduto clienteProduto = new ClienteProduto();
	
	public List<Cultura> buscaCulturas(){
		return culturaRepository.buscarCulturas();
	}
	
	public List<Fabricante> buscaFabricantes(){
		return fabricanteRepository.buscarFabricantes();
	}
	
	public List<Produto> buscaProdutos(){
		return produtoRepository.buscarProdutos();
	}
	
	public void salvarClienteCultura(){
		clienteCultura.setCliente(loginBean.getCliente());
		clienteCultura.setCultura(cultura);
		
		if(clienteCulturaRepository.buscarClienteCulturaExiste(clienteCultura).isEmpty()){			
			clienteCulturaRepository.salvarClienteCultura(clienteCultura);
			FacesUtil.addInfoMessage("Cultura salvo com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Cultura ja existe.");
		}
	}
	
	public void atualizarClienteCultura(ClienteCultura atualizarClienteCultura){
		atualizarClienteCultura.setCliente(loginBean.getCliente());
		
		if(clienteCulturaRepository.salvarClienteCultura(atualizarClienteCultura)){			
			FacesUtil.addInfoMessage("Cultura atualizado com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Erro ao atualizar cultura.");
		}
	}
	
	public void salvarClienteFabricante(){
		clienteFabricante.setCliente(loginBean.getCliente());
		clienteFabricante.setFabricante(fabricante);
		
		if(clienteFabricanteRepository.buscarClienteFabricanteExiste(clienteFabricante).isEmpty()){
			clienteFabricanteRepository.salvarClienteFabricantes(clienteFabricante);
			FacesUtil.addInfoMessage("Fabricante salvo com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Fabricante ja existe.");
		}
	}
	
	public void atualizarClienteFabricante(ClienteFabricante atualizarClienteFabricante){
		atualizarClienteFabricante.setCliente(loginBean.getCliente());
				
		if(clienteFabricanteRepository.salvarClienteFabricantes(atualizarClienteFabricante)){			
			FacesUtil.addInfoMessage("Fabricante atualizado com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Erro ao atualizar fabricante.");
		}		
	}
	
	public void salvarClienteProduto(){
		clienteProduto.setCliente(loginBean.getCliente());
		clienteProduto.setProduto(produto);
		
		if(clienteProdutoRepository.buscarClienteProdutoExiste(clienteProduto).isEmpty()){
			clienteProdutoRepository.salvarClienteProdutos(clienteProduto);
			FacesUtil.addInfoMessage("Produto salvo com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Produto ja existe.");
		}
	}
	
	public void removerClienteCultura(ClienteCultura clienteCultura ){
		if(listClienteCulturaCadastrada().size() >= 2){
			clienteCulturaRepository.removerClienteCultura(clienteCultura);
			FacesUtil.addInfoMessage("Cultura removida com sucesso");
		}else{
			FacesUtil.addErrorMessage("Cultura não pode ser removida.");
		}	
	}
	
	public void removerClienteFabricante(ClienteFabricante clienteFabricante ){
		if(listClienteFabricanteCadastrada().size() >= 2){
			clienteFabricanteRepository.removerClienteFabricante(clienteFabricante);			
			FacesUtil.addInfoMessage("Fabricante removido com sucesso");
		}else{
			FacesUtil.addErrorMessage("Fabricante não pode ser removida.");
		}	
	}
	
	public void removerClientePruduto(ClienteProduto clienteProduto){
		if(listClienteProdutoCadastrada().size() >= 2){
			clienteProdutoRepository.removerClienteProduto(clienteProduto);			
			FacesUtil.addInfoMessage("Produto removido com sucesso");
		}else{
			FacesUtil.addErrorMessage("Produto não pode ser removido.");
		}	
	}
	
	public List<ClienteCultura> listClienteCulturaCadastrada(){
		return clienteCulturaRepository.buscarClienteCultura(loginBean.getCliente());
	}
	
	public List<ClienteFabricante> listClienteFabricanteCadastrada(){
		return clienteFabricanteRepository.buscarClienteFabricante(loginBean.getCliente());
	}
	
	public List<ClienteProduto> listClienteProdutoCadastrada(){
		return clienteProdutoRepository.buscarClienteProduto(loginBean.getCliente());
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ClienteProduto getClienteProduto() {
		return clienteProduto;
	}

	public void setClienteProduto(ClienteProduto clienteProduto) {
		this.clienteProduto = clienteProduto;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public ClienteFabricante getClienteFabricante() {
		return clienteFabricante;
	}

	public void setClienteFabricante(ClienteFabricante clienteFabricante) {
		this.clienteFabricante = clienteFabricante;
	}

	public ClienteCultura getClienteCultura() {
		return clienteCultura;
	}

	public void setClienteCultura(ClienteCultura clienteCultura) {
		this.clienteCultura = clienteCultura;
	}

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}	
}
