package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoImagem;
import com.agrofauna.tratorweb.model.ConexaoBanco;

public class CampanhaImagemRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
			
	public List<ProdutoImagem> buscarTodasImagensProduto(CampanhaProduto campanhaProduto){
		List<ProdutoImagem> listProdutoImagems = new ArrayList<ProdutoImagem>(); 
		
		try {			
			//Query query = manager.createNamedQuery("ProdutoImagem.todasImagensProduto");
			//query.setParameter("idProdutoCampanha", campanhaProduto.getIdProdutoCampanha());			
			//listProdutoImagems = query.getResultList();
			
			String imagemProduto = "SELECT "+ 
										"pi.id_produto_imagem, "+ 
									    "pi.nm_link "+
									"FROM "+
										"db_agro_matriz.campanha_produto pc "+
									    "inner join db_agro_matriz.produto_imagem pi on pi.id_produto=pc.id_produto "+
									"WHERE "+
										"pc.id_produto_campanha="+campanhaProduto.getIdProdutoCampanha();
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(imagemProduto)) { 
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
					
					ProdutoImagem pi = new ProdutoImagem();
					pi.setIdProdutoImagem(rsCampanhaProduto.getLong("id_produto_imagem"));
					pi.setNmLink(rsCampanhaProduto.getString("nm_link"));
					
					listProdutoImagems.add(pi);
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();			
		} catch (SQLException e) {		
		}		
		return listProdutoImagems;
	}
	
	@SuppressWarnings("unchecked")	
	public List<ProdutoImagem> buscarTodasImagensProdutoCompreGanhe(Produto produto){
		List<ProdutoImagem> listProdutoImagems = new ArrayList<ProdutoImagem>(); 
		
		try {			
			Query query = manager.createNamedQuery("ProdutoImagem.todasImagensProdutoCompreGanhe");
			query.setParameter("idProduto", produto.getIdProduto());			
			listProdutoImagems = query.getResultList();
					
		} catch (NoResultException e) {		
		}		 
		return listProdutoImagems;
	}
}
