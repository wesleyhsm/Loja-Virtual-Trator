package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Estado;

public class EstadoRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public List<Estado> buscarEstado(){
		try {
			return manager.createQuery("from Estado e order by e.sgEstado ASC", Estado.class)
				.getResultList();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public Estado buscarEstadoSigla(String sigla){
		try {
			return manager.createQuery("from Estado e where e.sgEstado = :sigla", Estado.class)
				.setParameter("sigla", sigla.toUpperCase())	
				.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public Estado buscarEstadoCliente(Cliente cliente){
		try {
			Query query = manager.createNamedQuery("Estado.cliente");
			query.setParameter("idPessoa", cliente.getIdPessoa());			
			return (Estado) query.getSingleResult();		
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public Estado buscarEstadoOrigem(long idEstado){
		try {
			Query query = manager.createNamedQuery("Estado.origem");
			query.setParameter("idEstado", idEstado);			
			return (Estado) query.getSingleResult();		
			
		} catch (NoResultException e) {
			return null;
		}
	}
}
