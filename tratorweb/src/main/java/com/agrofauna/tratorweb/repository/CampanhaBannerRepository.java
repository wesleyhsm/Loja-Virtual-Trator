package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agrofauna.tratorweb.model.CampanhaImagem;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.service.CalcularPrecoProduto;

public class CampanhaBannerRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CalcularPrecoProduto calcularPrecoProduto;
	
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<CampanhaImagem> buscarTodasImagensCampanha(Cliente cliente){
		Session session = manager.unwrap(Session.class);
		
		//pega todos os banner disponiveis e ativo
		Criteria criteriaCampanhaImagem = session.createCriteria(CampanhaImagem.class, "ci")
			.add(Restrictions.eq("ci.snStatus", true));		
		List<CampanhaImagem> listCampanhaImagem = criteriaCampanhaImagem.list();
		
		for(int cont = 0; cont < listCampanhaImagem.size(); cont++){			
			CampanhaProduto campanhaProduto = new CampanhaProduto();
			
			//verifica qual o tipo da campanha se promoção ou encomenda, e verifica se o produto do banner esta na campanha do
			//cliente caso sim mostrar o banner, caso não remove o banner da lista.
			if(listCampanhaImagem.get(cont).getNrIdProduto() > 0 && listCampanhaImagem.get(cont).getNmTipoImagem().equalsIgnoreCase("promocao")){				
				int nrPrioridade = retornaCampanhaComMaiorPrioridade(cliente);				
				
				try{
					Criteria criteriaProduto = session.createCriteria(CampanhaProduto.class, "cp");	
					
					criteriaProduto.createCriteria("cp.campanha", "ca")
						.add(Restrictions.eq("nrPrioridade", nrPrioridade))
						.add(Restrictions.le("dtInicial", new Date()))
						.add(Restrictions.ge("dtFinal", new Date()))
						.add(Restrictions.eq("ca.snPromocao", true));		
						
					criteriaProduto.createCriteria("cp.produto", "p")
						.add(Restrictions.eq("snDisponivelVenda", "S"))
						.add(Restrictions.eq("snStatus", 1))
						.add(Restrictions.eq("idProduto", listCampanhaImagem.get(cont).getNrIdProduto()));
					
					criteriaProduto.createCriteria("ca.campanhaClientes", "cc");
					criteriaProduto.createCriteria("cc.cliente", "c")
						.add(Restrictions.eq("idPessoa", cliente.getIdPessoa()));
					
					campanhaProduto = (CampanhaProduto) criteriaProduto.uniqueResult();
				}catch(Exception e){
					e.printStackTrace();
					campanhaProduto = null;
				}	
				
				if(campanhaProduto == null){					
					listCampanhaImagem.remove(cont);
				}else{					
					//campanhaProduto = calcularPrecoProduto.calculaPrecoProdutoNormal(campanhaProduto);
					//listCampanhaImagem.get(cont).setPrecoProduto(campanhaProduto.getVlPrecoCampanha());
					//listCampanhaImagem.get(cont).setIdCampanhaProduto(campanhaProduto.getIdProdutoCampanha());
				}
				
			}else if(listCampanhaImagem.get(cont).getNrIdProduto() > 0 && listCampanhaImagem.get(cont).getNmTipoImagem().equalsIgnoreCase("encomenda")){
				int nrPrioridade = retornaCampanhaComMaiorPrioridadeEncomenda(cliente);
				
				try{
					Criteria criteriaProduto = session.createCriteria(CampanhaProduto.class, "cp");
					
					criteriaProduto.createCriteria("cp.campanha", "ca")
						.add(Restrictions.eq("nrPrioridade", nrPrioridade))
						.add(Restrictions.le("dtInicial", new Date()))
						.add(Restrictions.ge("dtFinal", new Date()));
							
					criteriaProduto.createCriteria("cp.produto", "p")
						.add(Restrictions.eq("snDisponivelVenda", "S"))
						.add(Restrictions.eq("snStatus", 1))
						.add(Restrictions.eq("idProduto", listCampanhaImagem.get(cont).getNrIdProduto()));
					
					criteriaProduto.createCriteria("ca.campanhaClientes", "cc");
					criteriaProduto.createCriteria("cc.cliente", "c")
						.add(Restrictions.eq("idPessoa", cliente.getIdPessoa()));
					
					campanhaProduto = (CampanhaProduto) criteriaProduto.uniqueResult();
				}catch(Exception e){
					e.printStackTrace();
					campanhaProduto = null;
				}	
				
				if(campanhaProduto == null){					
					listCampanhaImagem.remove(cont);
				}else{					
					listCampanhaImagem.get(cont).setPrecoProduto(campanhaProduto.getVlPrecoCampanha() / campanhaProduto.getNrClassificaPrecoEncomenda());
					listCampanhaImagem.get(cont).setIdCampanhaProduto(campanhaProduto.getIdProdutoCampanha());
				}
			}
		}
		
		return listCampanhaImagem;
	}
	
	public int retornaCampanhaComMaiorPrioridade(Cliente cliente){		
		try{
			return (Integer)  manager.createQuery("Select MAX(ca.nrPrioridade) FROM Campanha ca "
					+ "INNER JOIN ca.campanhaClientes cc "
					+ "INNER JOIN cc.cliente c "
					+ "WHERE ca.dtInicial<=:data1 AND ca.dtFinal>=:data2 AND c.idPessoa=:idPessoa group by ca.nrPrioridade")
					.setParameter("data1", new Date())
					.setParameter("data2", new Date())
					.setParameter("idPessoa", cliente.getIdPessoa() )
					.getSingleResult();					
			
		}catch(NoResultException e){
			return 0;
		}
	}
	
	public int retornaCampanhaComMaiorPrioridadeEncomenda(Cliente cliente){		
		
		try{
			return (Integer)  manager.createQuery("Select MAX(ca.nrPrioridade) FROM Campanha ca "
					+ "INNER JOIN ca.campanhaClientes cc "
					+ "INNER JOIN cc.cliente c "					
					+ "WHERE ca.snEncomenda=1 AND ca.dtInicial<=:data1 AND ca.dtFinal>=:data2 AND c.idPessoa=:idPessoa group by ca.nrPrioridade")
					.setParameter("data1", new Date())
					.setParameter("data2", new Date())
					.setParameter("idPessoa", cliente.getIdPessoa() )
					.getSingleResult();					
			
		}catch(NoResultException e){
			return 0;
		}
	}
}
