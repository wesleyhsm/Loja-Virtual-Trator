package com.agrofauna.tratorweb.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.mail.MailConfigNormal;
import com.agrofauna.tratorweb.model.CategoriaCliente;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.ClienteCompreGanhe;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.Endereco;
import com.agrofauna.tratorweb.model.Estado;
import com.agrofauna.tratorweb.model.Login;
import com.agrofauna.tratorweb.model.Suframa;
import com.agrofauna.tratorweb.model.Telefone;
import com.agrofauna.tratorweb.repository.EstadoRepository;
import com.agrofauna.tratorweb.repository.OfertaRepository;
import com.agrofauna.tratorweb.repository.PessoaRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;
import com.agrofauna.tratorweb.validation.ValidaCnpjCpf;

public class PessoaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaRepository pessoaRepository;
	
	@Inject
	private OfertaRepository ofertaRepository;
		
	@Inject
	private EstadoRepository estadoRepository;
	
	@Inject
	private MailConfigNormal mailConfigNormal;
	
	@Transactional
	public boolean salvarPreCadastro(Cliente cliente, Endereco endereco, Email email, Telefone telefone, Estado estado, CategoriaCliente categoriaCliente){
		boolean resultado = false;						
		
		cliente.setNmCnpjCpf(cliente.getNmCnpjCpf().replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("/", ""));				
				
		//verifica se CPF ou CNPJ existe mo banco de dados e se é válido
		if(!pessoaRepository.buscarPessoa(cliente).isEmpty()){
			resultado = false;			
			FacesUtil.addErrorMessage("CNPJ / CPF já existe.");
			
		}else if(!ValidaCnpjCpf.isValidaCpfCnpj(cliente.getNmCnpjCpf())){
			resultado = false;			
			FacesUtil.addErrorMessage("CNPJ / CPF Inválido.");
			
		//verifica se e-mail ja existe no banco de dados	
		}else if(!ofertaRepository.buscarEmail(email).isEmpty()){			
			resultado = false;
			FacesUtil.addErrorMessage("E-mail já existe.");
		
		//realiza cadatro de um novo cliente	
		}else{
			
			Date dataAtual = new Date();
			
			Login login = new Login();			
			login.setDtCriacao(dataAtual);			
			login.setSnStatus(2);
			login.setNmLogin(cliente.getNmCnpjCpf());
			login.setNmSenha(cliente.getNmCnpjCpf());
			login.setPessoa(cliente);
			
			endereco.setNrTipoEndereco(1);
			endereco.setEstado(estado);
			endereco.setPessoa(cliente);			
			
			telefone.setDtCriacao(dataAtual);
			telefone.setNmContato(cliente.getNmContato());
			telefone.setSnStatus(1);
			telefone.setPessoa(cliente);			
			
			email.setDtCricao(dataAtual);
			email.setNmContato(cliente.getNmContato());
			email.setSnStatus(1);
			email.setPessoa(cliente);
			
			ClienteCompreGanhe clienteCompreGanhe = new ClienteCompreGanhe(); 
			clienteCompreGanhe.setNrQuantidadePontos(0);
			clienteCompreGanhe.setSnStatus(false);
			clienteCompreGanhe.setCliente(cliente);
			
			Suframa suframa = new Suframa();
			suframa.setDtCriacao(new Date());
			suframa.setNmSuframa("0");
			suframa.setSnIsencao(2);
			suframa.setSnRegular(2);
			suframa.setSnStatus(2);			
			suframa.setCliente(cliente);
			
			if(cliente.getNmCnpjCpf().length() <= 11)
				cliente.setNrTipo(1);
			else
				cliente.setNrTipo(2);
			
			cliente.setDtAlteracao(new Date());
			cliente.setDtCriacao(dataAtual);
			cliente.setSnStatus(1);
			cliente.setDtCriacao(dataAtual);			
			cliente.setCategoriaCliente(categoriaCliente);								
			cliente.addEmail(email);
			cliente.addEnderecoe(endereco);
			cliente.addTelefone(telefone);
			cliente.setLogin(login);			
			cliente.setClienteCompreGanhe(clienteCompreGanhe);
			cliente.setSuframas(suframa);
			
			pessoaRepository.salvarPessoa(cliente);
			
			mailConfigNormal.montagemEmailNovoCliente(cliente, email, telefone);
			
			resultado = true;			
		}		
		
		return resultado;
	}
	
	public long verificaEstado(String sigla){
		Estado estado = estadoRepository.buscarEstadoSigla(sigla);
		
		if(estado == null){
			return 1L;
		}else{
			return estado.getIdEstado();
		}
	}	
}
