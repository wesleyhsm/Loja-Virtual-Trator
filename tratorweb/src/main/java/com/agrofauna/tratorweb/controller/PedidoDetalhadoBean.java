package com.agrofauna.tratorweb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.Pedido;
import com.agrofauna.tratorweb.model.PedidoProduto;
import com.agrofauna.tratorweb.model.PedidoStatusEmailEnviados;
import com.agrofauna.tratorweb.repository.EmailRepository;
import com.agrofauna.tratorweb.repository.PedidoProdutoRepository;
import com.agrofauna.tratorweb.repository.PedidoRepository;
import com.agrofauna.tratorweb.repository.PedidoStatusEmailEnviadosRepository;

@Named
@ViewScoped
public class PedidoDetalhadoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private PedidoProdutoRepository pedidoProdutoRepository;
	
	@Inject
	private EmailRepository emailRepository;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private PedidoStatusEmailEnviadosRepository pedidoStatusEmailEnviadosRepository;
	
	private Pedido pedido = new Pedido();
	private List<Email> listEmails = new ArrayList<>();
	private List<PedidoProduto> listProdutos = new ArrayList<>();
	
	FacesContext context = FacesContext.getCurrentInstance();
	
	public PedidoDetalhadoBean(){
		try {
			this.pedido.setIdPedido(Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
						
		}catch(Exception e){
			try {
				context.getExternalContext().redirect("/tratorweb/index.xhtml");
			} catch (IOException e1) {				
			}
		}
	}
	
	public List<PedidoStatusEmailEnviados> pedidoStatusEmail(){
		return pedidoStatusEmailEnviadosRepository.pedidoStatusEmail(this.pedido);
	}
	
	public void pedidoDetalhado(){
		this.pedido = pedidoRepository.pedidoDetalhado(this.pedido);
		this.listProdutos = pedidoProdutoRepository.pedidoProdutoDetalhado(this.pedido);
		this.listEmails = emailRepository.buscarEmail(loginBean.getCliente());
	}
		
	public List<Email> getListEmails() {
		return listEmails;
	}

	public void setListEmails(List<Email> listEmails) {
		this.listEmails = listEmails;
	}

	public List<PedidoProduto> getListProdutos() {
		return listProdutos;
	}

	public void setListProdutos(List<PedidoProduto> listProdutos) {
		this.listProdutos = listProdutos;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}	
}
