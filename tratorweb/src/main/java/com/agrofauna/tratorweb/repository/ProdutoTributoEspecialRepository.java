package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.agrofauna.tratorweb.model.ConexaoBanco;
import com.agrofauna.tratorweb.model.ProdutoTributoEspecial;


public class ProdutoTributoEspecialRepository implements Serializable{

	private static final long serialVersionUID = 1L;
		
	public ProdutoTributoEspecial buscarProdutoTributoEspecial(Long idProduto){		 		
		ProdutoTributoEspecial produtoTributoEspecial = new ProdutoTributoEspecial();		
		try {			
						
			String queryTributo = "SELECT id_produto, nr_alicota, nr_base_calculo FROM "+
								  "db_agro_matriz.produto_tributo_especial where id_produto="+idProduto;
			Connection connTributo = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtTributo = connTributo.prepareStatement(queryTributo)) { 
				ResultSet rsTributo = stmtTributo.executeQuery();								
				if(rsTributo.next()){					
										
					produtoTributoEspecial.setNrAlicota(rsTributo.getDouble("nr_alicota"));
					produtoTributoEspecial.setNrBaseCalculo(rsTributo.getDouble("nr_base_calculo"));
					
				}else{
					produtoTributoEspecial = null;
				}
				rsTributo.close();
				stmtTributo.close();
			}	
			connTributo.close(); 
		}catch(SQLException sql){			
			produtoTributoEspecial = null;
		}	
		return produtoTributoEspecial;
	}
}
