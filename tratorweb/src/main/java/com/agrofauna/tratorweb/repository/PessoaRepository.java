package com.agrofauna.tratorweb.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.agrofauna.tratorweb.model.ConexaoBanco;
import com.agrofauna.tratorweb.filtro.RecuperarSenha;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Funcionario;
import com.agrofauna.tratorweb.model.Login;

public class PessoaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Inject
	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	@Transactional
	public void salvarPessoa(Cliente cliente){
		try{
			manager.merge(cliente);			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> buscarPessoa(Cliente cliente){
		
		List<Cliente> listCliente = new ArrayList<Cliente>(); 
		
		try {
			
			Query query = manager.createNamedQuery("Cliente.existeCnpjCpf");
			query.setParameter("cnpjcpf", cliente.getNmCnpjCpf());			
			listCliente = query.getResultList();
			
		} catch (NoResultException e) {		
		}
		
		return listCliente;
	}
	
	public Cliente recuperarSenhaCliente(RecuperarSenha recuperarSenha){			
		try {			
			Query query = manager.createNamedQuery("Cliente.recuperarSenha"+recuperarSenha.getNrTipo());
			query.setParameter("campo", recuperarSenha.getNmCampo());			
			return (Cliente) query.getSingleResult();			
		} catch (NoResultException e) {
			return null;
		}	
	}
	
	public Cliente loginCliente(Login login, int tipoLogin){						
		try {			
			Query query = manager.createNamedQuery("Cliente.loginCliente" + tipoLogin);
			query.setParameter("login", login.getNmLogin());
			query.setParameter("senha", login.getNmSenha());
			return (Cliente) query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;	
		}		
	}
	
	public Cliente loginClienteCodigo(long clienteCodigo){						
		try {			
			Query query = manager.createNamedQuery("Cliente.loginClienteCodigo");
			query.setParameter("codigo", clienteCodigo);			
			return (Cliente) query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;	
		}		
	}
	
	public Funcionario loginFuncionario(Login login){
		Funcionario func = new Funcionario();		
		try{
			String queryLogin = "SELECT "+
									"f.id_pessoa, "+
									"f.nm_funcionario "+
								"FROM "+
									"db_agro_matriz.login l "+
								    "INNER JOIN db_agro_matriz.funcionario f on f.id_pessoa=l.id_pessoa "+
								"WHERE "+
									"l.nm_login = '"+ login.getNmLogin() +"' AND "+ 
								    "l.nm_senha = '"+ login.getNmSenha() +"' AND "+
								    "l.sn_status=1;";
			Connection connPessoa = new ConexaoBanco().getConnectionNovo();
			try(java.sql.PreparedStatement stmtPessoa = connPessoa.prepareStatement(queryLogin)) {								
				ResultSet rsPessoa = stmtPessoa.executeQuery();								
				if(rsPessoa.next()){
					
					func.setIdPessoa(rsPessoa.getLong("id_pessoa"));
					func.setNmFuncionario(rsPessoa.getString("nm_funcionario"));
				}
				rsPessoa.close();
				stmtPessoa.close();
			}
			connPessoa.close();
			
		}catch (SQLException e) {
			return null;
		}
		return func;
	}
}
