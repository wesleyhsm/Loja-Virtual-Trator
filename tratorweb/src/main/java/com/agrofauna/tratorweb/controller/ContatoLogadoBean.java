package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.mail.MailConfigNormal;
import com.agrofauna.tratorweb.model.ContatoLogado;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.Setor;
import com.agrofauna.tratorweb.model.Telefone;
import com.agrofauna.tratorweb.repository.ContatoLogadoRepository;
import com.agrofauna.tratorweb.repository.EmailRepository;
import com.agrofauna.tratorweb.repository.SetorRepository;
import com.agrofauna.tratorweb.repository.TelefoneRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ContatoLogadoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private SetorRepository setorRepository;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private ContatoLogadoRepository contatoLogadoRepository;
	
	@Inject
	private MailConfigNormal mailConfigNormal;
	
	@Inject
	private EmailRepository emailRepository;
	
	@Inject
	private TelefoneRepository telefoneRepository;
	
	private Setor setor = new Setor();
	private ContatoLogado contatoLogado = new ContatoLogado(); 
	
	public void salvarContatologado(){		
		contatoLogado.setCliente(loginBean.getCliente());
		setor = setorRepository.buscarSetor2(setor);
		contatoLogado.setSetor(setor);
		contatoLogado.setDtCriacao(new Date());
		contatoLogado.setDtAlteracao(new Date());
		contatoLogado.setSnStatus(1);
		contatoLogado.setNmDescricaoFuncionario("");
		
		if(contatoLogadoRepository.salvarContatoLogado(contatoLogado)){
			List<Email> emails = emailRepository.buscarEmail(loginBean.getCliente());
			List<Telefone> telefones = telefoneRepository.buscarTelefone(loginBean.getCliente());
			mailConfigNormal.montagemEmailContatoLogado(contatoLogado, emails, telefones);
			contatoLogado = new ContatoLogado();
			FacesUtil.addInfoMessage("Contato salvo com sucesso.");
		}else
			FacesUtil.addErrorMessage("Erro preencher os campos corretamente.");
	}
	
	public List<Setor> buscarSetor(){
		return setorRepository.buscarSetor();
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public ContatoLogado getContatoLogado() {
		return contatoLogado;
	}

	public void setContatoLogado(ContatoLogado contatoLogado) {
		this.contatoLogado = contatoLogado;
	}
}
