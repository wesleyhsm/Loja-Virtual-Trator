package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.agrofauna.tratorweb.model.Setor;

public class SetorRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	public List<Setor> buscarSetor(){
		try {
			return manager.createQuery("from Setor s order by s.nmSetor ASC", Setor.class)
				.getResultList();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Setor buscarSetor2(Setor setor){
		try {
			return manager.createQuery("from Setor s where s.idSetor=:idsetor", Setor.class)
				.setParameter("idsetor", setor.getIdSetor())	
				.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
}
