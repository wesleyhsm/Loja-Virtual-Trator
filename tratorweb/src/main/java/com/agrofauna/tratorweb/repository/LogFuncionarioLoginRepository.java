package com.agrofauna.tratorweb.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Funcionario;
import com.agrofauna.tratorweb.model.LogFuncionarioLogin;

public class LogFuncionarioLoginRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public void salvarLogFuncionarioLogin(LogFuncionarioLogin logFuncionarioLogin){
		try{
			manager.merge(logFuncionarioLogin);			
		}catch(Exception e){						
			e.printStackTrace();
		}	
	}

	public LogFuncionarioLogin buscarUltimoLog(Funcionario funcionario){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaUltimoLog = session.createCriteria(LogFuncionarioLogin.class, "lf")
				.setProjection(Projections.projectionList().add(Projections.max("lf.idLogFuncionarioLogin")));
		
		criteriaUltimoLog.createCriteria("lf.funcionario", "f")
				.add(Restrictions.eq("f.idPessoa", funcionario.getIdPessoa()));
				
		Criteria criteriaUltimoLog2 = session.createCriteria(LogFuncionarioLogin.class, "lf2")
				.add(Restrictions.eq("lf2.idLogFuncionarioLogin", (long) criteriaUltimoLog.uniqueResult()));
		
		return (LogFuncionarioLogin) criteriaUltimoLog2.uniqueResult();
	}
}
