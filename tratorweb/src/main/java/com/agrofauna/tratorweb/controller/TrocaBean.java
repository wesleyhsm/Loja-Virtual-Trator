package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.agrofauna.tratorweb.filtro.FiltroCampanhaProduto;
import com.agrofauna.tratorweb.model.CampanhaProduto;
import com.agrofauna.tratorweb.repository.CampanhaProdutoRepository;

@Named
@ViewScoped
public class TrocaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private CampanhaProdutoRepository CampanhaProdutoRepository;
			
	@Inject
	private LoginBean loginBean;
	
	private LazyDataModel<CampanhaProduto> model;
	private FiltroCampanhaProduto filtroCampanhaProduto = new FiltroCampanhaProduto();
	private CampanhaProduto campanhaProduto = new CampanhaProduto();
	
	public TrocaBean(){
		/*model = new LazyDataModel<CampanhaProduto>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<CampanhaProduto> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				filtroCampanhaProduto.setCliente(loginBean.getCliente());
				filtroCampanhaProduto.setPrimeiroRegistro(first);
				filtroCampanhaProduto.setQuantidadeRegistro(pageSize);
																
				setRowCount(CampanhaProdutoRepository.quantidadeFiltradosTroca(filtroCampanhaProduto));				
				return CampanhaProdutoRepository.listaCampanhaProdutoTroca(filtroCampanhaProduto);				
			}
		};*/
	}
	
	public void setTipoProduto(String tipoProduto){
		filtroCampanhaProduto.setTipoConsulta(tipoProduto);
	}
	
	public CampanhaProduto getCampanhaProduto() {
		return campanhaProduto;
	}
		
	public void setCampanhaProduto(CampanhaProduto campanhaProduto) {
		this.campanhaProduto = campanhaProduto;
	}	
		
	public FiltroCampanhaProduto getFiltroCampanhaProduto() {
		return filtroCampanhaProduto;
	}

	public void setFiltroCampanhaProduto(FiltroCampanhaProduto filtroCampanhaProduto) {
		this.filtroCampanhaProduto = filtroCampanhaProduto;
	}
		
	public LazyDataModel<CampanhaProduto> getModel() {
		return model;
	}
}
