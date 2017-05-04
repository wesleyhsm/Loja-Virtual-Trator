package com.agrofauna.tratorweb.controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.model.ClienteCompreGanhe;
import com.agrofauna.tratorweb.repository.ClienteCompreGanheRepository;

@Named
@ViewScoped
public class MeusPontosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteCompreGanheRepository clienteCompreGanheRepository;
	
	@Inject
	private LoginBean loginBean;
			
	private ClienteCompreGanhe clienteCompreGanhe = new ClienteCompreGanhe();
				
	public double pontosCompreGanheCliente(){
		clienteCompreGanhe = clienteCompreGanheRepository.buscarClienteCompreGanhe(loginBean.getCliente());
		if(clienteCompreGanhe != null){
			return clienteCompreGanhe.getNrQuantidadePontos();
		}else{
			return 0;
		}
	}
		
	public ClienteCompreGanhe getClienteCompreGanhe() {
		return clienteCompreGanhe;
	}

	public void setClienteCompreGanhe(ClienteCompreGanhe clienteCompreGanhe) {
		this.clienteCompreGanhe = clienteCompreGanhe;
	}
}
