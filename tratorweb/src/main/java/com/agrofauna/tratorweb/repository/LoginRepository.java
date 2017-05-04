package com.agrofauna.tratorweb.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Login;

public class LoginRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")	
	private EntityManager manager;
	
	@Transactional
	public void salvarLogin(Login login){
		try{
			manager.merge(login);			
		}catch(Exception e){
			e.printStackTrace();			
		}	
	}
	
	public Login buscarLogin(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaLogin = session.createCriteria(Login.class, "l");		
		criteriaLogin.createCriteria("l.pessoa", "p")		
				.add(Restrictions.eq("p.idPessoa", cliente.getIdPessoa()));
		
		return (Login) criteriaLogin.uniqueResult();		
	}
	
	public Login porNmLogin(String nm_login){
		Login login = null;
		
		try {
			login = this.manager.createQuery("from Login where nm_login = :nm_login", Login.class)
				.setParameter("nm_login", nm_login.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {			
		}
		
		return login;
	}
}
