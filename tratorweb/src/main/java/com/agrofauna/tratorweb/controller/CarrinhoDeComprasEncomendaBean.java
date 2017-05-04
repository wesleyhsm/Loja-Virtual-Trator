package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.filtro.CarrinhoCompras;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@SessionScoped
public class CarrinhoDeComprasEncomendaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CarrinhoDeComprasBean carrinhoDeComprasBean;
	
	@Inject
	private CarrinhodeComprasCompreGanheBean carrinhodeComprasCompreGanheBean;	
	
	private boolean incluirFrete;
	private int tipoFrete;
	private boolean liberaFinalizaCompra = false;
	private List<CarrinhoCompras> listaCarrinhoDeCompras = new ArrayList<CarrinhoCompras>();
		
	public void adicionaProdutoNoCarrinho(CarrinhoCompras carrinhoCompras){
		
		if(carrinhoCompras.getQtdProduto() >= 1){		
			
			boolean produtoExste = false;
			
			for(int cont = 0;cont < listCarrinhoCompras().size(); cont++){				
				if(carrinhoCompras.getIdProdutoCampanha() == getListaCarrinhoDeCompras().get(cont).getIdProdutoCampanha()){
					
					produtoExste = true;					
					getListaCarrinhoDeCompras().get(cont).setQtdProduto(carrinhoCompras.getQtdProduto());					
					getListaCarrinhoDeCompras().get(cont).setSubTotal( carrinhoCompras.getQtdProduto() * (carrinhoCompras.getValorUnitario() * carrinhoCompras.getQtdPorCaixa()) );
					
					FacesUtil.addInfoMessage("Produto alterado com sucesso.");					
				}
			}
			
			if(produtoExste == false){
				this.listaCarrinhoDeCompras.add(carrinhoCompras);
				FacesUtil.addInfoMessage("Produto adicionado com sucesso.");
			}
		}else{
			FacesUtil.addErrorMessage("Quantidade produto invalido.");
		}		
	}
	
	public void removeTodosProdutoCarrinhoCompras(){
		listaCarrinhoDeCompras = new ArrayList<CarrinhoCompras>();
	}
	
	public String botaoFinalizarCompraEncomenda(){
		if(listaCarrinhoDeCompras.size() >= 1 ){			
			liberaFinalizaCompra = true;
			carrinhoDeComprasBean.setLiberaFinalizaCompra(false);
			carrinhodeComprasCompreGanheBean.setLiberaFinalizaCompra(false);
						
			return "/finalizacaoDeCompras/frete.xhtml";
		}
		FacesUtil.addErrorMessage("Adicione produto no carrinho para finalizar a compra.");		
		return "";
	}
	
	public double totalEncomendaInicioProdutoCarrinho(){
		double totalCarrinho = 0;
		for(int cont = 0;cont < listCarrinhoCompras().size(); cont++){
			totalCarrinho = totalCarrinho + ((listCarrinhoCompras().get(cont).getSubTotal() / listCarrinhoCompras().get(cont).getClassificaPrecoEncomenda()));
		}
		return totalCarrinho;
	}
	
	public double totalEncomendaFimProdutoCarrinho(){
		double totalCarrinho = 0;
		for(int cont = 0;cont < listCarrinhoCompras().size(); cont++){
			totalCarrinho = totalCarrinho + (((listCarrinhoCompras().get(cont).getSubTotal() / listCarrinhoCompras().get(cont).getClassificaPrecoEncomenda()) / listCarrinhoCompras().get(cont).getClassificaPrecoEncomenda()));
		}
		return totalCarrinho;
	}
	
	public void removerProdutoCarrinho(CarrinhoCompras carrinhoCompras){
		listCarrinhoCompras().remove(carrinhoCompras);
	}
	
	public List<CarrinhoCompras> listCarrinhoCompras(){
		return this.listaCarrinhoDeCompras;
	} 
	
	public int getContagemProdutos() {
		return this.listaCarrinhoDeCompras.size();
	}

	public List<CarrinhoCompras> getListaCarrinhoDeCompras() {
		return listaCarrinhoDeCompras;
	}

	public void setListaCarrinhoDeCompras(List<CarrinhoCompras> listaCarrinhoDeCompras) {
		this.listaCarrinhoDeCompras = listaCarrinhoDeCompras;
	}

	public boolean isLiberaFinalizaCompra() {
		return liberaFinalizaCompra;
	}

	public void setLiberaFinalizaCompra(boolean liberaFinalizaCompra) {
		this.liberaFinalizaCompra = liberaFinalizaCompra;
	}

	public int getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(int tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public boolean isIncluirFrete() {
		return incluirFrete;
	}

	public void setIncluirFrete(boolean incluirFrete) {
		this.incluirFrete = incluirFrete;
	}
}
