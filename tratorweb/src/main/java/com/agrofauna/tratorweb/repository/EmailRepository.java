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
import com.agrofauna.tratorweb.model.Email;

public class EmailRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarNovoEmail(Email email){
		try{
			manager.merge(email);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")	
	public List<Email> buscarEmail(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaEmail = session.createCriteria(Email.class, "e");		
		criteriaEmail.createCriteria("e.pessoa", "p")		
				.add(Restrictions.eq("p.idPessoa", cliente.getIdPessoa()));
		
		return criteriaEmail.list();		
	}
	
	@SuppressWarnings("unchecked")
	public List<Email> buscarEmailExiste(Email email){
		List<Email> listEmail = new ArrayList<Email>();
		try {		
			Query query = manager.createNamedQuery("Email.existeEmail");
			query.setParameter("email", email.getNmEmail().toLowerCase().trim());			
			listEmail = query.getResultList();
						
		} catch (NoResultException e) {			
		}
		return listEmail;
	}
	
	
	@Transactional
	public boolean removerEmail(Email email){
		try {			
			//manager.find(Email.class, email);
			manager.remove(manager.find(Email.class, email.getIdEmail()));
			manager.flush();
			return true;
			
		} catch (NoResultException e) {
			return false;
		}		
	}
}
