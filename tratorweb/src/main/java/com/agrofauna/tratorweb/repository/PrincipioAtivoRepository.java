package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.PrincipioAtivo;

public class PrincipioAtivoRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<PrincipioAtivo> buscarPincipioAtivo(String nmPrincipioAtivo){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaPrincipioAtivo = session.createCriteria(PrincipioAtivo.class, "pa")
				.add(Restrictions.ilike("pa.nmPrincipioAtivo", nmPrincipioAtivo, MatchMode.ANYWHERE))
				.addOrder(Order.asc("pa.nmPrincipioAtivo"));
		
		criteriaPrincipioAtivo.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteriaPrincipioAtivo.list();
	}	
}
