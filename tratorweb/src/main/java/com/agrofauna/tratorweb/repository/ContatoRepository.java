package com.agrofauna.tratorweb.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.Contato;

public class ContatoRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarContato(Contato contato){
		try{
			manager.merge(contato);			
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
}
