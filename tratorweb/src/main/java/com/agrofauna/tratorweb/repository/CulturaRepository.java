package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.Cultura;

public class CulturaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarNovoEmail(Cultura cultura){
		try{
			manager.merge(cultura);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")	
	public List<Cultura> buscarCulturas(){
		List<Cultura> listCulturas = new ArrayList<>();
		try{
			Query query = manager.createNamedQuery("Cultura.todos");						
			listCulturas= query.getResultList();			
			
		}catch(NoResultException nr){			
		}
		return listCulturas;
	}
}
