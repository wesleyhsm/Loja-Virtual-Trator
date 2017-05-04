package com.agrofauna.tratorweb.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.Cliente;

public class ClienteRepository implements Serializable{
private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public Cliente buscarCliente(Cliente cliente){
		try {			
			Query query = manager.createNamedQuery("Cliente.clienteId");
			query.setParameter("idPessoa", cliente.getIdPessoa());			
			return (Cliente) query.getSingleResult();
			
		} catch (NoResultException e){
			return null;
		}
	}
}
