package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import com.agrofauna.tratorweb.filtro.FiltroCampanhaProduto;
import com.agrofauna.tratorweb.filtro.FiltroPersonalizarProduto;
import com.agrofauna.tratorweb.filtro.FiltroPesquisaProdutoIndex;
import com.agrofauna.tratorweb.filtro.FiltroProduto;
import com.agrofauna.tratorweb.model.ConexaoBanco;
import com.agrofauna.tratorweb.model.Produto;

public class ProdutoRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public boolean salvarNovoProduto(Produto Produto){
		try{
			manager.merge(Produto);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> buscarProdutos(){
		List<Produto> listProdutos = new ArrayList<Produto>();
		try{
			Query query = manager.createNamedQuery("Produto.todos");						
			listProdutos= query.getResultList();			
			
		}catch(NoResultException nr){			
		}
		return listProdutos;
	}
		
	@SuppressWarnings("unchecked")
	public List<Produto> listaProduto(FiltroPesquisaProdutoIndex filtroPesquisaProdutoIndex){
		Criteria criteria = criaCriteriaParaFiltro(filtroPesquisaProdutoIndex);	
		
		criteria.setFirstResult(filtroPesquisaProdutoIndex.getPrimeiroRegistro());
		criteria.setMaxResults(filtroPesquisaProdutoIndex.getQuantidadeRegistro());		
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(FiltroPesquisaProdutoIndex filtroPesquisaProdutoIndex){
		Criteria criteria = criaCriteriaParaFiltro(filtroPesquisaProdutoIndex);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaParaFiltro(FiltroPesquisaProdutoIndex filtroPesquisaProdutoIndex){
		Session session = manager.unwrap(Session.class);		
				
		Criteria criteriaProduto = session.createCriteria(Produto.class, "p");		
		criteriaProduto.add( Restrictions.eq("p.snDisponivelVenda", "S"));		
		criteriaProduto.add( Restrictions.eq("p.snStatus", 1));
		
		criteriaProduto.createCriteria("p.produtoDadosTecnicos", "pdt");
		
		criteriaProduto.createCriteria("pdt.principioAtivo", "pa");
		
		if(filtroPesquisaProdutoIndex.getNomeProduto() != null && filtroPesquisaProdutoIndex.getNomeProduto().length() >= 1){
			criteriaProduto.add( Restrictions.ilike("p.nmProduto", filtroPesquisaProdutoIndex.getNomeProduto(), MatchMode.ANYWHERE));
		}
		
		if(filtroPesquisaProdutoIndex.getNomePrincipioAtivo() != null && filtroPesquisaProdutoIndex.getNomePrincipioAtivo().length() >= 1){
			criteriaProduto.add( Restrictions.ilike("pa.nmPrincipioAtivo", filtroPesquisaProdutoIndex.getNomePrincipioAtivo(), MatchMode.ANYWHERE));
		}
		
		if(filtroPesquisaProdutoIndex.getOrdenacao() == 2){
			criteriaProduto.addOrder(Order.desc("p.nmProduto"));
		
		}else if(filtroPesquisaProdutoIndex.getOrdenacao() == 3){
			criteriaProduto.addOrder(Order.asc("pa.nmPrincipioAtivo"));
			
		}else if(filtroPesquisaProdutoIndex.getOrdenacao() == 4){
			criteriaProduto.addOrder(Order.desc("pa.nmPrincipioAtivo"));
			
		}else{
			criteriaProduto.addOrder(Order.asc("p.nmProduto"));
		}
		
		criteriaProduto.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteriaProduto;
	}
	
	/*@SuppressWarnings("unchecked")
	@Transactional
	public List<Produto> listaProdutoCompreGanhe(FiltroProduto filtroProduto){
		Criteria criteria = criaCriteriaProdutoCompreGanhe(filtroProduto);	
		
		criteria.setFirstResult(filtroProduto.getPrimeiroRegistro());
		criteria.setMaxResults(filtroProduto.getQuantidadeRegistro());		
		
		return criteria.list();
	}
	
	public int quantidadeFiltradosProdutoCompreGanhe(FiltroProduto filtroProduto){
		Criteria criteria = criaCriteriaProdutoCompreGanhe(filtroProduto);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaProdutoCompreGanhe(FiltroProduto filtroProduto){
		Session session = manager.unwrap(Session.class);		
				
		Criteria criteriaCompreGanhe = session.createCriteria(Produto.class, "p")		
			.add( Restrictions.eq("p.snDisponivelVenda", "S"))		
			.add( Restrictions.eq("p.snStatus", 1))
			.add( Restrictions.eq("p.snCompreGanhe", true));		
		
		criteriaCompreGanhe.createCriteria("p.produtoEstoque", "e")
			.add(Restrictions.ge("nrEstoqueAtual", 1));
		
		if(filtroProduto.getDescricao() != null && filtroProduto.getDescricao().length() >= 1) {
			criteriaCompreGanhe.add(Restrictions.ilike("p.nmProduto", filtroProduto.getDescricao().toLowerCase(), MatchMode.ANYWHERE));
		}
		
		if(filtroProduto.getTipoConsulta()!=null && filtroProduto.getTipoConsulta().length() >= 1){			
			criteriaCompreGanhe.createCriteria("p.produtoTipoProduto", "ptp");
			criteriaCompreGanhe.createCriteria("ptp.produtoTipo", "pt")
			.add(Restrictions.ilike("pt.nmProdutoTipo", filtroProduto.getTipoConsulta().toLowerCase(), MatchMode.ANYWHERE));
		}
		
		//aqui é o combobox
		System.out.println("combo " + filtroProduto.getOrdenacao());
		if(filtroProduto.getOrdenacao() == 1){
			//ordena menor ponto
			criteriaCompreGanhe.addOrder(Order.asc("p.nrPontoCompreGanhe"));
			
		}else if(filtroProduto.getOrdenacao() == 2){
			//ordena maior ponto
			criteriaCompreGanhe.addOrder(Order.desc("p.nrPontoCompreGanhe"));
			
		}else if(filtroProduto.getOrdenacao() == 3){
			//ordena nome produto crescente
			criteriaCompreGanhe.addOrder(Order.asc("p.nmProduto"));
			
		}else if(filtroProduto.getOrdenacao() == 4){
			//ordena nome produto decrescente
			criteriaCompreGanhe.addOrder(Order.desc("p.nmProduto"));
		}
		
		criteriaCompreGanhe.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteriaCompreGanhe;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<Produto> listProdutoDetalhadoCompreGanhe(Produto produto){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(Produto.class, "p")
				.add(Restrictions.eq("snCompreGanhe", true))
				.add(Restrictions.eq("idProduto", produto.getIdProduto()));
			
		criteriaProduto.createCriteria("p.fabricante", "f");
				
		criteriaProduto.createCriteria("p.produtoDadosTecnicos", "d");
		
		criteriaProduto.createCriteria("d.principioAtivo", "pa");
		
		criteriaProduto.createCriteria("d.grupoQuimico", "g");
		
		criteriaProduto.createCriteria("p.produtoEstoque", "e")
			.add(Restrictions.ge("nrEstoqueAtual", 1));
				
		return criteriaProduto.list();
	}
	
	
	@SuppressWarnings("unchecked")	
	public List<Produto> listaProdutoMesmoPrincipioAtivoCompreGanhe(FiltroCampanhaProduto filtroCampanhaProduto){
		Criteria criteria = criaCriteriaProdutoMesmoPrincipioAtivoCompreGanhe(filtroCampanhaProduto);	
		
		criteria.setFirstResult(filtroCampanhaProduto.getPrimeiroRegistro());
		criteria.setMaxResults(filtroCampanhaProduto.getQuantidadeRegistro());		
		
		return criteria.list();
	}
	
	public int quantidadeFiltradosProdutoMesmoPrincipioAtivoCompreGanhe(FiltroCampanhaProduto filtroCampanhaProduto){
		Criteria criteria = criaCriteriaProdutoMesmoPrincipioAtivoCompreGanhe(filtroCampanhaProduto);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaProdutoMesmoPrincipioAtivoCompreGanhe(FiltroCampanhaProduto filtroCampanhaProduto){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(Produto.class, "p")
				.add(Restrictions.eq("p.snCompreGanhe", true))
				.add(Restrictions.ge("p.nrPontoCompreGanhe", 1));
								
		criteriaProduto.createCriteria("p.produtoDadosTecnicos", "d");
		
		criteriaProduto.createCriteria("d.principioAtivo", "pa")
		.add(Restrictions.eq("pa.idPrincipioAtivo", filtroCampanhaProduto.getPrincipioAtivo().getIdPrincipioAtivo()));
						
		criteriaProduto.createCriteria("p.produtoEstoque", "e")
			.add(Restrictions.ge("nrEstoqueAtual", 1));
				
		criteriaProduto.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteriaProduto;
	}
	
	public Produto produtoQuantidadeEstoque(long idProduto){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(Produto.class, "p")
				.add(Restrictions.eq("p.idProduto", idProduto))
				.add(Restrictions.eq("p.snCompreGanhe", true))
				.add(Restrictions.ge("p.nrPontoCompreGanhe", 1));								
		
		criteriaProduto.createCriteria("p.produtoArmazenamento", "d");
		
		return (Produto) criteriaProduto.uniqueResult();
	}
	
	public Produto produtoQuantidadeEstoque2(long idProduto){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(Produto.class, "p")
				.add(Restrictions.eq("p.idProduto", idProduto));								
		
		criteriaProduto.createCriteria("p.produtoArmazenamento", "d");
		
		return (Produto) criteriaProduto.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> buscarProdutosProNome(String nomeProduto){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(Produto.class, "p")
				.add(Restrictions.ilike("p.nmProduto", nomeProduto, MatchMode.ANYWHERE))
				.add( Restrictions.eq("p.snDisponivelVenda", "S"))		
				.add( Restrictions.eq("p.snStatus", 1))
				.addOrder(Order.asc("p.nmProduto"));
						
		criteriaProduto.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteriaProduto.list();
	}
	
	//consulta personalizar produto
	@SuppressWarnings("unchecked")	
	public List<Produto> listaProdutoPersonalizar(FiltroPersonalizarProduto filtroPersonalizarProduto){
		try{
			Criteria criteria = criaCriteriaProdutoPersonalizar(filtroPersonalizarProduto);	
			
			criteria.setFirstResult(filtroPersonalizarProduto.getPrimeiroRegistro());
			criteria.setMaxResults(filtroPersonalizarProduto.getQuantidadeRegistro());		
			
			return criteria.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public int quantidadeFiltradosProdutoPersonalizar(FiltroPersonalizarProduto filtroPersonalizarProduto){
		Criteria criteria = criaCriteriaProdutoPersonalizar(filtroPersonalizarProduto);
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criaCriteriaProdutoPersonalizar(FiltroPersonalizarProduto filtroPersonalizarProduto){
		Session session = manager.unwrap(Session.class);
		
		Criteria criteriaProduto = session.createCriteria(Produto.class, "p")
			.add( Restrictions.eq("p.snDisponivelVenda", "S"))		
			.add( Restrictions.eq("p.snStatus", 1));
								
		criteriaProduto.createCriteria("p.produtoDadosTecnicos", "d");
		
		criteriaProduto.createCriteria("d.principioAtivo", "pa");
				
		if(filtroPersonalizarProduto.getNomePesquisa() != null && filtroPersonalizarProduto.getNomePesquisa().length() >= 1 && filtroPersonalizarProduto.getTipoPesquisa() == 1){
			criteriaProduto.add( Restrictions.ilike("p.nmProduto", filtroPersonalizarProduto.getNomePesquisa(), MatchMode.ANYWHERE));
		}
				
		if(filtroPersonalizarProduto.getNomePesquisa() != null && filtroPersonalizarProduto.getNomePesquisa().length() >= 1 && filtroPersonalizarProduto.getTipoPesquisa() == 2){
			criteriaProduto.add( Restrictions.ilike("pa.nmPrincipioAtivo", filtroPersonalizarProduto.getNomePesquisa(), MatchMode.ANYWHERE));
		}		
						
		criteriaProduto.addOrder(Order.asc("p.nmProduto"));
		
		criteriaProduto.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteriaProduto;
	}
	
	public List<Produto> listaProdutoCompreGanheManual(FiltroProduto filtroProduto){
		List<Produto> produtos = new ArrayList<>();
		try{
			String queryFiltro = "";			
			int liberaQuery = 0;
			if(filtroProduto.getDescricao() != null  && filtroProduto.getDescricao().length() >= 1){
				queryFiltro = "p.nm_produto like ? and ";				
				liberaQuery = 1;
			}
			
			String queryOrdenacao = "";
			if(filtroProduto.getOrdenacao() == 1){
				//ordena menor valor do produto
				queryOrdenacao = "p.nr_ponto_compre_ganhe DESC";
				
			}else if(filtroProduto.getOrdenacao() == 2){
				//ordena maior valor porduto
				queryOrdenacao = "p.nr_ponto_compre_ganhe ASC";
				
			}else if(filtroProduto.getOrdenacao() == 4){
				//ordena nome produto decrescente
				queryOrdenacao = "p.nm_produto DESC";				
				
			}else {
				//ordena nome produto crescente
				queryOrdenacao = "p.nm_produto ASC";
			}
						
			String queryCampanhaProduto = "";			
			if(filtroProduto.getTipoConsulta() != null && filtroProduto.getTipoConsulta().length() >= 1){
				queryCampanhaProduto = "SELECT "+
											"p.id_produto, "+ 
											"p.nm_produto, "+
											"p.nr_ponto_compre_ganhe "+ 
										"FROM "+
											"db_agro_matriz.produto p "+
											"inner join db_agro_matriz.produto_tipo_produto ptp on ptp.id_produto=p.id_produto "+
										    "inner join db_agro_matriz.produto_tipo pt on pt.id_produto_tipo=ptp.id_produto_tipo "+
										    "inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										"WHERE "+
											"p.sn_disponivel_venda='S' and "+
											"pe.nr_estoque_atual>0 and "+
											queryFiltro +
										    "p.sn_compre_ganhe=1 and "+
										    "pt.nm_produto_tipo like '%" + filtroProduto.getTipoConsulta() +"%' "+
										"order by " + queryOrdenacao;				
			}else{
				queryCampanhaProduto = "SELECT "+
											"p.id_produto, "+ 
											"p.nm_produto, "+
											"p.nr_ponto_compre_ganhe "+ 
										"FROM "+
											"db_agro_matriz.produto p "+
											"inner join db_agro_matriz.produto_estoque pe on pe.id_produto=p.id_produto "+
										"WHERE "+
											"p.sn_disponivel_venda='S' and "+
											"pe.nr_estoque_atual>0 and "+
											queryFiltro +
										    "p.sn_compre_ganhe=1 "+
										"order by "+ queryOrdenacao;
			}
			
			Connection connCampanhaProduto = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtCampanhaProduto = connCampanhaProduto.prepareStatement(queryCampanhaProduto)) { 
				if(liberaQuery == 1){
					stmtCampanhaProduto.setString(1, "%"+filtroProduto.getDescricao()+"%");					
				}
				ResultSet rsCampanhaProduto = stmtCampanhaProduto.executeQuery();								
				while(rsCampanhaProduto.next()){
										
					Produto produto = new Produto();
					produto.setIdProduto(rsCampanhaProduto.getLong("id_produto"));
					produto.setNmProduto(rsCampanhaProduto.getString("nm_produto"));
					produto.setNrPontoCompreGanhe(rsCampanhaProduto.getInt("nr_ponto_compre_ganhe"));					
					
					produtos.add(produto);					
				}
				rsCampanhaProduto.close();
				stmtCampanhaProduto.close();
			}	
			connCampanhaProduto.close();
		}catch(SQLException sql){			
			sql.printStackTrace();
		}
		return produtos;
	}
}
