package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.HorarioAtendimento;

public class HorarioRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")	
	private EntityManager manager;
	
	@Transactional
	public boolean removerHorarioAtendimento(HorarioAtendimento horarioAtendimento){
		try{
			manager.remove(horarioAtendimento);
			manager.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
		
	@SuppressWarnings("unchecked")
	public List<HorarioAtendimento> buscarHorarioAtendimento(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaHorarioAtendimento = session.createCriteria(HorarioAtendimento.class, "h");		
		criteriaHorarioAtendimento.createCriteria("h.cliente", "c")		
				.add(Restrictions.eq("c.idPessoa", cliente.getIdPessoa()));
		
		return criteriaHorarioAtendimento.list();		
	}
}
