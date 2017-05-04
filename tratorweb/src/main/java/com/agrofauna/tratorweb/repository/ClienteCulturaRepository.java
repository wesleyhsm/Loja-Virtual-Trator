package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.ClienteCultura;

public class ClienteCulturaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarClienteCultura(ClienteCultura clienteCultura){
		try{			
			manager.merge(clienteCultura);
			manager.flush();			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
		
	@Transactional
	public boolean removerClienteCultura(ClienteCultura clienteCultura){
		try{
			//manager.find(Email.class, email.getIdEmail())
			manager.remove(manager.find(ClienteCultura.class, clienteCultura.getIdClienteCultura()));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<ClienteCultura> buscarClienteCulturaExiste(ClienteCultura clienteCultura){
		List<ClienteCultura> listClienteCultura = new ArrayList<ClienteCultura>(); 
		
		try {
			
			Query query = manager.createNamedQuery("ClienteCultura.existe");
			query.setParameter("idPessoa", clienteCultura.getCliente().getIdPessoa());
			query.setParameter("idCultura", clienteCultura.getCultura().getIdCultura());
			listClienteCultura = query.getResultList();
			
		} catch (NoResultException e) {		
		}		
		return listClienteCultura;
	}
	
	@SuppressWarnings("unchecked")
	public List<ClienteCultura> buscarClienteCultura(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaCultura = session.createCriteria(ClienteCultura.class, "cp");
		
		criteriaCultura.createCriteria("cp.cliente", "c")
			.add(Restrictions.eq("c.idPessoa", cliente.getIdPessoa()));
		
		criteriaCultura.createCriteria("cp.cultura", "cc")
			.addOrder(Order.asc("cc.nmCultura"));
				
		return criteriaCultura.list();
	}
}
