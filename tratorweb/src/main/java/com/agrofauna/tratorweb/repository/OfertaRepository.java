package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.Email;

public class OfertaRepository implements Serializable{

private static final long serialVersionUID = 1L;
	
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public void salvarEmail(Email email){
		try{
			manager.merge(email);			
		}catch(Exception e){			
		}	
	}
	
	@Transactional
	public List<Email> buscarEmail(Email email){
		
		List<Email> listEmail = new ArrayList<Email>();
		
		try {
			listEmail = manager.createQuery("from Email where lower(nm_email) = :email", Email.class)
				.setParameter("email", email.getNmEmail().toLowerCase())
				.getResultList();
			
		} catch (NoResultException e) {			
		}
		
		return listEmail;
	}
}
