package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.filtro.CarrinhoCompras;
import com.agrofauna.tratorweb.repository.ProdutoRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@SessionScoped
public class CarrinhoDeComprasBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CarrinhodeComprasCompreGanheBean carrinhodeComprasCompreGanheBean;
	
	@Inject
	private CarrinhoDeComprasEncomendaBean carrinhoDeComprasEncomendaBean;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	private boolean incluirFrete;
	private boolean liberaFinalizaCompra = false;
	private int tipoFrete;
	private List<CarrinhoCompras> listaCarrinhoDeCompras = new ArrayList<CarrinhoCompras>();
		
	public void adicionaProdutoNoCarrinho(CarrinhoCompras carrinhoCompras){
			
		if(carrinhoCompras.getQtdProduto() >= 1){			
			boolean produtoExste = false;
		
			int estoque = produtoRepository.produtoQuantidadeEstoque2(carrinhoCompras.getIdProduto()).getProdutoEstoque().get(0).getNrEstoqueAtual();
			int reserva = produtoRepository.produtoQuantidadeEstoque2(carrinhoCompras.getIdProduto()).getProdutoEstoque().get(0).getNrReserva();
			int troca = produtoRepository.produtoQuantidadeEstoque2(carrinhoCompras.getIdProduto()).getProdutoEstoque().get(0).getNrTroca();			
			int total = estoque - (reserva + troca);
			
			//verificar se não ultrapassa quantidade de estoque
			if( total >= (carrinhoCompras.getQtdProduto() * carrinhoCompras.getQtdPorCaixa()) ){			

				for(int cont = 0; cont < listCarrinhoCompras().size(); cont++){				
					if(carrinhoCompras.getIdProdutoCampanha() == listaCarrinhoDeCompras.get(cont).getIdProdutoCampanha()){
						
						produtoExste = true;					
						getListaCarrinhoDeCompras().get(cont).setQtdProduto(carrinhoCompras.getQtdProduto());					
						getListaCarrinhoDeCompras().get(cont).setSubTotal( carrinhoCompras.getQtdProduto() * (carrinhoCompras.getValorUnitario() * carrinhoCompras.getQtdPorCaixa()) );
						
						FacesUtil.addInfoMessage("Produto alterado com sucesso.");					
					}
				}
				
				if(produtoExste == false && total >= (carrinhoCompras.getQtdProduto() * carrinhoCompras.getQtdPorCaixa())){					
					this.listaCarrinhoDeCompras.add(carrinhoCompras);
					FacesUtil.addInfoMessage("Produto adicionado com sucesso.");
				}
				
			}else{				
				carrinhoCompras.setQtdProduto(1);
				
				for(int cont = 0; cont < listCarrinhoCompras().size(); cont++){				
					if(carrinhoCompras.getIdProdutoCampanha() == listaCarrinhoDeCompras.get(cont).getIdProdutoCampanha()){
																	
						getListaCarrinhoDeCompras().get(cont).setQtdProduto(carrinhoCompras.getQtdProduto());					
						getListaCarrinhoDeCompras().get(cont).setSubTotal( carrinhoCompras.getQtdProduto() * (carrinhoCompras.getValorUnitario() * carrinhoCompras.getQtdPorCaixa()) );
					
					}
				}				
				FacesUtil.addErrorMessage("Quantidade estoque insuficiente.");
			}	
		}else{
			FacesUtil.addErrorMessage("Quantidade produto invalida.");
		}		
	}
		
	public String botaoFinalizarCompraConvecional(){
		String link = "carrinhoDeCompras.xhtml";
		if(listaCarrinhoDeCompras.size() >= 1 && totalConvencionalProdutoCarrinho() >= 1000){			
			liberaFinalizaCompra = true;
			carrinhodeComprasCompreGanheBean.setLiberaFinalizaCompra(false);
			carrinhoDeComprasEncomendaBean.setLiberaFinalizaCompra(false);
			
			link = "/finalizacaoDeCompras/frete.xhtml";
		}else if(listaCarrinhoDeCompras.size() >= 1 && totalConvencionalProdutoCarrinho() <= 999.99){
			FacesUtil.addErrorMessage("Pedido minimo de R$ 1000,00 reais.");
			
		}else{			
			FacesUtil.addErrorMessage("Adicione produto no carrinho para finalizar a compra.");
		}
		return link;
	}
	
	public double totalConvencionalProdutoCarrinho(){
		double totalCarrinho = 0;
		for(int cont = 0;cont < listCarrinhoCompras().size(); cont++){
			totalCarrinho = totalCarrinho + listCarrinhoCompras().get(cont).getSubTotal();
		}
		return totalCarrinho;
	}
	
	public void removeTodosProdutoCarrinhoCompras(){
		listaCarrinhoDeCompras = new ArrayList<CarrinhoCompras>();
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

	public int getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(int tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public boolean isLiberaFinalizaCompra() {
		return liberaFinalizaCompra;
	}

	public void setLiberaFinalizaCompra(boolean liberaFinalizaCompra) {
		this.liberaFinalizaCompra = liberaFinalizaCompra;
	}

	public boolean isIncluirFrete() {
		return incluirFrete;
	}

	public void setIncluirFrete(boolean incluirFrete) {
		this.incluirFrete = incluirFrete;
	}
}
