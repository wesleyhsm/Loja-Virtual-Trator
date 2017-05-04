package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.agrofauna.tratorweb.model.ConexaoBanco;
import com.agrofauna.tratorweb.model.Pedido;
import com.agrofauna.tratorweb.model.PedidoProduto;

public class PedidoReserva implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public boolean pedidoRevervaProduto(Pedido pedido, List<PedidoProduto> pedidoProdutos){
		try{
			
			for(int cont=0; pedidoProdutos.size() > cont; cont++){
				String queryReserva = "INSERT INTO db_agrofauna.CAD_RES "+
										"(COD_PROD, "+
										"QTDRES,"+
										"DATA,"+
										"VENDEDOR,"+
										"PEDNUM) "+
									  "VALUES "+
									  	"("+ pedidoProdutos.get(cont).getProduto().getIdProduto() +", " +
									  	pedidoProdutos.get(cont).getNrQuantidadeProduto() + ",'" +
									  	dt.format(new Date()) + "'," +
									  	"33," +
									  	pedido.getIdPedido() + ")";								 
				
				//System.out.println("wesley reserva: " + queryReserva);
				
				Connection connReserva = new ConexaoBanco().getConnectionNovo();
				try(java.sql.PreparedStatement stmtReserva = connReserva.prepareStatement(queryReserva)) {											
					stmtReserva.execute();
					stmtReserva.close();						
				}
				connReserva.close();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	
}
