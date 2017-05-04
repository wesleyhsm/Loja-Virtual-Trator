package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Pedido;
import com.agrofauna.tratorweb.model.PedidoProduto;
import com.agrofauna.tratorweb.model.Produto;

public class PedidoProdutoRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<PedidoProduto> pedidoProdutoDetalhado(Pedido pedido){
		Session session = manager.unwrap(Session.class);		
				
		Criteria criteriaPedido = session.createCriteria(PedidoProduto.class,"pp");					
			
		criteriaPedido.createCriteria("pp.pedido", "pe")
			.add( Restrictions.eq("snStatus", 1))
			.add( Restrictions.eq("idPedido", pedido.getIdPedido()));
		
		criteriaPedido.createCriteria("pp.produto", "p");
		
		criteriaPedido.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//essa linha era usada antes dos pedidos ir para o reltrab ele fas a ligação do produto com a campanha
		//criteriaPedido.createCriteria("pp.campanhaProduto", "cp");				
		
		return criteriaPedido.list();
	}
	
	//REGRA
	//eu preciso de todos os produtos da ultima compra (CONVENCIONAL)
	//depois preciso verifica se esse produto esta em alguma campanha nova e pega o preço
	//sendo q a campanha tem q ser de maior prioridade
	//e se o produto repetir pega o de menor valor
	@SuppressWarnings("unchecked")
	public List<Produto> buscarTodosProdutosComprado(Cliente cliente){
		
		long idUltimoPedidoFaturado = retornaPedidoUltimaCompra(cliente); 
		
		Session session = manager.unwrap(Session.class);		
		Criteria criteriaPedidoProduto = session.createCriteria(Produto.class,"p");					
			
		criteriaPedidoProduto.createCriteria("p.campanhaProdutos", "cp");
		
		criteriaPedidoProduto.createCriteria("cp.pedidoProdutos", "pp");
		
		//pega o pedido ativo, do tipo convencional,  
		criteriaPedidoProduto.createCriteria("pp.pedido", "pe")
			.add(Restrictions.eq("idPedido", idUltimoPedidoFaturado))
			.add(Restrictions.eq("snStatus", 1))
			.add(Restrictions.ilike("nmTipoPedido", "COMUM", MatchMode.EXACT));			
		
		criteriaPedidoProduto.createCriteria("pe.cliente", "c")
			.add(Restrictions.eq("idPessoa", cliente.getIdPessoa()));
				
		//pega o pedido com status de faturado (codigo=1, sigla=S, nome=FATURADO) 
		criteriaPedidoProduto.createCriteria("pe.pedidoStatus", "ps")
			.add(Restrictions.eq("idPedidoStatus", 1l));
		
		return criteriaPedidoProduto.list();
	}
	
	//pega o id do ultimo pedido faturado
	public long retornaPedidoUltimaCompra(Cliente cliente){		
		try{
			return (Long)  manager.createQuery("Select MAX(p.idPedido) FROM Pedido p "					
					+ "INNER JOIN p.cliente c "
					+ "INNER JOIN p.pedidoStatus ps "
					+ "WHERE c.idPessoa=:idPessoa AND ps.idPedidoStatus=:idStatus")					
					.setParameter("idStatus", 1l)
					.setParameter("idPessoa", cliente.getIdPessoa())
					.getSingleResult();					
			
		}catch(NoResultException e){
			return 0;
		}
	}
	
	//pega campanha com maior prioridade
	public int retornaCampanhaComMaiorPrioridade(Cliente cliente){		
		try{
			return (Integer)  manager.createQuery("Select MAX(ca.nrPrioridade) FROM Campanha ca "
					+ "INNER JOIN ca.campanhaClientes cc "
					+ "INNER JOIN cc.cliente c "
					+ "WHERE ca.dtInicial<=:data1 AND ca.dtFinal>=:data2 AND c.idPessoa=:idPessoa group by ca.nrPrioridade")
					.setParameter("data1", new Date())
					.setParameter("data2", new Date())
					.setParameter("idPessoa", cliente.getIdPessoa())
					.getSingleResult();					
			
		}catch(NoResultException e){
			return 0;
		}
	}
	
	public List<CampanhaProduto> listarProtudosUltimasCompras(Cliente cliente){
		List<CampanhaProduto> listaCampanhaProdutosUltimaCompra = new ArrayList<>();
		
		for(Produto produto: buscarTodosProdutosComprado(cliente) ){
			
			Session session = manager.unwrap(Session.class);	
			
			//pega a campanha com maior prioridade
			int nrPrioridade = retornaCampanhaComMaiorPrioridade(cliente);
								
			Criteria criteriaProduto = session.createCriteria(CampanhaProduto.class, "cp");	
			criteriaProduto.createCriteria("cp.campanha", "ca")
				.add(Restrictions.eq("nrPrioridade", nrPrioridade))
				.add(Restrictions.le("dtInicial", new Date()))
				.add(Restrictions.ge("dtFinal", new Date()));
								
			criteriaProduto.createCriteria("cp.produto", "p")
				.add(Restrictions.eq("idProduto", produto.getIdProduto()))
				.add(Restrictions.eq("snDisponivelVenda", "S"))
				.add(Restrictions.eq("snStatus", 1));
			
			criteriaProduto.createCriteria("p.produtoEstoque", "e")
				.add(Restrictions.ge("nrEstoqueAtual", 1));
						
			criteriaProduto.createCriteria("ca.campanhaClientes", "cc");
			criteriaProduto.createCriteria("cc.cliente", "c")
				.add(Restrictions.eq("idPessoa", cliente.getIdPessoa()));
			
			CampanhaProduto campanhaProduto = (CampanhaProduto) criteriaProduto.uniqueResult();
			if(campanhaProduto != null && campanhaProduto.getIdProdutoCampanha() > 0){
				listaCampanhaProdutosUltimaCompra.add((CampanhaProduto) criteriaProduto.uniqueResult());
			}	
		}
		
		return listaCampanhaProdutosUltimaCompra;
	}
}
