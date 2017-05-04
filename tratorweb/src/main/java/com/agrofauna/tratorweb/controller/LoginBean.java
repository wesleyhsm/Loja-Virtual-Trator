package com.agrofauna.tratorweb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.agrofauna.tratorweb.model.CategoriaCliente;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.ClienteCompreGanhe;
import com.agrofauna.tratorweb.model.Estado;
import com.agrofauna.tratorweb.model.Funcionario;
import com.agrofauna.tratorweb.model.LogClienteLogin;
import com.agrofauna.tratorweb.model.LogFuncionarioLogin;
import com.agrofauna.tratorweb.model.Login;
import com.agrofauna.tratorweb.model.Suframa;
import com.agrofauna.tratorweb.repository.CategoriaClienteRepository;
import com.agrofauna.tratorweb.repository.ClienteCompreGanheRepository;
import com.agrofauna.tratorweb.repository.EstadoRepository;
import com.agrofauna.tratorweb.repository.LogClienteLoginRepository;
import com.agrofauna.tratorweb.repository.LogFuncionarioLoginRepository;
import com.agrofauna.tratorweb.repository.PessoaRepository;
import com.agrofauna.tratorweb.repository.SuframaRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;
import com.agrofauna.tratorweb.webservice.PegarDollar;

@Named
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
					
	private boolean logar = false;
	private Login login = new Login();
	private int tipoLogin = 0;
	private Cliente cliente = new Cliente(); 
	private Estado estado = new Estado();
	private Suframa suframa = new Suframa();
	private ClienteCompreGanhe clienteCompreGanhe = new ClienteCompreGanhe(); 
	private Funcionario funcionario = new Funcionario();
	
	private Estado estadoOrigem = new Estado();
	
	@Inject
	private PessoaRepository pessoaRepository;
	
	@Inject
	private LogClienteLoginRepository logClienteLoginRepository;
	
	@Inject
	private LogFuncionarioLoginRepository logFuncionarioLoginRepository;
	
	@Inject
	private EstadoRepository estadoRepository;
	
	@Inject
	private SuframaRepository suframaRepository;
			
	@Inject
	private ClienteCompreGanheRepository clienteCompreGanheRepository;
	
	@Inject
	private CategoriaClienteRepository categoriaClienteRepository;
	
	private LogClienteLogin logCliente = new LogClienteLogin();
	private LogFuncionarioLogin logFuncionario = new LogFuncionarioLogin();  
	private CategoriaCliente categoriaCliente = new CategoriaCliente();
	private PegarDollar pegarDollar = new PegarDollar(); 	
	private long codigoCliente;
	
	public String logarCliente(){
		
		cliente = pessoaRepository.loginCliente(login, tipoLogin);
		
		System.out.println();
		if(cliente != null){
			
			this.login = cliente.getLogin();
			this.funcionario = null;
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
            request.getSession().setAttribute("login", this.login);
            		
			System.out.println("wesley 1 " + login.getNmLogin());
			System.out.println("wesley 2 " + login.getNmSenha());
			
			categoriaCliente = categoriaClienteRepository.buscarCategoriaCliente(cliente);
									
			String browser = (String) ec.getRequestHeaderMap().get("User-Agent"); //pega o navegador do cliente
			
			//pega ip do cliente		  
		    String ip = null;
		    ip = request.getHeader("x-forwarded-for");
		    if (ip == null) {
		    	ip = request.getHeader("X_FORWARDED_FOR");
		        if (ip == null){
		        	ip = request.getRemoteAddr();
		        }
		    }		    		    
			//System.out.println("wesley IP: " + ip);		
			
		    LogClienteLogin logClienteLogin = new LogClienteLogin();
			logClienteLogin.setNmIp(""+ip);
			logClienteLogin.setNmNavegador(browser);
			logClienteLogin.setDtLogEntrada(new Date());
			logClienteLogin.setDtLogSaida(new Date());						
			logClienteLogin.setSnGeroPedido(false);
			logClienteLogin.setCliente(cliente);			
			logClienteLoginRepository.salvarLogClienteLogin(logClienteLogin);
					
			this.logCliente = logClienteLoginRepository.buscarUltimoLog(cliente);
			
			//System.out.println("wesley cliente: " + this.logCliente.getIdLogClienteLogin());
			
			Suframa suframa2 = suframaRepository.buscarSuframa(cliente); 
			if(suframa2 != null){ 
				this.suframa = suframa2;
			}	
			
			estado = estadoRepository.buscarEstadoCliente(cliente);			
			estadoOrigem = estadoRepository.buscarEstadoOrigem(26);
			clienteCompreGanhe = clienteCompreGanheRepository.buscarClienteCompreGanhe(cliente);
			
			//return "/promocao/promocaoIndex.xhtml";
			return"/produto/produtoNormal.xhtml";
		}
		
		FacesUtil.addErrorMessage("Dados Inválidos.");
        return "index.xhtml";
    }
	
	public String logarVendedor(){
		
		this.funcionario = pessoaRepository.loginFuncionario(login);
		this.cliente = pessoaRepository.loginClienteCodigo(codigoCliente);
		
		if(cliente != null && funcionario != null){
			
			this.login = cliente.getLogin();
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
            request.getSession().setAttribute("login", this.login);
            		
			System.out.println("wesley 1 " + login.getNmLogin());
			System.out.println("wesley 2 " + login.getNmSenha());
			
			categoriaCliente = categoriaClienteRepository.buscarCategoriaCliente(cliente);
									
			String browser = (String) ec.getRequestHeaderMap().get("User-Agent"); //pega o navegador do cliente
			
			//pega ip do cliente		  
		    String ip = null;
		    ip = request.getHeader("x-forwarded-for");
		    if (ip == null) {
		    	ip = request.getHeader("X_FORWARDED_FOR");
		        if (ip == null){
		        	ip = request.getRemoteAddr();
		        }
		    }		    		    
			//System.out.println("wesley IP: " + ip);		
			
		    LogFuncionarioLogin logFuncionarioLogin = new LogFuncionarioLogin();			
			logFuncionarioLogin.setNmNavegador(browser);
			logFuncionarioLogin.setDtLogEntrada(new Date());
			logFuncionarioLogin.setDtLogSaida(new Date());						
			logFuncionarioLogin.setSnGeroPedido(false);
			logFuncionarioLogin.setFuncionario(funcionario);
			logFuncionarioLoginRepository.salvarLogFuncionarioLogin(logFuncionarioLogin);
					
			this.logFuncionario = logFuncionarioLoginRepository.buscarUltimoLog(this.funcionario); 
			
			//System.out.println("wesley Funcionario: " + this.logFuncionario.getIdLogFuncionarioLogin());
			
			Suframa suframa2 = suframaRepository.buscarSuframa(cliente); 
			if(suframa2 != null){ 
				this.suframa = suframa2;
			}	
			
			estado = estadoRepository.buscarEstadoCliente(cliente);
			estadoOrigem = estadoRepository.buscarEstadoOrigem(26);
			clienteCompreGanhe = clienteCompreGanheRepository.buscarClienteCompreGanhe(cliente);
			
			//return "/promocao/promocaoIndex.xhtml";
			return"/produto/produtoNormal.xhtml";
		}
		
		FacesUtil.addErrorMessage("Dados Inválidos.");
        return "admin.xhtml";
    }
	
	public boolean statusLogar(){
		return logar;
	}
	
	public void atualizaLog(){
		this.logCliente.setSnGeroPedido(true);
		this.logCliente.setDtLogSaida(new Date());
		logClienteLoginRepository.salvarLogClienteLogin(this.logCliente);
	} 
	
	public void atualizaLogFuncionario(){
		this.logFuncionario.setSnGeroPedido(true);
		this.logFuncionario.setDtLogSaida(new Date());
		logFuncionarioLoginRepository.salvarLogFuncionarioLogin(this.logFuncionario);
	}
	
	public void deslogar(){		
		if(funcionario != null){
			logFuncionario.setDtLogSaida(new Date());
			logFuncionarioLoginRepository.salvarLogFuncionarioLogin(logFuncionario);
		}else{
			logCliente.setDtLogSaida(new Date());
			logClienteLoginRepository.salvarLogClienteLogin(logCliente);
		}
		
		// "limpa" cliente e login
		this.login = new Login();
		this.cliente = new Cliente();
		this.logCliente = new LogClienteLogin();
		this.logFuncionario = new LogFuncionarioLogin();		
		setLogar(true);
		
		// invalida a sessao
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
		session.invalidate();
		
		//faz o redirect de qualquer página
		try {			
			facesContext.getExternalContext().redirect("/tratorweb/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
			
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getTipoLogin() {
		return tipoLogin;
	}

	public void setTipoLogin(int tipoLogin) {
		this.tipoLogin = tipoLogin;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public boolean isLogar() {
		return logar;
	}

	public void setLogar(boolean logar) {
		this.logar = logar;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Estado getEstadoOrigem() {
		return estadoOrigem;
	}

	public void setEstadoOrigem(Estado estadoOrigem) {
		this.estadoOrigem = estadoOrigem;
	}

	public Suframa getSuframa() {
		return suframa;
	}

	public void setSuframa(Suframa suframa) {
		this.suframa = suframa;
	}

	public ClienteCompreGanhe getClienteCompreGanhe() {
		return clienteCompreGanhe;
	}

	public void setClienteCompreGanhe(ClienteCompreGanhe clienteCompreGanhe) {
		this.clienteCompreGanhe = clienteCompreGanhe;
	}

	public LogClienteLogin getLogCliente() {
		return logCliente;
	}

	public void setLogCliente(LogClienteLogin logCliente) {
		this.logCliente = logCliente;
	}

	public CategoriaCliente getCategoriaCliente() {
		return categoriaCliente;
	}

	public void setCategoriaCliente(CategoriaCliente categoriaCliente) {
		this.categoriaCliente = categoriaCliente;
	}

	public PegarDollar getPegarDollar() {
		return pegarDollar;
	}

	public void setPegarDollar(PegarDollar pegarDollar) {
		this.pegarDollar = pegarDollar;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public LogFuncionarioLogin getLogFuncionario() {
		return logFuncionario;
	}

	public void setLogFuncionario(LogFuncionarioLogin logFuncionario) {
		this.logFuncionario = logFuncionario;
	}
}
