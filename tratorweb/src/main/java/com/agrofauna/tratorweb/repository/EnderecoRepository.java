package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Endereco;

public class EnderecoRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public Endereco buscarEndereco(Cliente cliente){
		try {			
			Query query = manager.createNamedQuery("Endereco.endereco");
			query.setParameter("idPessoa", cliente.getIdPessoa());			
			return (Endereco) query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;	
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Endereco> listaEndereco(Cliente cliente){
		try {			
			Query query = manager.createNamedQuery("Endereco.endereco");
			query.setParameter("idPessoa", cliente.getIdPessoa());			
			return query.getResultList();
			
		} catch (NoResultException e) {
			return null;	
		}
	}
}
