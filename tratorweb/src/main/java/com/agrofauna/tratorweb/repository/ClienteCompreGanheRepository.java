package com.agrofauna.tratorweb.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.ClienteCompreGanhe;

public class ClienteCompreGanheRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
		
	public ClienteCompreGanhe buscarClienteCompreGanhe(Cliente cliente){
		try {			
			Query query = manager.createNamedQuery("ClienteCompreGanhe.pontos");
			query.setParameter("idPessoa", cliente.getIdPessoa());			
			return (ClienteCompreGanhe) query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}	
	}
}
