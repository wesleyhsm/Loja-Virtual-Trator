package com.agrofauna.tratorweb.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.service.OfertaService;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@RequestScoped
public class OfertaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private OfertaService ofertaService;
	private Email email;
			
	public OfertaBean(){		
		limpaEmail();
	}
	
	private void limpaEmail(){
		email = new Email();
	}
	
	public void ofertaEmailSalvar(){
		if(ofertaService.salvar(email)){		
			limpaEmail();		
			FacesUtil.addInfoMessage("E-mail salvo com sucesso.");
		}else{
			FacesUtil.addErrorMessage("E-mail já existe");
		}
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}
}
