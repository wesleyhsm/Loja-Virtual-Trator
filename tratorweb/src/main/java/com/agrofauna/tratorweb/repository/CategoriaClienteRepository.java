package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.CategoriaCliente;
import com.agrofauna.tratorweb.model.Cliente;

public class CategoriaClienteRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public List<CategoriaCliente> buscarCategoriaCliente(){
		try {
			return manager.createQuery("from CategoriaCliente cc order by cc.nmCategoriaCliente ASC", CategoriaCliente.class)
				.getResultList();
			
		} catch (NoResultException e) {
			return null;
		}
	}
		
	public CategoriaCliente buscarCategoriaCliente(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaCategoriaCliente = session.createCriteria(CategoriaCliente.class, "cc");	
		
		criteriaCategoriaCliente.createCriteria("cc.clientes", "c")
			.add(Restrictions.eq("c.idPessoa", cliente.getIdPessoa()));
		
		return (CategoriaCliente) criteriaCategoriaCliente.uniqueResult();
	}
}
