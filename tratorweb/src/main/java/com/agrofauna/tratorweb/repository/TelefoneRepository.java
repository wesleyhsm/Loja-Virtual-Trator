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
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Telefone;

public class TelefoneRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarNovoTelefone(Telefone telefone){
		try{
			manager.merge(telefone);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Telefone> buscarTelefone(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaTelefone = session.createCriteria(Telefone.class, "t");		
		criteriaTelefone.createCriteria("t.pessoa", "p")		
				.add(Restrictions.eq("p.idPessoa", cliente.getIdPessoa()));
		
		return criteriaTelefone.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Telefone> buscarTelefoneExiste(Telefone telefone){
		List<Telefone> listTelefone = new ArrayList<Telefone>();
		try {			
			Query query = manager.createNamedQuery("Telefone.existeTelefone");
			query.setParameter("numeroTelefone", telefone.getNmTelefone().toLowerCase().trim());			
			listTelefone = query.getResultList();
			
			System.out.println("wesley telefone digitado: " + telefone.getNmTelefone());
			for(Telefone t: listTelefone){
				System.out.println("wesley telefone banco: " + t.getNmTelefone());
			}
			
		} catch (NoResultException e) {			
		}
		return listTelefone;
	}

	@Transactional
	public boolean removerTelefone(Telefone telefone){
		try {			
			manager.remove(manager.find(Telefone.class, telefone.getIdTelefone()));
			manager.flush();
			return true;
			
		} catch (NoResultException e) {
			return false;
		}		
	}
}
