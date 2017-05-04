package com.agrofauna.tratorweb.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Suframa;

public class SuframaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
		
	public Suframa buscarSuframa(Cliente cliente){		 		
		try {			
			Query query = manager.createNamedQuery("Suframa.clienteSuframa");
			query.setParameter("idPessoa", cliente.getIdPessoa());			
			return (Suframa) query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}		
	}
}
