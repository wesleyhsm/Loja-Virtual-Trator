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
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.ClienteFabricante;

public class ClienteFabricanteRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarClienteFabricantes(ClienteFabricante clienteFabricante){
		try{
			manager.merge(clienteFabricante);
			manager.flush();			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@Transactional
	public boolean removerClienteFabricante(ClienteFabricante clienteFabricante){
		try{		
			manager.remove(manager.find(ClienteFabricante.class, clienteFabricante.getIdClienteFabricante()));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ClienteFabricante> buscarClienteFabricanteExiste(ClienteFabricante clienteFabricante){
		List<ClienteFabricante> listClienteCultura = new ArrayList<ClienteFabricante>(); 
		
		try {
			
			Query query = manager.createNamedQuery("ClienteFabricante.existe");
			query.setParameter("idPessoa", clienteFabricante.getCliente().getIdPessoa());
			query.setParameter("idFabricante", clienteFabricante.getFabricante().getIdFabricante());
			listClienteCultura = query.getResultList();
			
		} catch (NoResultException e) {		
		}		
		return listClienteCultura;
	}
	
	@SuppressWarnings("unchecked")
	public List<ClienteFabricante> buscarClienteFabricante(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaClienteFabricante = session.createCriteria(ClienteFabricante.class, "cf");	
		criteriaClienteFabricante.createCriteria("cf.cliente", "c")		
			.add(Restrictions.eq("idPessoa", cliente.getIdPessoa()));
		
		criteriaClienteFabricante.createCriteria("cf.fabricante", "f");
				
		return criteriaClienteFabricante.list();
	}
}
