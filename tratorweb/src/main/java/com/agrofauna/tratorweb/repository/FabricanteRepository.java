package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.Fabricante;

public class FabricanteRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarNovoFabricante(Fabricante fabricante){
		try{
			manager.merge(fabricante);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Fabricante> buscarFabricantes(){
		List<Fabricante> listFabricantes = new ArrayList<>();
		try{
			Query query = manager.createNamedQuery("Fabricante.todos");						
			listFabricantes= query.getResultList();			
			
		}catch(NoResultException nr){			
		}
		return listFabricantes;
	}
	
	
}
