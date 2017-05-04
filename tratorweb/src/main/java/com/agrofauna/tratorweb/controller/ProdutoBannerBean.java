package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.repository.ProdutoBannerRepository;
import com.agrofauna.tratorweb.service.CalcularPrecoProduto;

@Named
@ViewScoped
public class ProdutoBannerBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoBannerRepository produtoBannerRepository;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CalcularPrecoProduto calcularPrecoProduto;
	
	public List<CampanhaProduto> listProdutoBanner1(){		
		//return calcalarPrecoProduto.calculaPrecoProdutoList(produtoBannerRepository.buscarTodosBannerProdutoPosicao1(loginBean.getCliente()));
		return calcularPrecoProduto.calculaPrecoProdutoList( produtoBannerRepository.todosBannerProdutoPosicao1(loginBean.getCliente()) );
	}

	public List<CampanhaProduto> listProdutoBanner2(){		
		//return  calcalarPrecoProduto.calculaPrecoProdutoList(produtoBannerRepository.buscarTodosBannerProdutoPosicao2(loginBean.getCliente()));
		return calcularPrecoProduto.calculaPrecoProdutoList( produtoBannerRepository.todosBannerProdutoPosicao2(loginBean.getCliente()) );
	}
	
	public List<CampanhaProduto> listProdutoBanner3(){		
		//return  calcalarPrecoProduto.calculaPrecoProdutoList(produtoBannerRepository.buscarTodosBannerProdutoPosicao3(loginBean.getCliente()));
		return calcularPrecoProduto.calculaPrecoProdutoList( produtoBannerRepository.todosBannerProdutoPosicao3(loginBean.getCliente()) );
	}
}
