package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.model.CategoriaCliente;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.Endereco;
import com.agrofauna.tratorweb.model.Estado;
import com.agrofauna.tratorweb.model.Telefone;
import com.agrofauna.tratorweb.repository.CategoriaClienteRepository;
import com.agrofauna.tratorweb.repository.EstadoRepository;
import com.agrofauna.tratorweb.service.PessoaService;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;
import com.agrofauna.tratorweb.webservice.CepWebService;

@Named
@ViewScoped
public class PessoaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private CategoriaClienteRepository categoriaClienteRepository;
	
	@Inject
	private EstadoRepository estadoRepository;
	
	private Cliente cliente = new Cliente();
	private Endereco endereco = new Endereco();
	private Email email = new Email();
	private Telefone telefone = new Telefone();
	private Estado estado = new Estado();
	private CategoriaCliente categoriaCliente = new CategoriaCliente();
		
	private void limpar(){
		cliente = new Cliente();
		endereco.setNmCep("");
		endereco =  new Endereco();		
		email =  new Email(); 
		telefone = new Telefone();
		estado =  new Estado();
		categoriaCliente = new CategoriaCliente();
		
	}

	public void salvarCliente(){		
		if(endereco.getNmCep() != null && endereco.getNmCep().length() == 9){
			if(pessoaService.salvarPreCadastro(cliente, endereco, email, telefone, estado, categoriaCliente)){
				limpar();
				FacesUtil.addInfoMessage("Cadastrado com sucesso.");
			}
		}else{
			FacesUtil.addInfoMessage("CEP: Não pode estar em branco.");
		}	
	}
	
	public void encontraCEP() {        
		if(endereco.getNmCep().length() == 9){
			CepWebService cepWebService = new CepWebService(endereco.getNmCep());
 
	        if (cepWebService.getResultado() == 1) {            
	            endereco.setNmRua(cepWebService.getLogradouro());
	            estado.setIdEstado(pessoaService.verificaEstado(cepWebService.getEstado()));
	            endereco.setNmCidade(cepWebService.getCidade());
	            endereco.setNmBairro(cepWebService.getBairro());
	            
	        } else {
	        	FacesUtil.addErrorMessage("Servidor não está respondendo, fazer cadastro manual.");            
	        }
	        
		} else {
        	FacesUtil.addErrorMessage("CEP inválido, preencha corretamente.");            
        }   
    }
		
	public CategoriaCliente getCategoriaCliente() {
		return categoriaCliente;
	}

	public List<CategoriaCliente> carregaListCategoriaCliente(){
		return categoriaClienteRepository.buscarCategoriaCliente();
	}
	
	public List<Estado> carregaListEstado(){
		return estadoRepository.buscarEstado();
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public Email getEmail() {
		return email;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void setCategoriaCliente(CategoriaCliente categoriaCliente) {
		this.categoriaCliente = categoriaCliente;
	}
}