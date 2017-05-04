package com.agrofauna.tratorweb.util.relarotio;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.agrofauna.tratorweb.model.Pedido;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

public class TodosRelatorio implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;
	
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	public void emitirComprasConvencional(Pedido pedido){	
		Map<String, Object> parametros = new HashMap<>();		
		parametros.put("id_pedido", pedido.getIdPedido());
		parametros.put("sub_relatorio", "/relatorios/RelatorioPedidosImpressoSub.jasper");
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioPedidoImpresso.jasper",
				this.response, parametros, "RELTRAB - Pedido de Compra.pdf");
		
		Session session = manager.unwrap(Session.class);			
		session.doWork(executor);
					
		if (executor.isRelatorioGerado()) {				
			FacesUtil.addInfoMessage("Relatório Gerado com sucesso.");
			facesContext.responseComplete();				
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}
	
	public void emitirComprasCompreGanhe(Pedido pedido){	
		Map<String, Object> parametros = new HashMap<>();		
		parametros.put("id_pedido", pedido.getIdPedido());
		parametros.put("sub_relatorio", "/relatorios/RelatorioPedidoImpressoCompreGanheSub.jasper");
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioPedidoImpressoCompreGanhe.jasper",
				this.response, parametros, "RELTRAB - Pedido Compre Ganhe.pdf");
		
		Session session = manager.unwrap(Session.class);			
		session.doWork(executor);
					
		if (executor.isRelatorioGerado()) {				
			FacesUtil.addInfoMessage("Relatório Gerado com sucesso.");
			facesContext.responseComplete();				
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}
	
	public void emitirComprasEncomenda(Pedido pedido){	
		Map<String, Object> parametros = new HashMap<>();		
		parametros.put("id_pedido", pedido.getIdPedido());
		parametros.put("sub_relatorio", "/relatorios/RelatorioPedidoImpressoEncomendaSub.jasper");
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioPedidoImpressoEncomenda.jasper",
				this.response, parametros, "RELTRAB - Solicitação de Encomenda.pdf");
		
		Session session = manager.unwrap(Session.class);			
		session.doWork(executor);
					
		if (executor.isRelatorioGerado()) {				
			FacesUtil.addInfoMessage("Relatório Gerado com sucesso.");
			facesContext.responseComplete();				
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}
}
