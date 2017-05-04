package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.mail.MailConfigNormal;
import com.agrofauna.tratorweb.model.Contato;
import com.agrofauna.tratorweb.repository.ContatoRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@RequestScoped
public class ContatoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContatoRepository contatoRepository;
	
	@Inject
	private MailConfigNormal mailConfigNormal;
	
	private Contato contato;
	
	public ContatoBean(){
		limpar();
	}
	
	private void limpar(){
		contato =  new Contato();
	}
	
	public void salvar(){
		
		contato.setDtCriacao(new Date());
		contato.setDtAlteracao(new Date());
		contato.setSnStatus(1);
		
		if(contatoRepository.salvarContato(contato)){
			mailConfigNormal.montagemEmailContato(contato);			
			limpar();
			FacesUtil.addInfoMessage("Contato salvo com sucesso.");
			
		}else{
			FacesUtil.addErrorMessage("Preemcher os campos corretamente");
		}
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}
}
