package com.agrofauna.tratorweb.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.agrofauna.tratorweb.model.IcmsEstado;

public class IcmsEstadoRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	public IcmsEstado buscarIcmsEstado(long idEstadoOrigem, long idEstadoDestino){		
		try {
			Query query = manager.createNamedQuery("IcmsEstado.pegaCalculo");
			query.setParameter("idEstadoOrigem", idEstadoOrigem);	
			query.setParameter("idEstadoDestino", idEstadoDestino);
			return (IcmsEstado) query.getSingleResult();		
			
		} catch (NoResultException e) {
			return null;
		}
	}
}
