package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
@PrimaryKeyJoinColumn(name="id_pessoa")
@NamedQueries({
	@NamedQuery(name="Cliente.existeCnpjCpf", query="SELECT c FROM Cliente c WHERE c.nmCnpjCpf=:cnpjcpf"),
	@NamedQuery(name="Cliente.recuperarSenha1", query="SELECT c FROM Cliente c inner join c.emails e WHERE e.nmEmail=:campo"),
	@NamedQuery(name="Cliente.recuperarSenha2", query="SELECT c FROM Cliente c inner join c.emails e WHERE c.nmCnpjCpf=:campo"),	
	@NamedQuery(name="Cliente.loginCliente1", query="SELECT c FROM Cliente c inner join c.login l WHERE l.nmLogin=:login AND l.nmSenha=:senha AND l.snStatus=1"),
	@NamedQuery(name="Cliente.loginCliente2", query="SELECT c FROM Cliente c inner join c.login l WHERE c.nmCnpjCpf=:login AND l.nmSenha=:senha AND l.snStatus=1"),
	@NamedQuery(name="Cliente.loginCliente3", query="SELECT c FROM Cliente c inner join c.login l inner join c.emails e WHERE e.nmEmail=:login AND l.nmSenha=:senha AND l.snStatus=1"),
	@NamedQuery(name="Cliente.clienteId", query="SELECT c FROM Cliente c WHERE c.idPessoa=:idPessoa"),
	@NamedQuery(name="Cliente.loginClienteCodigo", query="SELECT c FROM Cliente c inner join c.login l WHERE l.pessoa.idFatfClie=:codigo AND l.snStatus=1")
})	

public class Cliente extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name="dt_fundacao")
	private Date dtFundacao;

	@NotBlank
	@Column(name="nm_contato")
	private String nmContato;

	@Column(name="nm_fantasia")
	private String nmFantasia;
	
	@Column(name="nm_inscricao_estadual")
	private String nmInscricaoEstadual;
	
	@Column(name="nm_inscricao_produtor")
	private String nmInscricaoProdutor;
		
	@NotBlank
	@Column(name="nm_razao_social")
	private String nmRazaoSocial;
	
	@NotNull
	@Column(name="nr_porte")
	private int nrPorte;
	
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<Contato> contato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_categoria_cliente")
	private CategoriaCliente categoriaCliente;
	
	@OneToMany(mappedBy="cliente", cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private List<HorarioAtendimento> horarioAtendimentos = new ArrayList<>();

	@OneToOne(mappedBy="cliente", cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private Suframa suframas;

	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<LogClienteLogin> LogClienteLogins = new ArrayList<>();
	
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<CampanhaCliente> campanhaClientes = new ArrayList<>();
	
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<ContatoLogado> contatoLogados = new ArrayList<>();
		
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<ClienteCultura> clienteCulturas = new ArrayList<>();
		
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<ClienteFabricante> clienteFabricantes = new ArrayList<>();
		
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();
		
	@OneToOne(mappedBy="cliente", cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	private ClienteCompreGanhe clienteCompreGanhe;
		
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<LogProdutoAcessado> logProdutoAcessados;
	
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private List<CotacaoFornecedor> cotacaoFornecedor = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_funcionario_vendedor")
	private Funcionario funcionarioVendedo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_funcionario_log")
	private Funcionario funcionarioLog;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dtAlteracao;
			
	public List<CotacaoFornecedor> getCotacaoFornecedor() {
		return cotacaoFornecedor;
	}

	public void setCotacaoFornecedor(List<CotacaoFornecedor> cotacaoFornecedor) {
		this.cotacaoFornecedor = cotacaoFornecedor;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}
	
	public Funcionario getFuncionarioVendedo() {
		return funcionarioVendedo;
	}

	public void setFuncionarioVendedo(Funcionario funcionarioVendedo) {
		this.funcionarioVendedo = funcionarioVendedo;
	}

	public Funcionario getFuncionarioLog() {
		return funcionarioLog;
	}

	public void setFuncionarioLog(Funcionario funcionarioLog) {
		this.funcionarioLog = funcionarioLog;
	}

	public List<LogProdutoAcessado> getLogProdutoAcessados() {
		return logProdutoAcessados;
	}

	public void setLogProdutoAcessados(List<LogProdutoAcessado> logProdutoAcessados) {
		this.logProdutoAcessados = logProdutoAcessados;
	}

	public ClienteCompreGanhe getClienteCompreGanhe() {
		return clienteCompreGanhe;
	}

	public void setClienteCompreGanhe(ClienteCompreGanhe clienteCompreGanhe) {
		this.clienteCompreGanhe = clienteCompreGanhe;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<ClienteFabricante> getClienteFabricantes() {
		return clienteFabricantes;
	}

	public void setClienteFabricantes(List<ClienteFabricante> clienteFabricantes) {
		this.clienteFabricantes = clienteFabricantes;
	}

	public List<ClienteCultura> getClienteCulturas() {
		return clienteCulturas;
	}

	public void setClienteCulturas(List<ClienteCultura> clienteCulturas) {
		this.clienteCulturas = clienteCulturas;
	}

	public List<ContatoLogado> getContatoLogados() {
		return contatoLogados;
	}

	public void setContatoLogados(List<ContatoLogado> contatoLogados) {
		this.contatoLogados = contatoLogados;
	}

	public List<CampanhaCliente> getCampanhaClientes() {
		return campanhaClientes;
	}

	public void setCampanhaClientes(List<CampanhaCliente> campanhaClientes) {
		this.campanhaClientes = campanhaClientes;
	}

	public Date getDtFundacao() {
		return dtFundacao;
	}

	public void setDtFundacao(Date dtFundacao) {
		this.dtFundacao = dtFundacao;
	}

	public String getNmContato() {
		return nmContato;
	}

	public void setNmContato(String nmContato) {
		this.nmContato = nmContato;
	}

	public String getNmFantasia() {
		return nmFantasia;
	}

	public void setNmFantasia(String nmFantasia) {
		this.nmFantasia = nmFantasia;
	}

	public String getNmInscricaoEstadual() {
		return nmInscricaoEstadual;
	}

	public void setNmInscricaoEstadual(String nmInscricaoEstadual) {
		this.nmInscricaoEstadual = nmInscricaoEstadual;
	}

	public String getNmInscricaoProdutor() {
		return nmInscricaoProdutor;
	}

	public void setNmInscricaoProdutor(String nmInscricaoProdutor) {
		this.nmInscricaoProdutor = nmInscricaoProdutor;
	}

	public String getNmRazaoSocial() {
		return nmRazaoSocial;
	}

	public void setNmRazaoSocial(String nmRazaoSocial) {
		this.nmRazaoSocial = nmRazaoSocial;
	}

	public int getNrPorte() {
		return nrPorte;
	}

	public void setNrPorte(int nrPorte) {
		this.nrPorte = nrPorte;
	}

	public List<Contato> getContato() {
		return contato;
	}

	public void setContato(List<Contato> contato) {
		this.contato = contato;
	}

	public CategoriaCliente getCategoriaCliente() {
		return categoriaCliente;
	}

	public void setCategoriaCliente(CategoriaCliente categoriaCliente) {
		this.categoriaCliente = categoriaCliente;
	}

	public List<HorarioAtendimento> getHorarioAtendimentos() {
		return horarioAtendimentos;
	}

	public void setHorarioAtendimentos(List<HorarioAtendimento> horarioAtendimentos) {
		this.horarioAtendimentos = horarioAtendimentos;
	}
		
	public Suframa getSuframas() {
		return suframas;
	}

	public void setSuframas(Suframa suframas) {
		this.suframas = suframas;
	}

	public List<LogClienteLogin> getLogClienteLogins() {
		return LogClienteLogins;
	}

	public void setLogClienteLogins(List<LogClienteLogin> logClienteLogins) {
		LogClienteLogins = logClienteLogins;
	}
}