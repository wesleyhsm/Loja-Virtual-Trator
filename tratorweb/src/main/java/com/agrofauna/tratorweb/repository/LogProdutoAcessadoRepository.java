package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.ConexaoBanco;
import com.agrofauna.tratorweb.model.LogProdutoAcessado;

public class LogProdutoAcessadoRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//@Inject
	//@PersistenceContext(unitName="mydb")	
	//private EntityManager manager;
	
	@Transactional
	public boolean salvarLogProdutoAcessado(LogProdutoAcessado logProdutoAcessado){
		try{
			//manager.merge(logProdutoAcessado);						
			String insertLog = "INSERT INTO db_agro_matriz.log_produto_acesso "+
							   "(dt_acesso, nm_tipo_acesso, id_pessoa_cliente, id_produto) "+
							   "VALUES "+
							   "('"+ df.format(logProdutoAcessado.getDtAcesso()) +"', '"+ logProdutoAcessado.getNmTipoAcesso() +"', "+ logProdutoAcessado.getCliente().getIdPessoa() +", "+ logProdutoAcessado.getProduto().getIdProduto() +")";
			
			System.out.println("wesley salvo: " + insertLog);
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(insertLog)) {
				stmtCampanhaProduto.execute();				
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
}
