package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.filtro.EncomendaNovoProduto;
import com.agrofauna.tratorweb.mail.MailConfigNormal;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.Telefone;
import com.agrofauna.tratorweb.repository.EmailRepository;
import com.agrofauna.tratorweb.repository.TelefoneRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@SessionScoped
public class EncomendaNovoProdutoBean implements Serializable{
		
	private static final long serialVersionUID = 1L;

	@Inject
	private MailConfigNormal mailConfigNormal;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private EmailRepository emailRepository;
	
	@Inject
	private TelefoneRepository telefoneRepository;
	
	private EncomendaNovoProduto encomendaNovoProduto = new EncomendaNovoProduto();
	private List<EncomendaNovoProduto> listEncomendaNovoProdutos = new ArrayList<EncomendaNovoProduto>();
	
	public void FinalizarEncomenda(){		
		if(listEncomendaNovoProdutos.size() > 0){			
			List<Email> emails = emailRepository.buscarEmail(loginBean.getCliente());
			List<Telefone> telefones = telefoneRepository.buscarTelefone(loginBean.getCliente());			
			mailConfigNormal.montagemEmailEncomendaNovoProduto(loginBean.getCliente(), emails, telefones, listEncomendaNovoProdutos);
			listEncomendaNovoProdutos = new ArrayList<EncomendaNovoProduto>();
			FacesUtil.addInfoMessage("Enviada com sucesso.");
			
		}else{
			FacesUtil.addErrorMessage("Adicionar produto.");
		}
		
	}
	
	public void removerNovoProduto(EncomendaNovoProduto encomendaNovoProduto){
		listEncomendaNovoProdutos.remove(encomendaNovoProduto);
		FacesUtil.addInfoMessage("Removido com sucesso.");
	}
	
	public void adicionarNovoProduto(){		
		if(encomendaNovoProduto.getQtdProduto() > 0 && encomendaNovoProduto.getPrecoInicio() > 0 && encomendaNovoProduto.getPrecoFinal() >= encomendaNovoProduto.getPrecoInicio()){
			listEncomendaNovoProdutos.add(encomendaNovoProduto);
			encomendaNovoProduto = new EncomendaNovoProduto();
			FacesUtil.addInfoMessage("Produto salvo.");
			
		}else{
			FacesUtil.addErrorMessage("Preço e qtd invalido.");
		}
	}
	
	public List<EncomendaNovoProduto> getListEncomendaNovoProdutos() {
		return listEncomendaNovoProdutos;
	}

	public void setListEncomendaNovoProdutos(List<EncomendaNovoProduto> listEncomendaNovoProdutos) {
		this.listEncomendaNovoProdutos = listEncomendaNovoProdutos;
	}

	public EncomendaNovoProduto getEncomendaNovoProduto() {
		return encomendaNovoProduto;
	}

	public void setEncomendaNovoProduto(EncomendaNovoProduto encomendaNovoProduto) {
		this.encomendaNovoProduto = encomendaNovoProduto;
	}
}
