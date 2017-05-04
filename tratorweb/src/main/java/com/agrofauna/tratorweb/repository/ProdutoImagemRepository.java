package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoImagem;

public class ProdutoImagemRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<ProdutoImagem> buscarImagemPrduto(Produto produto){
		Session session = manager.unwrap(Session.class);		
				
		Criteria criteriaProduto = session.createCriteria(ProdutoImagem.class, "pi")			
				.add(Restrictions.eq("pi.snStatus", true));				
		
		criteriaProduto.createCriteria("pi.produto", "p")
			.add(Restrictions.eq("p.idProduto", produto.getIdProduto()));
				
		criteriaProduto.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteriaProduto.list();
	}
	
	public ProdutoImagem buscarImagemProdutoPrincipal(Produto produto){
		try{
			Session session = manager.unwrap(Session.class);		
			
			Criteria criteriaProduto = session.createCriteria(ProdutoImagem.class, "pi")			
					.add(Restrictions.eq("pi.snStatus", true))
					.add(Restrictions.eq("pi.snPrincipal", true));				
			
			criteriaProduto.createCriteria("pi.produto", "p")
				.add(Restrictions.eq("p.idProduto", produto.getIdProduto()));
					
			criteriaProduto.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				
			return (ProdutoImagem) criteriaProduto.uniqueResult();
		}catch(Exception e){			
			return null;
		}	
	}
}
