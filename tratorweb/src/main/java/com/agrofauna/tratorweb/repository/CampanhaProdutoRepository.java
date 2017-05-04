package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.agrofauna.tratorweb.filtro.FiltroCampanhaProduto;
import com.agrofauna.tratorweb.filtro.ProdutoDetalhado;
import com.agrofauna.tratorweb.model.Campanha;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.ConexaoBanco;
import com.agrofauna.tratorweb.model.Produto;

public class CampanhaProdutoRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");
			
	//lista produto detalhado normal	                                    
	public ProdutoDetalhado listaCampanhaProdutoDetalhado(CampanhaProduto campanhaProduto, Cliente cliente){
		ProdutoDetalhado produtoDetalhado = new ProdutoDetalhado();
		try{
			String queryCampanhaProduto = "SELECT "+
												"c.id_campanha, "+
												"c.nr_percentual_valor, "+
												"c.nr_prioridade, "+
												"p.id_produto, "+
												"p.nm_produto, "+
												"p.sn_epi, "+
											    "p.desc_concentracao_quimica, "+												 
												"cp.id_produto_campanha, "+ 
												"cp.vl_preco_campanha, "+
												"f.id_fabricante, "+
											    "f.nm_razao_social, "+
											    "gq.id_grupo_quimico, "+
											    "gq.nm_grupo_quimico, "+
											    "pa.id_principio_ativo, "+
											    "pa.nm_principio_ativo, "+
											    "ar.qtd_especie_medida, "+
											    "ar.nr_peso_bruto "+
											"FROM "+  
												"db_agro_matriz.campanha c "+ 
												"inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+ 
												"INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+ 
												"inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+ 
												"inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
											    "inner join db_agro_matriz.fabricante f on f.id_fabricante=p.id_fabricante "+
											    "inner join db_agro_matriz.produto_dados_tecnicos pdt on pdt.id_produto=p.id_produto "+
											    "inner join db_agro_matriz.grupo_quimico gq on gq.id_grupo_quimico=pdt.id_grupo_quimico "+
											    "inner join db_agro_matriz.principio_ativo pa on pa.id_principio_ativo=pdt.id_principio_ativo "+
											    "inner join db_agro_matriz.produto_armazenamento ar on ar.id_produto=p.id_produto "+
											"WHERE "+ 
												"date(c.dt_final) >= date('"+dt.format(new Date())+"') and "+ 
												"cc.id_pessoa="+cliente.getIdPessoa()+" and "+ 
												"c.sn_encomenda=0 and "+
												"p.sn_disponivel_venda='S' and "+
												"pe.nr_estoque_atual>0 and "+
												"cp.vl_preco_campanha>0 and "+
												"cp.id_produto_campanha="+campanhaProduto.getIdProdutoCampanha()+" "+
											"ORDER BY "+ 
												"p.nm_produto"; 

			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) {			
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();
				while(rsCampanhaProduto.next()){
										
					produtoDetalhado.setDesc_concentracao_quimica(rsCampanhaProduto.getString("desc_concentracao_quimica"));
					produtoDetalhado.setId_fabricante(rsCampanhaProduto.getLong("id_fabricante"));
					produtoDetalhado.setId_grupo_quimico(rsCampanhaProduto.getLong("id_grupo_quimico"));
					produtoDetalhado.setId_principio_ativo(rsCampanhaProduto.getLong("id_principio_ativo"));
					produtoDetalhado.setId_produto(rsCampanhaProduto.getLong("id_produto"));
					produtoDetalhado.setId_produto_campanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					produtoDetalhado.setNm_grupo_quimico(rsCampanhaProduto.getString("nm_grupo_quimico"));
					produtoDetalhado.setNm_principio_ativo(rsCampanhaProduto.getString("nm_principio_ativo"));
					produtoDetalhado.setNm_produto(rsCampanhaProduto.getString("nm_produto"));
					produtoDetalhado.setNm_razao_social(rsCampanhaProduto.getString("nm_razao_social"));
					produtoDetalhado.setNr_peso_bruto(rsCampanhaProduto.getDouble("nr_peso_bruto"));
					produtoDetalhado.setQtd_especie_medida(rsCampanhaProduto.getInt("qtd_especie_medida"));
					produtoDetalhado.setVl_preco_campanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					produtoDetalhado.setId_campanha(rsCampanhaProduto.getLong("id_campanha"));
					produtoDetalhado.setSn_epi(rsCampanhaProduto.getString("sn_epi"));
					produtoDetalhado.setNr_percentual_valor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					produtoDetalhado.setNr_prioridade(rsCampanhaProduto.getInt("nr_prioridade"));
				}
			rsCampanhaProduto.close();
			stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){
			System.out.println("WESLEY VERIFICA SE PRODUTO É ESPECIAL");
			sql.printStackTrace();
		}		
		return produtoDetalhado;		
	}
	
	//lista produto detalhado encomenda
	public ProdutoDetalhado listaCampanhaProdutoEncomendaDetalhado(CampanhaProduto campanhaProduto, Cliente cliente){
		ProdutoDetalhado produtoDetalhado = new ProdutoDetalhado();
		try{
			String queryCampanhaProduto = "SELECT "+
												"c.id_campanha, "+
												"c.nr_percentual_valor, "+
												"c.nr_prioridade, "+
												"p.id_produto, "+
												"p.nm_produto, "+
												"p.sn_epi, "+
											    "p.desc_concentracao_quimica, "+												 
												"cp.id_produto_campanha, "+												
												"cp.nr_classifica_preco_encomenda, "+
												"cp.vl_preco_campanha, "+
												"f.id_fabricante, "+
											    "f.nm_razao_social, "+
											    "gq.id_grupo_quimico, "+
											    "gq.nm_grupo_quimico, "+
											    "pa.id_principio_ativo, "+
											    "pa.nm_principio_ativo, "+
											    "ar.qtd_especie_medida, "+
											    "ar.nr_peso_bruto "+
											"FROM "+  
												"db_agro_matriz.campanha c "+ 
												"inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+ 
												"INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+ 
												"inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+ 
												"inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
											    "inner join db_agro_matriz.fabricante f on f.id_fabricante=p.id_fabricante "+
											    "inner join db_agro_matriz.produto_dados_tecnicos pdt on pdt.id_produto=p.id_produto "+
											    "inner join db_agro_matriz.grupo_quimico gq on gq.id_grupo_quimico=pdt.id_grupo_quimico "+
											    "inner join db_agro_matriz.principio_ativo pa on pa.id_principio_ativo=pdt.id_principio_ativo "+
											    "inner join db_agro_matriz.produto_armazenamento ar on ar.id_produto=p.id_produto "+
											"WHERE "+ 
												"date(c.dt_final) >= date('"+dt.format(new Date())+"') and "+ 
												"cc.id_pessoa="+cliente.getIdPessoa()+" and "+ 
												"c.sn_encomenda=1 and "+												
												"p.sn_disponivel_venda='S' and "+
												"pe.nr_estoque_atual<=0 and "+
												"cp.vl_preco_campanha>0 and "+
												"cp.id_produto_campanha="+campanhaProduto.getIdProdutoCampanha()+" "+
											"ORDER BY "+ 
												"p.nm_produto"; 

			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) {			
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();
				while(rsCampanhaProduto.next()){
										
					produtoDetalhado.setDesc_concentracao_quimica(rsCampanhaProduto.getString("desc_concentracao_quimica"));
					produtoDetalhado.setId_fabricante(rsCampanhaProduto.getLong("id_fabricante"));
					produtoDetalhado.setId_grupo_quimico(rsCampanhaProduto.getLong("id_grupo_quimico"));
					produtoDetalhado.setId_principio_ativo(rsCampanhaProduto.getLong("id_principio_ativo"));
					produtoDetalhado.setId_produto(rsCampanhaProduto.getLong("id_produto"));
					produtoDetalhado.setId_produto_campanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					produtoDetalhado.setNm_grupo_quimico(rsCampanhaProduto.getString("nm_grupo_quimico"));
					produtoDetalhado.setNm_principio_ativo(rsCampanhaProduto.getString("nm_principio_ativo"));
					produtoDetalhado.setNm_produto(rsCampanhaProduto.getString("nm_produto"));
					produtoDetalhado.setNm_razao_social(rsCampanhaProduto.getString("nm_razao_social"));
					produtoDetalhado.setNr_peso_bruto(rsCampanhaProduto.getDouble("nr_peso_bruto"));
					produtoDetalhado.setQtd_especie_medida(rsCampanhaProduto.getInt("qtd_especie_medida"));
					produtoDetalhado.setVl_preco_campanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					produtoDetalhado.setId_campanha(rsCampanhaProduto.getLong("id_campanha"));	
					produtoDetalhado.setNr_classifica_preco_encomenda(rsCampanhaProduto.getDouble("nr_classifica_preco_encomenda"));
					produtoDetalhado.setSn_epi(rsCampanhaProduto.getString("sn_epi"));
					produtoDetalhado.setNr_percentual_valor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					produtoDetalhado.setNr_prioridade(rsCampanhaProduto.getInt("nr_prioridade"));
				}
			rsCampanhaProduto.close();
			stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return produtoDetalhado;		
	}
				
	public List<CampanhaProduto> produtoCampanhaMesmoPricipioAtivoManual(FiltroCampanhaProduto filtroCampanhaProduto){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{			
						
			String queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_dados_tecnicos pdt on pdt.id_produto=p.id_produto "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "pdt.id_principio_ativo="+filtroCampanhaProduto.getPrincipioAtivo().getIdPrincipioAtivo()+" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+										    
										    "cp.vl_preco_campanha>0 "+										    
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto "+
										        "inner join db_agro_matriz.produto_dados_tecnicos pdt2 on pdt2.id_produto=p2.id_produto "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+												
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"pdt2.id_principio_ativo="+filtroCampanhaProduto.getPrincipioAtivo().getIdPrincipioAtivo()+" and "+
												"c2.sn_encomenda=0 and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+												
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY p.nm_produto";			
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) {			
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
					campanha.setNmLinkSelo(rsCampanhaProduto.getString("nm_link_selo"));
					campanha.setSnPromocao(rsCampanhaProduto.getBoolean("sn_promocao"));
					campanha.setNrPercentualValor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setSnEpi(rsCampanhaProduto.getString("sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					campanhaProduto.setSnUltimasUnidades(rsCampanhaProduto.getInt("sn_ultimas_unidades"));
					campanhaProduto.setSnPagamentoAntecipado(rsCampanhaProduto.getInt("sn_pagamento_antecipado"));
					campanhaProduto.setCampanha(campanha);
					campanhaProduto.setProduto(produto);
					
					campanhaProdutos.add(campanhaProduto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return campanhaProdutos;
	}
	
	public List<CampanhaProduto> produtoCampanhaMesmoPricipioAtivoEncomendaManual(FiltroCampanhaProduto filtroCampanhaProduto){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{			
			String queryCampanhaProduto = "SELECT "+ 	
				    "cp.id_produto, "+
				    "p.nm_produto, "+
				    "c.id_campanha, "+
				    "c.nr_percentual_valor, "+
				    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
				    "c.sn_promocao, "+
				    "cp.id_produto_campanha, "+
				    "cp.vl_preco_campanha, "+
				    "cp.sn_ultimas_unidades, "+ 
				    "cp.sn_pagamento_antecipado, "+
				    "cp.nr_classifica_preco_encomenda, "+
				    "c.nm_campanha, "+
				    "c.dt_final, "+
				    "c.nr_prioridade "+
				"FROM  "+
					"db_agro_matriz.campanha c "+
				    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
				    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
				    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
				    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
				    "inner join db_agro_matriz.produto_dados_tecnicos pdt on pdt.id_produto=p.id_produto "+
				"WHERE "+
					"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
				    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
				    "pdt.id_principio_ativo="+filtroCampanhaProduto.getPrincipioAtivo().getIdPrincipioAtivo()+" and "+
				    "c.sn_encomenda=1 and "+
				    "c.nr_prioridade=4 and "+
				    "p.sn_disponivel_venda='S' and "+
				    "pe.nr_estoque_atual<=0 and "+										    
				    "cp.vl_preco_campanha>0 "+
				"ORDER BY p.nm_produto";	
						
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
					campanha.setNmLinkSelo(rsCampanhaProduto.getString("nm_link_selo"));
					campanha.setSnPromocao(rsCampanhaProduto.getBoolean("sn_promocao"));
					campanha.setNrPercentualValor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					campanhaProduto.setSnUltimasUnidades(rsCampanhaProduto.getInt("sn_ultimas_unidades"));
					campanhaProduto.setSnPagamentoAntecipado(rsCampanhaProduto.getInt("sn_pagamento_antecipado"));
					campanhaProduto.setNrClassificaPrecoEncomenda(rsCampanhaProduto.getDouble("nr_classifica_preco_encomenda"));
					campanhaProduto.setCampanha(campanha);
					campanhaProduto.setProduto(produto);
					
					campanhaProdutos.add(campanhaProduto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return campanhaProdutos;
	}
	
	public List<CampanhaProduto> produtoCampanhaPromocaoManual(FiltroCampanhaProduto filtroCampanhaProduto){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{			
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}				
			
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto  AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial)<= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+
										    "c.sn_promocao=1 and "+
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto  AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"c2.sn_promocao=1 and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;	
			}else{
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+
										    "c.sn_promocao=1 "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"c2.sn_promocao=1 and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;
			}
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
					campanha.setNmLinkSelo(rsCampanhaProduto.getString("nm_link_selo"));
					campanha.setSnPromocao(rsCampanhaProduto.getBoolean("sn_promocao"));
					campanha.setNrPercentualValor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setSnEpi(rsCampanhaProduto.getString("sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					campanhaProduto.setSnUltimasUnidades(rsCampanhaProduto.getInt("sn_ultimas_unidades"));
					campanhaProduto.setSnPagamentoAntecipado(rsCampanhaProduto.getInt("sn_pagamento_antecipado"));
					campanhaProduto.setCampanha(campanha);
					campanhaProduto.setProduto(produto);
					
					campanhaProdutos.add(campanhaProduto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return campanhaProdutos;
	}
	
	public int produtoCampanhaPromocaoManualContador(FiltroCampanhaProduto filtroCampanhaProduto, int first, int pageSize){
		int contador = 0;
		try{			
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}				
			
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
											"count(*) as total "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto  AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+
										    "c.sn_promocao=1 and "+
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto  AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"c2.sn_promocao=1 and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;	
			}else{
				queryCampanhaProduto = "SELECT "+ 	
										    "count(*) as total "+										   
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+
										    "c.sn_promocao=1 "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"c2.sn_promocao=1 and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;
			}
			
			System.out.println("wesley consulta lazy: " + queryCampanhaProduto);
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					contador = contador + rsCampanhaProduto.getInt("total");
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return contador;
	}
	
	public List<CampanhaProduto> produtoCampanhaPromocaoManualLazy(FiltroCampanhaProduto filtroCampanhaProduto, int first, int pageSize){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{			
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}				
			
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+										    
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto  AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+
										    "c.sn_promocao=1 and "+
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto  AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"c2.sn_promocao=1 and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao + " LIMIT "+first+","+ pageSize;	
			}else{
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+
										    "c.sn_promocao=1 "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"c2.sn_promocao=1 and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao + " LIMIT "+first+","+ pageSize;
			}
			System.out.println("wesley consulta lazy: " + queryCampanhaProduto);
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
					campanha.setNmLinkSelo(rsCampanhaProduto.getString("nm_link_selo"));
					campanha.setSnPromocao(rsCampanhaProduto.getBoolean("sn_promocao"));
					campanha.setNrPercentualValor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setSnEpi(rsCampanhaProduto.getString("sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					campanhaProduto.setSnUltimasUnidades(rsCampanhaProduto.getInt("sn_ultimas_unidades"));
					campanhaProduto.setSnPagamentoAntecipado(rsCampanhaProduto.getInt("sn_pagamento_antecipado"));
					campanhaProduto.setCampanha(campanha);
					campanhaProduto.setProduto(produto);
					
					campanhaProdutos.add(campanhaProduto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return campanhaProdutos;
	}
	
	//normal contador
	public int produtoCampanhaNormalManualContador(FiltroCampanhaProduto filtroCampanhaProduto){
		int contador = 0;
		try{		
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}
						
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
										    "count(*) as total "+										   
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+										    
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+										    
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+												
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;				
			}else{				
				queryCampanhaProduto = "SELECT "+ 	
											"count(*) as total "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+										    
										    queryFiltro +
										    "cp.vl_preco_campanha>0 "+										    
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual > 0 and "+												
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;
			}
			
			System.out.println("wesley: " + queryCampanhaProduto);
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					contador = contador + rsCampanhaProduto.getInt("total");					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return contador;
	}
	// normal
	public List<CampanhaProduto> produtoCampanhaNormalManual(FiltroCampanhaProduto filtroCampanhaProduto){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{		
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}
						
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+										    
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+										    
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+												
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;				
			}else{				
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+										    
										    queryFiltro +
										    "cp.vl_preco_campanha>0 "+										    
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual > 0 and "+												
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;
			}
			
			System.out.println("wesley: " + queryCampanhaProduto);
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
					campanha.setNmLinkSelo(rsCampanhaProduto.getString("nm_link_selo"));
					campanha.setSnPromocao(rsCampanhaProduto.getBoolean("sn_promocao"));
					campanha.setNrPercentualValor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setSnEpi(rsCampanhaProduto.getString("sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					campanhaProduto.setSnUltimasUnidades(rsCampanhaProduto.getInt("sn_ultimas_unidades"));
					campanhaProduto.setSnPagamentoAntecipado(rsCampanhaProduto.getInt("sn_pagamento_antecipado"));
					campanhaProduto.setCampanha(campanha);
					campanhaProduto.setProduto(produto);
					
					campanhaProdutos.add(campanhaProduto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return campanhaProdutos;
	}
	
	public List<CampanhaProduto> produtoCampanhaNormalManualLazy(FiltroCampanhaProduto filtroCampanhaProduto, int first, int pageSize){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{		
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}
						
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+										    
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+										    
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual>0 and "+												
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao + " LIMIT "+first+","+ pageSize;				
			}else{				
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto AND pe.nr_estoque_atual > (pe.nr_reserva + pe.nr_troca) "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
											"date(c.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=0 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual>0 and "+										    
										    queryFiltro +
										    "cp.vl_preco_campanha>0 "+										    
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto AND pe2.nr_estoque_atual > (pe2.nr_reserva + pe2.nr_troca) "+
											"WHERE  "+
												"date(c2.dt_final)>= date('"+ dt.format(new Date()) +"') and "+
												"date(c2.dt_inicial) <= date('"+dt.format(new Date()) +"') and "+
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=0 and "+
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual > 0 and "+												
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao  + " LIMIT "+first+","+ pageSize;
			}
			
			System.out.println("wesley: " + queryCampanhaProduto);
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
					campanha.setNmLinkSelo(rsCampanhaProduto.getString("nm_link_selo"));
					campanha.setSnPromocao(rsCampanhaProduto.getBoolean("sn_promocao"));
					campanha.setNrPercentualValor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setSnEpi(rsCampanhaProduto.getString("sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					campanhaProduto.setSnUltimasUnidades(rsCampanhaProduto.getInt("sn_ultimas_unidades"));
					campanhaProduto.setSnPagamentoAntecipado(rsCampanhaProduto.getInt("sn_pagamento_antecipado"));
					campanhaProduto.setCampanha(campanha);
					campanhaProduto.setProduto(produto);
					
					campanhaProdutos.add(campanhaProduto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return campanhaProdutos;
	}
	
	//emcomanda normal
	public List<CampanhaProduto> produtoCampanhaEncomendaManual(FiltroCampanhaProduto filtroCampanhaProduto){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{		
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}
						
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "cp.nr_classifica_preco_encomenda, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=1 and "+		
										    "c.nr_prioridade=4 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual<=0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+										    
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+												
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=1 and "+
												"c2.nr_prioridade=4 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+												
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual<=0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;
				
				System.out.println("wesley " + queryCampanhaProduto);
				
			}else{
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "cp.nr_classifica_preco_encomenda, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=1 and "+										    
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual<=0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 "+										    
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+												
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=1 and "+												
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual<=0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;
			}
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
					campanha.setNmLinkSelo(rsCampanhaProduto.getString("nm_link_selo"));
					campanha.setSnPromocao(rsCampanhaProduto.getBoolean("sn_promocao"));
					campanha.setNrPercentualValor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setSnEpi(rsCampanhaProduto.getString("sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					campanhaProduto.setSnUltimasUnidades(rsCampanhaProduto.getInt("sn_ultimas_unidades"));
					campanhaProduto.setSnPagamentoAntecipado(rsCampanhaProduto.getInt("sn_pagamento_antecipado"));
					campanhaProduto.setNrClassificaPrecoEncomenda(rsCampanhaProduto.getDouble("nr_classifica_preco_encomenda"));
					campanhaProduto.setCampanha(campanha);
					campanhaProduto.setProduto(produto);
					
					campanhaProdutos.add(campanhaProduto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return campanhaProdutos;
	}
	
	//encomenda lazy
	public List<CampanhaProduto> produtoCampanhaEncomendaManualLazy(FiltroCampanhaProduto filtroCampanhaProduto, int first, int pageSize){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{		
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}
						
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "cp.nr_classifica_preco_encomenda, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=1 and "+		
										    "c.nr_prioridade=4 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual<=0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+										    
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+												
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=1 and "+
												"c2.nr_prioridade=4 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+												
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual<=0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao + " LIMIT "+first+","+ pageSize;
				
				System.out.println("wesley " + queryCampanhaProduto);
				
			}else{
				queryCampanhaProduto = "SELECT "+ 	
										    "cp.id_produto, "+
										    "p.nm_produto, "+
										    "p.sn_epi, "+
										    "c.id_campanha, "+
										    "c.nr_percentual_valor, "+
										    "IF(STRCMP( ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'),''), ifnull(c.nm_link_selo,'http://images.agro-fauna.com.br/imgtrator/promocao.png'), 'http://images.agro-fauna.com.br/imgtrator/promocao.png') as nm_link_selo, "+
										    "c.sn_promocao, "+
										    "cp.id_produto_campanha, "+
										    "cp.vl_preco_campanha, "+
										    "cp.sn_ultimas_unidades, "+ 
										    "cp.sn_pagamento_antecipado, "+
										    "cp.nr_classifica_preco_encomenda, "+
										    "c.nm_campanha, "+
										    "c.dt_final, "+
										    "c.nr_prioridade "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=1 and "+										    
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual<=0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 "+										    
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+												
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=1 and "+												
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual<=0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao+ " LIMIT "+first+","+ pageSize;
			}
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
					campanha.setNmLinkSelo(rsCampanhaProduto.getString("nm_link_selo"));
					campanha.setSnPromocao(rsCampanhaProduto.getBoolean("sn_promocao"));
					campanha.setNrPercentualValor(rsCampanhaProduto.getDouble("nr_percentual_valor"));
					
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setSnEpi(rsCampanhaProduto.getString("sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
					campanhaProduto.setSnUltimasUnidades(rsCampanhaProduto.getInt("sn_ultimas_unidades"));
					campanhaProduto.setSnPagamentoAntecipado(rsCampanhaProduto.getInt("sn_pagamento_antecipado"));
					campanhaProduto.setNrClassificaPrecoEncomenda(rsCampanhaProduto.getDouble("nr_classifica_preco_encomenda"));
					campanhaProduto.setCampanha(campanha);
					campanhaProduto.setProduto(produto);
					
					campanhaProdutos.add(campanhaProduto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return campanhaProdutos;
	}
	
	public int produtoCampanhaEncomendaManualContador(FiltroCampanhaProduto filtroCampanhaProduto){
		int contador = 0;
		try{		
			String queryFiltro = "";
			String queryFiltro2 = "";
			int liberaQuery = 0;
			if(filtroCampanhaProduto.getDescricao() != null  && filtroCampanhaProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";
				queryFiltro2 = "p2.nm_produto like ? and ";
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroCampanhaProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "cp.vl_preco_campanha DESC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "cp.vl_preco_campanha ASC";
				
			}else if(filtroCampanhaProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}
						
			String queryCampanhaProduto = "";			
			if(filtroCampanhaProduto.getTipoConsulta() != null && filtroCampanhaProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+ 	
										    "count(*) as total "+										   
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=1 and "+		
										    "c.nr_prioridade=4 and "+
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual<=0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 and "+										    
										    "pt.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' "+
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_tipo_produto ptp2 on ptp2.id_produto=p2.id_produto "+
											    "inner join db_agro_matriz.produto_tipo pt2 on pt2.id_produto_tipo=ptp2.id_produto_tipo "+
											    "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto "+
											"WHERE "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+												
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=1 and "+
												"c2.nr_prioridade=4 and "+
												"pt2.nm_produto_tipo like '%" + filtroCampanhaProduto.getTipoConsulta() +"%' and "+												
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual<=0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;
				
				System.out.println("wesley " + queryCampanhaProduto);
				
			}else{
				queryCampanhaProduto = "SELECT "+ 	
											"count(*) as total "+
										"FROM  "+
											"db_agro_matriz.campanha c "+
										    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
										    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
										    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										"WHERE "+
											"date(c.dt_final) >= date('"+dt.format(new Date()) +"') and "+
										    "cc.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
										    "c.sn_encomenda=1 and "+										    
										    "p.sn_disponivel_venda='S' and "+
										    "pe.nr_estoque_atual<=0 and "+
										    queryFiltro +
										    "cp.vl_preco_campanha>0 "+										    
										"group by "+
											"c.nr_prioridade, "+
											"cp.id_produto "+
										    "HAVING "+
												"c.nr_prioridade = (SELECT MAX(c2.nr_prioridade) "+ 
										    "FROM "+
												"db_agro_matriz.campanha c2 "+
												"inner join db_agro_matriz.campanha_cliente cc2 on cc2.id_campanha=c2.id_campanha "+
												"INNER join db_agro_matriz.campanha_produto cp2 on cp2.id_campanha=c2.id_campanha "+
										        "inner join db_agro_matriz.produto p2 on p2.id_produto=cp2.id_produto "+
										        "inner join db_agro_matriz.produto_estoque pe2 on pe2.id_produto=p2.id_produto "+
											"WHERE  "+
												"date(c2.dt_final) >= date('"+ dt.format(new Date()) +"') and "+												
												"cc2.id_pessoa="+ filtroCampanhaProduto.getCliente().getIdPessoa() +" and "+
												"c2.sn_encomenda=1 and "+												
												"p2.sn_disponivel_venda='S' and "+
												"pe2.nr_estoque_atual<=0 and "+
												queryFiltro2 +
												"cp2.vl_preco_campanha>0 and "+ 
										        "cp2.id_produto=cp.id_produto) "+													
										"ORDER BY "+ queryOrdenacao;
			}
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroCampanhaProduto.getDescricao()+"%");
					stmtCampanhaProduto.setString(2, "%"+filtroCampanhaProduto.getDescricao()+"%");
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					
					contador = contador + rsCampanhaProduto.getInt("total");
										
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}		
		return contador;
	}
}
