package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.filtro.FiltroAlterarSenha;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Endereco;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.Estado;
import com.agrofauna.tratorweb.model.Login;
import com.agrofauna.tratorweb.model.Suframa;
import com.agrofauna.tratorweb.model.Telefone;
import com.agrofauna.tratorweb.repository.EmailRepository;
import com.agrofauna.tratorweb.repository.EnderecoRepository;
import com.agrofauna.tratorweb.repository.EstadoRepository;
import com.agrofauna.tratorweb.repository.LoginRepository;
import com.agrofauna.tratorweb.repository.SuframaRepository;
import com.agrofauna.tratorweb.repository.TelefoneRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;

@Named
@ViewScoped //trocar por request
public class MeuCadastroBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private EnderecoRepository enderecoRepository;
	
	@Inject
	private EstadoRepository estadoRepository;
	
	@Inject
	private EmailRepository emailRepository;
	
	@Inject
	private TelefoneRepository telefoneRepository;
	
	@Inject
	private SuframaRepository suframaRepository;
		
	@Inject
	private LoginRepository loginRepository;
	
	private Cliente cliente = new Cliente();
	private Endereco endereco = new Endereco();
	private Suframa suframa = new Suframa();
	private List<Email> listEmail = new ArrayList<Email>();
	private List<Telefone> listTelefones = new ArrayList<Telefone>();
	private Estado estado = new Estado(); 	
	private Telefone telefone = new Telefone();
	private Email email = new Email();
	private FiltroAlterarSenha filtroAlterarSenha = new FiltroAlterarSenha();
	
	public void inicializar(){
		this.cliente = loginBean.getCliente();
		this.endereco = enderecoRepository.buscarEndereco(loginBean.getCliente());
		this.estado = estadoRepository.buscarEstadoCliente(loginBean.getCliente());		
		this.listEmail = emailRepository.buscarEmail(loginBean.getCliente());
		this.listTelefones = telefoneRepository.buscarTelefone(loginBean.getCliente());		
		Suframa suframa = suframaRepository.buscarSuframa(loginBean.getCliente());
		
		if(suframa != null){
			this.suframa = suframa;
		}		
	}
	
	public void removerEmail(Email email){
		if(listEmail.size() >= 2){
			emailRepository.removerEmail(email);
			FacesUtil.addInfoMessage("E-mail removido com sucesso.");
		}else{
			FacesUtil.addErrorMessage("E-mail não pode ser removido.");
		}	
	}
	
	public void alterarSenha(){		
		if(loginBean.getLogin().getNmSenha().equals(filtroAlterarSenha.getSenhaAtual()) && filtroAlterarSenha.getNovaSenha1().equals(filtroAlterarSenha.getNovaSenha2())){
			loginBean.getLogin().setNmSenha(filtroAlterarSenha.getNovaSenha1());			
			loginBean.getLogin().setPessoa(cliente);			
						
			loginRepository.salvarLogin(loginBean.getLogin());
			filtroAlterarSenha = new FiltroAlterarSenha();
			FacesUtil.addInfoMessage("Senha alterada com sucesso. ");
		}else{
			FacesUtil.addErrorMessage("Dados invalidos.");
		}
	}
	
	public void removerTelefone(Telefone telefone){
		if(listTelefones.size() >= 2){
			telefoneRepository.removerTelefone(telefone);
			FacesUtil.addInfoMessage("Telefone removido com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Telefone não pode ser removido.");
		}	
	}
	
	public void salvarNovoEmail(){
		email.setDtCricao(new Date());
		email.setSnStatus(1);
		email.setPessoa(cliente);
						
		if(emailRepository.buscarEmailExiste(email).isEmpty()){
			emailRepository.salvarNovoEmail(email);
			email = new Email();
			FacesUtil.addInfoMessage("Novo e-mail salvo com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Novo e-mail já existe");			
		}	
	}
	
	public void salvarNovoTelefone(){
		telefone.setDtCriacao(new Date());
		telefone.setSnStatus(1);		
		telefone.setPessoa(cliente);
				
		if(telefoneRepository.buscarTelefoneExiste(telefone).isEmpty()){		
			telefoneRepository.salvarNovoTelefone(telefone);
			telefone = new Telefone();
			FacesUtil.addInfoMessage("Novo telefone salvo com sucesso.");
		}else{
			FacesUtil.addErrorMessage("Novo telefone já existe");			
		}	
	}
	
	public void editarEmail(Email mail){
		this.email = mail;
	} 
	
	public void editarTelefone(Telefone fone){
		this.telefone= fone;
	} 
	
	public List<Estado> carregaListEstado(){
		return estadoRepository.buscarEstado();
	}
			
	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Suframa getSuframa() {
		return suframa;
	}

	public void setSuframa(Suframa suframa) {
		this.suframa = suframa;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public EnderecoRepository getEnderecoRepository() {
		return enderecoRepository;
	}

	public void setEnderecoRepository(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Email> getListEmail() {
		return listEmail;
	}

	public void setListEmail(List<Email> listEmail) {
		this.listEmail = listEmail;
	}

	public List<Telefone> getListTelefones() {
		return listTelefones;
	}

	public void setListTelefones(List<Telefone> listTelefones) {
		this.listTelefones = listTelefones;
	}

	public FiltroAlterarSenha getFiltroAlterarSenha() {
		return filtroAlterarSenha;
	}

	public void setFiltroAlterarSenha(FiltroAlterarSenha filtroAlterarSenha) {
		this.filtroAlterarSenha = filtroAlterarSenha;
	}
}
