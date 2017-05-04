package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.agrofauna.tratorweb.model.Campanha;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.ConexaoBanco;
import com.agrofauna.tratorweb.model.Produto;

public class ProdutoBannerRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<CampanhaProduto> todosBannerProdutoPosicao1(Cliente cliente){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{
			String queryCampanhaProduto = "SELECT "+ 	
											    "cp.id_produto, "+
											    "p.nm_produto, "+
											    "p.sn_epi, "+
											    "c.id_campanha, "+
											    "cp.id_produto_campanha, "+
											    "cp.vl_preco_campanha, "+
											    "c.nm_campanha, "+
											    "c.dt_final, "+
											    "c.nr_prioridade, "+
											    "pb.nm_link, "+
											    "pb.id_produto_banner "+
											"FROM "+
												"db_agro_matriz.campanha c "+
											    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
											    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
											    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
											    "inner join db_agro_matriz.produto_banner pb on pb.id_produto=p.id_produto "+
											"WHERE "+
												"c.dt_final>='"+ dt.format(new Date()) +"' and "+
											    "cc.id_pessoa="+ cliente.getIdPessoa() +" and "+
											    "c.sn_encomenda=0 and "+
											    "p.sn_disponivel_venda='S' and "+
											    "cp.vl_preco_campanha>0 and "+
											    "pb.sn_status=1 and "+
											    "pb.id_banner_posicao=1 "+
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
											        "inner join db_agro_matriz.produto_banner pb2 on pb2.id_produto=p2.id_produto "+
												"WHERE "+
													"c2.dt_final>='"+ dt.format(new Date()) +"' and "+
													"cc2.id_pessoa="+ cliente.getIdPessoa() +" and "+
													"c2.sn_encomenda=0 and "+
													"p2.sn_disponivel_venda='S' and "+
													"pb2.id_banner_posicao=1 and "+
													"pb2.sn_status=1 and "+
													"cp2.vl_preco_campanha>0 and "+ 
											        "cp2.id_produto=cp.id_produto) "+													
											"ORDER BY "+
												"p.nm_produto LIMIT 5";
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
								
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setDescAspecto(rsCampanhaProduto.getString("nm_link"));
					produto.setSnEpi(rsCampanhaProduto.getString("p.sn_epi"));		
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
								
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
	
	public List<CampanhaProduto> todosBannerProdutoPosicao2(Cliente cliente){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{
			String queryCampanhaProduto = "SELECT "+ 	
											    "cp.id_produto, "+
											    "p.nm_produto, "+
											    "p.sn_epi, "+
											    "c.id_campanha, "+
											    "cp.id_produto_campanha, "+
											    "cp.vl_preco_campanha, "+
											    "c.nm_campanha, "+
											    "c.dt_final, "+
											    "c.nr_prioridade, "+
											    "pb.nm_link, "+
											    "pb.id_produto_banner "+
											"FROM "+
												"db_agro_matriz.campanha c "+
											    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
											    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
											    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
											    "inner join db_agro_matriz.produto_banner pb on pb.id_produto=p.id_produto "+
											"WHERE "+
												"c.dt_final>='"+ dt.format(new Date()) +"' and "+
											    "cc.id_pessoa="+ cliente.getIdPessoa() +" and "+
											    "c.sn_encomenda=0 and "+
											    "p.sn_disponivel_venda='S' and "+
											    "pb.sn_status=1 and "+
											    "cp.vl_preco_campanha>0 and "+
											    "pb.id_banner_posicao=2 "+
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
											        "inner join db_agro_matriz.produto_banner pb2 on pb2.id_produto=p2.id_produto "+
												"WHERE "+
													"c2.dt_final>='"+ dt.format(new Date()) +"' and "+
													"cc2.id_pessoa="+ cliente.getIdPessoa() +" and "+
													"c2.sn_encomenda=0 and "+
													"p2.sn_disponivel_venda='S' and "+
													"pb2.id_banner_posicao=2 and "+
													"pb2.sn_status=1 and "+
													"cp2.vl_preco_campanha>0 and "+ 
											        "cp2.id_produto=cp.id_produto) "+													
											"ORDER BY "+
												"p.id_produto LIMIT 5";
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
								
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setDescAspecto(rsCampanhaProduto.getString("nm_link"));
					produto.setSnEpi(rsCampanhaProduto.getString("p.sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
								
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
	
	public List<CampanhaProduto> todosBannerProdutoPosicao3(Cliente cliente){
		List<CampanhaProduto> campanhaProdutos = new ArrayList<CampanhaProduto>();
		try{
			String queryCampanhaProduto = "SELECT "+ 	
											    "cp.id_produto, "+
											    "p.nm_produto, "+
											    "p.sn_epi, "+
											    "c.id_campanha, "+
											    "cp.id_produto_campanha, "+
											    "cp.vl_preco_campanha, "+
											    "c.nm_campanha, "+
											    "c.dt_final, "+
											    "c.nr_prioridade, "+
											    "pb.nm_link, "+
											    "pb.id_produto_banner "+
											"FROM "+
												"db_agro_matriz.campanha c "+
											    "inner join db_agro_matriz.campanha_cliente cc on cc.id_campanha=c.id_campanha "+
											    "INNER join db_agro_matriz.campanha_produto cp on cp.id_campanha=c.id_campanha "+
											    "inner join db_agro_matriz.produto p on p.id_produto=cp.id_produto "+
											    "inner join db_agro_matriz.produto_banner pb on pb.id_produto=p.id_produto "+
											"WHERE "+
												"c.dt_final>='"+ dt.format(new Date()) +"' and "+
											    "cc.id_pessoa="+ cliente.getIdPessoa() +" and "+
											    "c.sn_encomenda=0 and "+
											    "p.sn_disponivel_venda='S' and "+
											    "pb.sn_status=1 and "+
											    "cp.vl_preco_campanha>0 and "+
											    "pb.id_banner_posicao=3 "+
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
											        "inner join db_agro_matriz.produto_banner pb2 on pb2.id_produto=p2.id_produto "+
												"WHERE "+
													"c2.dt_final>='"+ dt.format(new Date()) +"' and "+
													"cc2.id_pessoa="+ cliente.getIdPessoa() +" and "+
													"c2.sn_encomenda=0 and "+
													"p2.sn_disponivel_venda='S' and "+
													"pb2.id_banner_posicao=3 and "+
													"cp2.vl_preco_campanha>0 and "+ 
													"pb2.sn_status=1 and "+
											        "cp2.id_produto=cp.id_produto) "+													
											"ORDER BY "+
												"p.nm_produto desc LIMIT 5";
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					Campanha campanha = new Campanha();
					campanha.setIdCampanha(rsCampanhaProduto.getLong("id_campanha"));
					campanha.setNmCampanha(rsCampanhaProduto.getString("nm_campanha"));
					campanha.setNrPrioridade(rsCampanhaProduto.getInt("nr_prioridade")); 
					campanha.setDtFinal(rsCampanhaProduto.getDate("dt_final"));
								
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setDescAspecto(rsCampanhaProduto.getString("nm_link"));
					produto.setSnEpi(rsCampanhaProduto.getString("p.sn_epi"));
					
					CampanhaProduto campanhaProduto = new CampanhaProduto();
					campanhaProduto.setIdProdutoCampanha(rsCampanhaProduto.getLong("id_produto_campanha"));
					campanhaProduto.setVlPrecoCampanha(rsCampanhaProduto.getDouble("vl_preco_campanha"));
								
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
	
}
