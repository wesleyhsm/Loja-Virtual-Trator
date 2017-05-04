package com.agrofauna.tratorweb.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.agrofauna.tratorweb.controller.LoginBean;
import com.agrofauna.tratorweb.filtro.ProdutoDetalhado;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.IcmsEstado;
import com.agrofauna.tratorweb.model.ProdutoTributoEspecial;
import com.agrofauna.tratorweb.repository.IcmsEstadoRepository;
import com.agrofauna.tratorweb.repository.ProdutoTributoEspecialRepository;

public class CalcularPrecoProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private ProdutoTributoEspecialRepository produtoTributoEspecialRepository;
	
	@Inject
	private IcmsEstadoRepository icmsEstadoRepository;
	
	public List<CampanhaProduto> calculaPrecoProdutoList(List<CampanhaProduto> listCampanhaProduto){
		
		IcmsEstado icmsEstado = icmsEstadoRepository.buscarIcmsEstado(loginBean.getEstadoOrigem().getIdEstado(), loginBean.getEstado().getIdEstado());
				
		for(int cont = 0; cont < listCampanhaProduto.size(); cont++){			
			
			//if(listCampanhaProduto.get(cont).getCampanha().getNrPrioridade() != 10){
				//sempre que o produto for igual a (S) ele é EPI pega a alicota do estado de destinho a acrecenta no produto
				if(listCampanhaProduto.get(cont).getProduto().getSnEpi().equalsIgnoreCase("S")){
					//pega o preço do EPI e calcula a alicota de destino
					//double imposto = (listCampanhaProduto.get(cont).getVlPrecoCampanha() * loginBean.getEstado().getNrIcms()) / 100;
					//pega o valor da alicota de destino e soma ao preço do produto
					double total = listCampanhaProduto.get(cont).getVlPrecoCampanha() / pegaValorIcms(loginBean.getEstado().getNrIcms());
					listCampanhaProduto.get(cont).setVlPrecoCampanha( calculaPercentualCampanha(total, listCampanhaProduto.get(cont).getCampanha().getNrPercentualValor()) );
				
				//se o cliente não for suframa				
				}else if(loginBean.getSuframa().getSnStatus() == 2 || loginBean.getSuframa().getSnIsencao() == 2){
										
					//consultar se o produto é especial
					ProdutoTributoEspecial produtoTributoEspecial = produtoTributoEspecialRepository.buscarProdutoTributoEspecial(listCampanhaProduto.get(cont).getProduto().getIdProduto());
									
					//verifica se o produto é especial e fora de SP
					if(produtoTributoEspecial != null && loginBean.getEstado().getIdEstado() != 26){
						//double imposto = (listCampanhaProduto.get(cont).getVlPrecoCampanha() * produtoTributoEspecial.getNrAlicota()) / produtoTributoEspecial.getNrBaseCalculo();
						double total = listCampanhaProduto.get(cont).getVlPrecoCampanha() / pegaValorIcms(produtoTributoEspecial.getNrAlicota());
						listCampanhaProduto.get(cont).setVlPrecoCampanha( calculaPercentualCampanha(total, listCampanhaProduto.get(cont).getCampanha().getNrPercentualValor()) );
											
					//se produto não for especial e fora de SP	
					}else if(produtoTributoEspecial == null && loginBean.getEstado().getIdEstado() != 26){
						//double imposto = listCampanhaProduto.get(cont).getVlPrecoCampanha() * (produtoTributoEspecial.getNrAlicota() / produtoTributoEspecial.getNrBaseCalculo());
						//listCampanhaProduto.get(cont).setVlPrecoCampanha(imposto + listCampanhaProduto.get(cont).getVlPrecoCampanha());										
						//double baseImposto = (listCampanhaProduto.get(cont).getVlPrecoCampanha() * 60) / 100;
						//double imposto = ((listCampanhaProduto.get(cont).getVlPrecoCampanha() - baseImposto) * icmsEstado.getNrAlicota()) / 100;
						double total = listCampanhaProduto.get(cont).getVlPrecoCampanha() / pegaValorIcms(icmsEstado.getNrAlicota());
						listCampanhaProduto.get(cont).setVlPrecoCampanha( calculaPercentualCampanha(total, listCampanhaProduto.get(cont).getCampanha().getNrPercentualValor()) );														
					}				
				}
			//}	
		}		
		return listCampanhaProduto;
	}
	
	public ProdutoDetalhado calculaPrecoProdutoNormal(ProdutoDetalhado produtoDetalhado){
		
		//if(produtoDetalhado.getNr_prioridade() != 10){			
			//System.out.println("WESLEY ENTRO NO CALCULO");
			IcmsEstado icmsEstado = icmsEstadoRepository.buscarIcmsEstado(loginBean.getEstadoOrigem().getIdEstado(), loginBean.getEstado().getIdEstado());
			
			//System.out.println("WESLEY ICMS: " +icmsEstado.getIdIcmsEstado() );
			
			//sempre que o produto for igual a (S) ele é EPI pega a alicota do estado de destinho a acrecenta no produto
			if( "S".equals(produtoDetalhado.getSn_epi()) ){
				//pega o preço do EPI e calcula a alicota de destino
				//double imposto = (produtoDetalhado.getVl_preco_campanha() * loginBean.getEstado().getNrIcms()) / 100;
				//pega o valor da alicota de destino e soma ao preço do produto
				double total = produtoDetalhado.getVl_preco_campanha() / pegaValorIcms(loginBean.getEstado().getNrIcms());
				produtoDetalhado.setVl_preco_campanha( calculaPercentualCampanha(total, produtoDetalhado.getNr_percentual_valor()) );
						
			//se o cliente não for suframa (0)	
			}else if(loginBean.getSuframa().getSnRegular() == 2 || loginBean.getSuframa().getSnIsencao() == 2){						
				//System.out.println("WESLEY VERIFICA SE PRODUTO É ESPECIAL");
				//consultar se o produto é especial
				ProdutoTributoEspecial produtoTributoEspecial = produtoTributoEspecialRepository.buscarProdutoTributoEspecial(produtoDetalhado.getId_produto());
				
				//verifica se o produto é especial								
				if(produtoTributoEspecial != null && loginBean.getEstado().getIdEstado() != 26){
					//System.out.println("WESLEY ENTRO PRODUTO ESPECIAL");				
					//double imposto = (produtoDetalhado.getVl_preco_campanha() * produtoTributoEspecial.getNrAlicota()) / produtoTributoEspecial.getNrBaseCalculo();
					double total = produtoDetalhado.getVl_preco_campanha() / pegaValorIcms(produtoTributoEspecial.getNrAlicota());
					produtoDetalhado.setVl_preco_campanha( calculaPercentualCampanha(total, produtoDetalhado.getNr_percentual_valor()) );
					
				//se produto for especial e fora de SP	
				}else if(produtoTributoEspecial == null && loginBean.getEstado().getIdEstado() != 26){
					//System.out.println("WESLEY NÃO E PRODUTO ESPECIAL");
					//double baseImposto = (produtoDetalhado.getVl_preco_campanha() * 60) / 100;				
					//double imposto = ((produtoDetalhado.getVl_preco_campanha() - baseImposto) * icmsEstado.getNrAlicota()) / 100;
					//double imposto =(produtoDetalhado.getVl_preco_campanha() / calculoIcms);
					double total = produtoDetalhado.getVl_preco_campanha() / pegaValorIcms(icmsEstado.getNrAlicota());
					produtoDetalhado.setVl_preco_campanha( calculaPercentualCampanha(total, produtoDetalhado.getNr_percentual_valor()) );							
				}
			}
		//}
		return produtoDetalhado;
	}
	
	public double calculaPercentualCampanha(double preco, double percentual){
		if(percentual > 0){
			return (preco + ( (preco * percentual) / 100));
		}		
		return preco;
	}
	
	public double pegaValorIcms(double alicota){
		double resultado = 0;
		
		if(alicota == 7.0){
			resultado = 0.972;
			
		}else if(alicota == 12.0){
			resultado = 0.954;
			
		}else{
			resultado = (100 - alicota) / 100;
		}
		
		return resultado;
	}
}
