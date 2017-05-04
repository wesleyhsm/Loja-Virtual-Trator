package com.agrofauna.tratorweb.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.agrofauna.tratorweb.filtro.RecuperarSenha;
import com.agrofauna.tratorweb.mail.MailConfigNormal;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.Login;
import com.agrofauna.tratorweb.model.Telefone;
import com.agrofauna.tratorweb.repository.EmailRepository;
import com.agrofauna.tratorweb.repository.LoginRepository;
import com.agrofauna.tratorweb.repository.PessoaRepository;
import com.agrofauna.tratorweb.repository.TelefoneRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

public class RecuperarSenhaService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaRepository pessoaRepository;
	
	@Inject
	private EmailRepository emailRepository;
	
	@Inject
	private MailConfigNormal mailConfigNormal;
		
	@Inject
	private TelefoneRepository telefoneRepository;
	
	@Inject
	private LoginRepository loginRepository;
	
	public void recuprarSenha(RecuperarSenha recuperarSenha){
		try{
			Cliente cliente = pessoaRepository.recuperarSenhaCliente(recuperarSenha); 
			List<Email> emails = emailRepository.buscarEmail(cliente);
			List<Telefone> telefones = telefoneRepository.buscarTelefone(cliente);
			Login login = loginRepository.buscarLogin(cliente);
			
			if(cliente == null || cliente.getIdPessoa() <= 0){
				FacesUtil.addErrorMessage("Dados inválidos, Preencher corretamente.");
				
			}else{			
				mailConfigNormal.montagemEmailRecuperarSenha(cliente, emails, telefones, login);
				mailConfigNormal.montagemEmailRecuperarSenhaAdmin(cliente, emails, telefones);
				FacesUtil.addInfoMessage("E-mail enviado com sucesso.");
			}
		}catch(Exception e){			
			e.printStackTrace();
		}
	}
}
