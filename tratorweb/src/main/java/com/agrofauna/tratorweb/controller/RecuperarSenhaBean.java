package com.agrofauna.tratorweb.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.filtro.RecuperarSenha;
import com.agrofauna.tratorweb.service.RecuperarSenhaService;

@Named
@RequestScoped
public class RecuperarSenhaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private RecuperarSenhaService recuperarSenhaService;	
	private RecuperarSenha recuperarSenha = new RecuperarSenha();
	
	public void recuprarSenha(){
		recuperarSenhaService.recuprarSenha(recuperarSenha);
	}

	public RecuperarSenha getRecuperarSenha() {
		return recuperarSenha;
	}

	public void setRecuperarSenha(RecuperarSenha recuperarSenha) {
		this.recuperarSenha = recuperarSenha;
	}
}
