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
public class CarrinhodeComprasCompreGanheBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CarrinhoDeComprasBean carrinhoDeComprasBean;
	
	@Inject
	private CarrinhoDeComprasEncomendaBean carrinhoDeComprasEncomendaBean;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	private int tipoFrete;
	private boolean incluirFrete;
	private boolean liberaFinalizaCompra = false;
	private List<CarrinhoCompras> listaCarrinhoDeCompras = new ArrayList<CarrinhoCompras>();
	
	public void adicionaProdutoNoCarrinhoCompreGanhe(CarrinhoCompras carrinhoCompras){
		
		if(carrinhoCompras.getQtdProduto() >= 1 && loginBean.getClienteCompreGanhe().getSnStatus() == true){
			boolean produtoExste = false;
									
			//calcula pontos do produto que o cliente quer comprar
			double qtdPontosProduto = (carrinhoCompras.getQtdPorCaixa() * carrinhoCompras.getValorUnitario()) * carrinhoCompras.getQtdProduto();
			//verificar se não ultrapassa pontos do cliente
			if(loginBean.getClienteCompreGanhe().getNrQuantidadePontos() > 0 && qtdPontosProduto <= loginBean.getClienteCompreGanhe().getNrQuantidadePontos()){
								
				int estoque = produtoRepository.produtoQuantidadeEstoque(carrinhoCompras.getIdProduto()).getProdutoEstoque().get(0).getNrEstoqueAtual();
				int reserva = produtoRepository.produtoQuantidadeEstoque(carrinhoCompras.getIdProduto()).getProdutoEstoque().get(0).getNrReserva();
				int troca = produtoRepository.produtoQuantidadeEstoque(carrinhoCompras.getIdProduto()).getProdutoEstoque().get(0).getNrTroca();			
				int total = estoque - (reserva + troca);
				
				//verificar se não ultrapassa quantidade de estoque
				if( total >= (carrinhoCompras.getQtdProduto() * carrinhoCompras.getQtdPorCaixa()) ){
					for(int cont = 0;cont < listCarrinhoCompras().size(); cont++){				
						if(carrinhoCompras.getIdProduto() == listaCarrinhoDeCompras.get(cont).getIdProduto()){
							
							produtoExste = true;					
							getListaCarrinhoDeCompras().get(cont).setQtdProduto(carrinhoCompras.getQtdProduto());					
							getListaCarrinhoDeCompras().get(cont).setSubTotal( carrinhoCompras.getQtdProduto() * (carrinhoCompras.getValorUnitario() * carrinhoCompras.getQtdPorCaixa()) );
							
							FacesUtil.addInfoMessage("Produto alterado com sucesso.");					
						}
					}
					
					if(produtoExste == false){
						listaCarrinhoDeCompras.add(carrinhoCompras);
						carrinhoDeComprasBean.setLiberaFinalizaCompra(false);
						carrinhoDeComprasEncomendaBean.setLiberaFinalizaCompra(false);
						
						FacesUtil.addInfoMessage("Produto adicionado com sucesso.");
					}
				}else{
					carrinhoCompras.setQtdProduto(1);
					
					for(int cont = 0; cont < listCarrinhoCompras().size(); cont++){				
						if(carrinhoCompras.getIdProduto() == listaCarrinhoDeCompras.get(cont).getIdProduto()){
																		
							getListaCarrinhoDeCompras().get(cont).setQtdProduto(carrinhoCompras.getQtdProduto());					
							getListaCarrinhoDeCompras().get(cont).setSubTotal( carrinhoCompras.getQtdProduto() * (carrinhoCompras.getValorUnitario() * carrinhoCompras.getQtdPorCaixa()) );
						
						}
					}					
					FacesUtil.addErrorMessage("Quantidade estoque insuficiente.");
				}	
			}else{
				FacesUtil.addErrorMessage("Quantidade pontos invalido.");
			}	
		}else{
			FacesUtil.addErrorMessage("Quantidade produto invalida.");
		}		
	}
	
	public void removeTodosProdutoCarrinhoCompras(){
		listaCarrinhoDeCompras = new ArrayList<CarrinhoCompras>();
	}
	
	public String botaoFinalizarCompraCompreGanhe(){
		if(listaCarrinhoDeCompras.size() >= 1){			
			liberaFinalizaCompra = true;
			return "/finalizacaoDeCompras/freteCompreGanhe.xhtml";
		}		
		FacesUtil.addErrorMessage("Adicione produto no carrinho para finalizar a compra.");
		return "";
	}
	
	public double totalCompreGanheProdutoCarrinho(){
		double totalCarrinho = 0;
		for(int cont = 0;cont < listCarrinhoCompras().size(); cont++){
			totalCarrinho = totalCarrinho + listCarrinhoCompras().get(cont).getSubTotal();
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
