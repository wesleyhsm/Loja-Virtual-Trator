package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.ProdutoCultura;

public class ProdutoCulturaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<ProdutoCultura> buscarTodasCulturaProduto(CampanhaProduto campanhaProduto){
		Session session = manager.unwrap(Session.class);
						
		Criteria criteriaProdutoCultura = session.createCriteria(ProdutoCultura.class, "pc")
				.add(Restrictions.eq("snStatus", 1));
		
		criteriaProdutoCultura.createCriteria("pc.produto", "p")
		.add(Restrictions.eq("snDisponivelVenda", "S"));
		
		
		criteriaProdutoCultura.createCriteria("p.campanhaProdutos", "cp")
		.add(Restrictions.eq("idProdutoCampanha", campanhaProduto.getIdProdutoCampanha()));						
		
		return criteriaProdutoCultura.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdutoCultura> buscarTodasCulturaProdutoCompreGanhe(Produto produto){
		Session session = manager.unwrap(Session.class);
						
		Criteria criteriaProdutoCultura = session.createCriteria(ProdutoCultura.class, "pc")
				.add(Restrictions.eq("snStatus", 1));
		
		criteriaProdutoCultura.createCriteria("pc.produto", "p")
		.add(Restrictions.eq("snDisponivelVenda", "S"))
		.add(Restrictions.eq("idProduto", produto.getIdProduto()));
											
		return criteriaProdutoCultura.list();
	}
}
