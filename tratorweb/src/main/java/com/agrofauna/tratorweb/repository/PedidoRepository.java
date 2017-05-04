package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.filtro.FiltroPedido;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Pedido;

public class PedidoRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarPedido(Pedido pedido){
		try{
			manager.merge(pedido);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Pedido> listaPedidoConvencinal(FiltroPedido filtroPedido){
		Criteria criteria = criaCriteriaParaFiltroPedidoConvencional(filtroPedido);	
		
		criteria.setFirstResult(filtroPedido.getPrimeiroRegistro());
		criteria.setMaxResults(filtroPedido.getQuantidadeRegistro());		
		
		return criteria.list();
	}
	
	public int quantidadeFiltradosPedidoConvencinal(FiltroPedido filtroPedido){
		Criteria criteria = criaCriteriaParaFiltroPedidoConvencional(filtroPedido);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaParaFiltroPedidoConvencional(FiltroPedido filtroPedido){
		Session session = manager.unwrap(Session.class);		
				
		Criteria criteriaPedido = session.createCriteria(Pedido.class,"pe")					
			.add( Restrictions.eq("snStatus", 1))
			.add( Restrictions.ilike("nmTipoPedido", "COMUM", MatchMode.ANYWHERE))
			.addOrder(Order.desc("dtCriacao"));		
		
		criteriaPedido.createCriteria("pe.cliente", "c")
			.add(Restrictions.eq("idPessoa", filtroPedido.getCliente().getIdPessoa()));
		
		Long[] ids = {1L, 6L, 7L,  11L};
		//long[] ids = {1l};
		criteriaPedido.createCriteria("pe.pedidoStatus", "ps")
			.add(Restrictions.in("idPedidoStatus", ids));		
		
		return criteriaPedido;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Pedido> listaPedidoEncomenda(FiltroPedido filtroPedido){
		Criteria criteria = criaCriteriaParaFiltroPedidoEncomenda(filtroPedido);	
		
		criteria.setFirstResult(filtroPedido.getPrimeiroRegistro());
		criteria.setMaxResults(filtroPedido.getQuantidadeRegistro());		
		
		return criteria.list();
	}
	
	public int quantidadeFiltradosPedidoEncomenda(FiltroPedido filtroPedido){
		Criteria criteria = criaCriteriaParaFiltroPedidoEncomenda(filtroPedido);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaParaFiltroPedidoEncomenda(FiltroPedido filtroPedido){
		Session session = manager.unwrap(Session.class);		
				
		Criteria criteriaPedido = session.createCriteria(Pedido.class,"pe")					
			.add( Restrictions.eq("snStatus", 1))
			.add( Restrictions.ilike("nmTipoPedido", "ENCOMENDA", MatchMode.ANYWHERE))
			.addOrder(Order.desc("dtCriacao"));		
		
		criteriaPedido.createCriteria("pe.cliente", "c")
			.add(Restrictions.eq("idPessoa", filtroPedido.getCliente().getIdPessoa()));
		
		Long[] ids = {1L, 6L, 7L,  11L};
		//long[] ids = {1l};
		criteriaPedido.createCriteria("pe.pedidoStatus", "ps")
			.add(Restrictions.in("idPedidoStatus", ids));
		
		return criteriaPedido;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Pedido> listaPedidoCompreGanhe(FiltroPedido filtroPedido){
		Criteria criteria = criaCriteriaParaFiltroPedidoCompreGanhe(filtroPedido);	
		
		criteria.setFirstResult(filtroPedido.getPrimeiroRegistro());
		criteria.setMaxResults(filtroPedido.getQuantidadeRegistro());		
		
		return criteria.list();
	}
	
	public int quantidadeFiltradosPedidoCompreGanhe(FiltroPedido filtroPedido){
		Criteria criteria = criaCriteriaParaFiltroPedidoCompreGanhe(filtroPedido);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaParaFiltroPedidoCompreGanhe(FiltroPedido filtroPedido){
		Session session = manager.unwrap(Session.class);		
				
		Criteria criteriaPedido = session.createCriteria(Pedido.class,"pe")					
			.add( Restrictions.eq("snStatus", 1))
			.add( Restrictions.ilike("nmTipoPedido", "COMPRE GANHE", MatchMode.ANYWHERE))
			.addOrder(Order.desc("dtCriacao"));	
		
		criteriaPedido.createCriteria("pe.cliente", "c")
			.add(Restrictions.eq("idPessoa", filtroPedido.getCliente().getIdPessoa()));
				
		Long[] ids = {1L, 6L, 7L,  11L};
		//long[] ids = {1l};
		criteriaPedido.createCriteria("pe.pedidoStatus", "ps")
			.add(Restrictions.in("idPedidoStatus", ids));
		
		return criteriaPedido;
	}
	
	public Pedido pedidoDetalhado(Pedido pedido){
		Session session = manager.unwrap(Session.class);		
				
		Criteria criteriaPedido = session.createCriteria(Pedido.class,"pe")					
			.add( Restrictions.eq("snStatus", 1))
			.add( Restrictions.eq("idPedido", pedido.getIdPedido()));
				
		criteriaPedido.createCriteria("pe.cliente", "c");			
		
		criteriaPedido.createCriteria("pe.cfop", "cf");
		
		criteriaPedido.createCriteria("pe.tipoCobranca", "tc");
		
		criteriaPedido.createCriteria("pe.formaPagamento", "fp");
		
		criteriaPedido.createCriteria("pe.pedidoTipoFrete", "ptf");
						
		criteriaPedido.createCriteria("pe.pedidoStatus", "ps");
		
		return (Pedido) criteriaPedido.uniqueResult();
	}
	
	public Pedido buscarUltimoPedido(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaPedido = session.createCriteria(Pedido.class, "ped")
				.setProjection(Projections.projectionList().add(Projections.max("ped.idPedido")));
		
		criteriaPedido.createCriteria("ped.cliente", "c")
				.add(Restrictions.eq("c.idPessoa", cliente.getIdPessoa()));
				
		Criteria criteriaPedido2 = session.createCriteria(Pedido.class, "ped2")
				.add(Restrictions.eq("ped2.idPedido", (long) criteriaPedido.uniqueResult()));
		
		return (Pedido) criteriaPedido2.uniqueResult();
	}
}
