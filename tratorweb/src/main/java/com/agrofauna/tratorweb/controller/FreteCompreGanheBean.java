package com.agrofauna.tratorweb.controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class FreteCompreGanheBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CarrinhoDeComprasBean carrinhoDeComprasBean; 
	
	@Inject
	private CarrinhodeComprasCompreGanheBean carrinhodeComprasCompreGanheBean;
	
	@Inject
	private CarrinhoDeComprasEncomendaBean carrinhoDeComprasEncomendaBean;
	
	private int tipoFrete = 1;
	private boolean incluirValorFreteProduto = false;
	
	public String  verificaTipoFrete(){			
		if(carrinhoDeComprasBean.isLiberaFinalizaCompra() == true){
			carrinhoDeComprasBean.setTipoFrete(tipoFrete);
			carrinhoDeComprasBean.setIncluirFrete(incluirValorFreteProduto);
						
		}else if(carrinhodeComprasCompreGanheBean.isLiberaFinalizaCompra() == true){
			carrinhodeComprasCompreGanheBean.setTipoFrete(tipoFrete);
			carrinhodeComprasCompreGanheBean.setIncluirFrete(incluirValorFreteProduto);
			
		}else if(carrinhoDeComprasEncomendaBean.isLiberaFinalizaCompra() == true){
			carrinhoDeComprasEncomendaBean.setTipoFrete(tipoFrete);
			carrinhoDeComprasEncomendaBean.setIncluirFrete(incluirValorFreteProduto);			
		}
		
		return "/finalizacaoDeCompras/formaDePagamento2.xhtml";
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
