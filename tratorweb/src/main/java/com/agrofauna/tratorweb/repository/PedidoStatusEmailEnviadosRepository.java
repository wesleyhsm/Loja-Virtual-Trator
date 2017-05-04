package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Pedido;
import com.agrofauna.tratorweb.model.PedidoStatusEmailEnviados;
import com.agrofauna.tratorweb.model.ConexaoBanco;

public class PedidoStatusEmailEnviadosRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<PedidoStatusEmailEnviados> pedidoStatusEmail(Pedido pedido){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaCategoriaCliente = session.createCriteria(PedidoStatusEmailEnviados.class, "pse");
		
		criteriaCategoriaCliente.createCriteria("pse.pedido", "p")
				.add(Restrictions.eq("p.idPedido", pedido.getIdPedido()));	
		
		criteriaCategoriaCliente.createCriteria("pse.pedidoStatusEmail", "ps");
		
		return criteriaCategoriaCliente.list();			
	}
	
	public void salvarPedidoStatusEmail(Pedido pedido, long idStatusPedido, long idPessoa, int status){
		try{
			String query =  "INSERT INTO db_agro_matriz.pedido_status_email_enviados \n"+
							"(nm_obs_pedido_status_email_enviados, id_pedido, id_pedido_status_email, dt_criacao, id_pessoa, sn_status) \n"+
							"VALUES \n"+
							"(?, ?, ?, '"+ dt.format(new Date()) +"', ?, ?)";
			
			System.out.println(query);
			Connection connPedidoStatusEmail = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtPedidoStatusEmail = connPedidoStatusEmail.prepareStatement(query)) {
				stmtPedidoStatusEmail.setString(1, "");
				stmtPedidoStatusEmail.setLong(2, pedido.getIdPedido());
				stmtPedidoStatusEmail.setLong(3, idStatusPedido);
				stmtPedidoStatusEmail.setLong(4, idPessoa);
				stmtPedidoStatusEmail.setInt(5, status);
				
				stmtPedidoStatusEmail.execute();
				stmtPedidoStatusEmail.close();
			}	
			connPedidoStatusEmail.close();	
		}catch(SQLException sql){			
			sql.printStackTrace();
		}
	}
}
