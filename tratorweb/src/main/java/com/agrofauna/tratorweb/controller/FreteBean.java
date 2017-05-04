package com.agrofauna.tratorweb.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class FreteBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CarrinhoDeComprasBean carrinhoDeComprasBean; 
	
	@Inject
	private CarrinhodeComprasCompreGanheBean carrinhodeComprasCompreGanheBean;
	
	@Inject
	private CarrinhoDeComprasEncomendaBean carrinhoDeComprasEncomendaBean;
	
	private int tipoFrete = 1;
	private boolean incluirValorFreteProduto = false;
	
	public void inicializar(){
		this.tipoFrete = 1;
		this.incluirValorFreteProduto = false;
	}
	
	public String  verificaTipoFrete(){
		//vai pra tela de estimativa de frete
		String link = "/finalizacaoDeCompras/freteEstimativaComTrans.xhtml";
						
		if(tipoFrete == 1 || tipoFrete == 2 || tipoFrete == 3){
			//finaliza pedido		
			link = "/finalizacaoDeCompras/formaDePagamento.xhtml";		
		}		
				
		if(carrinhoDeComprasBean.isLiberaFinalizaCompra() == true){
			carrinhoDeComprasBean.setTipoFrete(tipoFrete);
			carrinhoDeComprasBean.setIncluirFrete(incluirValorFreteProduto);
						
		}else if(carrinhodeComprasCompreGanheBean.isLiberaFinalizaCompra() == true){
			carrinhodeComprasCompreGanheBean.setTipoFrete(tipoFrete);
			carrinhodeComprasCompreGanheBean.setIncluirFrete(incluirValorFreteProduto);
			
		}else if(carrinhoDeComprasEncomendaBean.isLiberaFinalizaCompra() == true){
			carrinhoDeComprasEncomendaBean.setTipoFrete(tipoFrete);
			carrinhoDeComprasEncomendaBean.setIncluirFrete(incluirValorFreteProduto);
			
		}else{
			link = "/carrinhoDeCompras/carrinhoDeCompras.xhtml";			
		}
						
		return link;
	}
	
	public int getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(int tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public boolean isIncluirValorFreteProduto() {
		return incluirValorFreteProduto;
	}

	public void setIncluirValorFreteProduto(boolean incluirValorFreteProduto) {
		this.incluirValorFreteProduto = incluirValorFreteProduto;
	}
}
