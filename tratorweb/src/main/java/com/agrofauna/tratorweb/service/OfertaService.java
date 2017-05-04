package com.agrofauna.tratorweb.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.CategoriaEmail;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.repository.OfertaRepository;

public class OfertaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject 
	private OfertaRepository ofertaRepository;
		 
	@Transactional
	public boolean salvar(Email email){
		boolean resultado = false;
		
		if (ofertaRepository.buscarEmail(email).isEmpty()) {			
			System.out.println("teste de email igual a null");
			CategoriaEmail categoriaEmail = new CategoriaEmail();
			categoriaEmail.setIdCategoriaEmail(1L); //ajustar servidor de email					
			email.setSnStatus(1);//status ativo
			email.setDtCricao(new Date());
			email.setCategoriaEmail(categoriaEmail);				
						
			ofertaRepository.salvarEmail(email);
			
			resultado = true;
		}
		
		return resultado;
	}
}
