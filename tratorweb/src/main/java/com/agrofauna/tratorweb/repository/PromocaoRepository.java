package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.filtro.FiltroCampanhaProduto;
import com.agrofauna.tratorweb.model.CampanhaProduto;

public class PromocaoRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<CampanhaProduto> listaCampanhaProdutoPromocao(FiltroCampanhaProduto filtroCampanhaProduto){
		Criteria criteria = criaCriteriaParaFiltroPromocao(filtroCampanhaProduto);
		
		criteria.setFirstResult(filtroCampanhaProduto.getPrimeiroRegistro());
		criteria.setMaxResults(filtroCampanhaProduto.getQuantidadeRegistro());		
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(FiltroCampanhaProduto filtroCampanhaProduto){
		Criteria criteria = criaCriteriaParaFiltroPromocao(filtroCampanhaProduto);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaParaFiltroPromocao(FiltroCampanhaProduto filtroCampanhaProduto){
		Session session = manager.unwrap(Session.class);	
				
		//pega a campanha com maior prioridade
		int nrPrioridade = retornaCampanhaComMaiorPrioridade(filtroCampanhaProduto);
		
		//todos produtos em promoção
		Criteria criteriaProduto = session.createCriteria(CampanhaProduto.class, "cp");	
		criteriaProduto.createCriteria("cp.campanha", "ca")
			.add(Restrictions.eq("ca.nrPrioridade", nrPrioridade))
			.add(Restrictions.eq("ca.snPromocao", true))
			.add(Restrictions.le("ca.dtInicial", new Date()))
			.add(Restrictions.ge("ca.dtFinal", new Date()));
						
		criteriaProduto.createCriteria("cp.produto", "p")
			.add(Restrictions.eq("p.snDisponivelVenda", "S"))
			.add(Restrictions.eq("p.snStatus", 1));
						
		criteriaProduto.createCriteria("p.produtoEstoque", "e")
			.add(Restrictions.ge("e.nrEstoqueAtual", 1));
		
		criteriaProduto.createCriteria("ca.campanhaClientes", "cc");
		criteriaProduto.createCriteria("cc.cliente", "c")
			.add(Restrictions.eq("c.idPessoa", filtroCampanhaProduto.getCliente().getIdPessoa()));
		
		//pesquisar por nome do produto
		if(filtroCampanhaProduto.getDescricao()!=null && filtroCampanhaProduto.getDescricao().length() >= 2) {
			criteriaProduto.add(Restrictions.ilike("p.nmProduto", filtroCampanhaProduto.getDescricao().toLowerCase(), MatchMode.ANYWHERE));
		}		
		
		//pesquisa por tipo do produto
		if(filtroCampanhaProduto.getTipoConsulta()!=null && filtroCampanhaProduto.getTipoConsulta().length() >= 3){			
			criteriaProduto.createCriteria("p.produtoTipoProduto", "ptp");
			criteriaProduto.createCriteria("ptp.produtoTipo", "pt")
			.add(Restrictions.ilike("pt.nmProdutoTipo", filtroCampanhaProduto.getTipoConsulta().toLowerCase(), MatchMode.ANYWHERE));
		}
								
		//aqui é o combobox do filtro
		if(filtroCampanhaProduto.getOrdenacao() == 1){
			//ordena menor valor do produto
			criteriaProduto.addOrder(Order.asc("cp.vlPrecoCampanha"));
			
		}else if(filtroCampanhaProduto.getOrdenacao() == 2){
			//ordena maior valor porduto
			criteriaProduto.addOrder(Order.desc("cp.vlPrecoCampanha"));
			
		}else if(filtroCampanhaProduto.getOrdenacao() == 3){
			//ordena nome produto crescente
			criteriaProduto.addOrder(Order.asc("p.nmProduto"));
			
		}else if(filtroCampanhaProduto.getOrdenacao() == 4){
			//ordena nome produto decrescente
			criteriaProduto.addOrder(Order.desc("p.nmProduto"));
		}				
		
		return criteriaProduto;
	}
	
	public int retornaCampanhaComMaiorPrioridade(FiltroCampanhaProduto filtroCampanhaProduto){		
		try{
			return (Integer)  manager.createQuery("Select MAX(ca.nrPrioridade) FROM Campanha ca "
					+ "INNER JOIN ca.campanhaClientes cc "
					+ "INNER JOIN cc.cliente c "
					+ "WHERE ca.dtFinal>=:data AND c.idPessoa=:idPessoa group by ca.nrPrioridade")
					.setParameter("data", new Date())					
					.setParameter("idPessoa", filtroCampanhaProduto.getCliente().getIdPessoa() )
					.getSingleResult();					
			
		}catch(NoResultException e){
			return 0;
		}
	}
}
