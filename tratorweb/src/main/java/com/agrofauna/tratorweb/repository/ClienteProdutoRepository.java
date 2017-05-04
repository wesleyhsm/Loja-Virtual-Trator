package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.filtro.FiltroPersonalizar;
import com.agrofauna.tratorweb.filtro.FiltroPersonalizarProduto;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.ClienteProduto;
import com.agrofauna.tratorweb.model.Produto;

public class ClienteProdutoRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarClienteProdutos(ClienteProduto ClienteProduto){
		try{
			manager.merge(ClienteProduto);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@Transactional
	public boolean removerClienteProduto(ClienteProduto clienteProduto){
		try{		
			manager.remove(manager.find(ClienteProduto.class, clienteProduto.getIdClienteProduto()));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ClienteProduto> buscarClienteProdutoExiste(ClienteProduto clienteProduto){
		List<ClienteProduto> listClienteProduto = new ArrayList<ClienteProduto>(); 
		
		try {
			
			Query query = manager.createNamedQuery("ClienteProduto.existe");
			query.setParameter("idPessoa", clienteProduto.getCliente().getIdPessoa());
			query.setParameter("idProduto", clienteProduto.getProduto().getIdProduto());
			listClienteProduto = query.getResultList();
			
		} catch (NoResultException e) {		
		}		
		return listClienteProduto;
	}
	
	@SuppressWarnings("unchecked")
	public List<ClienteProduto> buscarClienteProduto(Cliente cliente){
		List<ClienteProduto> listClienteProduto = new ArrayList<ClienteProduto>(); 
		
		try {			
			Query query = manager.createNamedQuery("ClienteProduto.cadastrada");
			query.setParameter("idPessoa", cliente.getIdPessoa());			
			listClienteProduto = query.getResultList();
			
		} catch (NoResultException e) {		
		}		
		return listClienteProduto;
	}
	
	@SuppressWarnings("unchecked")	
	public List<ClienteProduto> listaPersonalizar(FiltroPersonalizar filtroPersonalizar){
		try{
			Criteria criteria = criaCriteriaPersonalizar(filtroPersonalizar);	
			
			criteria.setFirstResult(filtroPersonalizar.getPrimeiroRegistro());
			criteria.setMaxResults(filtroPersonalizar.getQuantidadeRegistro());		
			
			return criteria.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public int quantidadeFiltradosPersonalizar(FiltroPersonalizar filtroPersonalizar){
		Criteria criteria = criaCriteriaPersonalizar(filtroPersonalizar);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaPersonalizar(FiltroPersonalizar filtroPersonalizar){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(ClienteProduto.class, "cp");
		
		criteriaProduto.createCriteria("cp.cliente", "c")
		.add( Restrictions.eq("c.idPessoa", filtroPersonalizar.getCliente().getIdPessoa() ));
				
		criteriaProduto.createCriteria("cp.produto", "p");
								
		criteriaProduto.addOrder(Order.asc("p.nmProduto"));
		
		criteriaProduto.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteriaProduto;
	}
}
