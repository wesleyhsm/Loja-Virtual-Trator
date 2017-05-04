package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.ProdutoLote;

public class ProdutoLoteRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<ProdutoLote> buscaLoteProdutoAtivo(long idProduto){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(ProdutoLote.class, "pl")				
			.add(Restrictions.eq("pl.snStatusLote", 1))
			.addOrder(Order.asc("pl.dtVencimento"));
		
		criteriaProduto.createCriteria("pl.produto", "p")
			.add(Restrictions.eq("p.idProduto", idProduto));
						
		return criteriaProduto.list();
	}
	
	public ProdutoLote buscaLoteProdutoDataMaisProximoDoVencimento(long idProduto){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(ProdutoLote.class, "pl")				
			.add(Restrictions.eq("pl.snStatusLote", 1))			
			.setProjection(Projections.min("pl.dtVencimento"));
		
		criteriaProduto.createCriteria("pl.produto", "p")
			.add(Restrictions.eq("p.idProduto", idProduto));
						
		return (ProdutoLote) criteriaProduto.uniqueResult();
	}
}
