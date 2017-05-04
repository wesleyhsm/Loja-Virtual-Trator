package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.agrofauna.tratorweb.filtro.FiltroPedido;
import com.agrofauna.tratorweb.model.Pedido;
import com.agrofauna.tratorweb.repository.PedidoRepository;
import com.agrofauna.tratorweb.util.relarotio.TodosRelatorio;

@Named
@ViewScoped
public class MinhasComprasConvencionalBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
			
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private TodosRelatorio todosRelatorio;
	
	private FiltroPedido filtroPedido = new FiltroPedido();
	private LazyDataModel<Pedido> model;
	
	public MinhasComprasConvencionalBean(){
		model = new LazyDataModel<Pedido>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Pedido> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {				
											
				filtroPedido.setCliente(loginBean.getCliente());
				filtroPedido.setPrimeiroRegistro(first);
				filtroPedido.setQuantidadeRegistro(pageSize);
																
				setRowCount(pedidoRepository.quantidadeFiltradosPedidoConvencinal(filtroPedido));	
				
				return pedidoRepository.listaPedidoConvencinal(filtroPedido);
			}
		};	
	}
	
	public void downloadPedido(Pedido pedido){
		todosRelatorio.emitirComprasConvencional(pedido);
	}
	
	public FiltroPedido getFiltroPedido() {
		return filtroPedido;
	}

	public void setFiltroPedido(FiltroPedido filtroPedido) {
		this.filtroPedido = filtroPedido;
	}

	public LazyDataModel<Pedido> getModel() {
		return model;
	}
}
