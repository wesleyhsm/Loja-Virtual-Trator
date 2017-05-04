package com.agrofauna.tratorweb.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.LogClienteLogin;

public class LogClienteLoginRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public void salvarLogClienteLogin(LogClienteLogin logClienteLogin){
		try{
			manager.merge(logClienteLogin);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public LogClienteLogin buscarUltimoLog(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaUltimoLog = session.createCriteria(LogClienteLogin.class, "lc")
				.setProjection(Projections.projectionList().add(Projections.max("lc.idLogClienteLogin")));
		
		criteriaUltimoLog.createCriteria("lc.cliente", "c")
				.add(Restrictions.eq("c.idPessoa", cliente.getIdPessoa()));
				
		Criteria criteriaUltimoLog2 = session.createCriteria(LogClienteLogin.class, "lc2")
				.add(Restrictions.eq("lc2.idLogClienteLogin", (long) criteriaUltimoLog.uniqueResult()));
		
		return (LogClienteLogin) criteriaUltimoLog2.uniqueResult();
	}
}
