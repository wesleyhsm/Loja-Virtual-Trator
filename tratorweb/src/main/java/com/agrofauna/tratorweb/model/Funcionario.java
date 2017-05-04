package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the funcionario database table.
 * 
 */
@Entity
@Table(name="funcionario")
@PrimaryKeyJoinColumn(name="id_pessoa")
@NamedQueries({
	@NamedQuery(name="Funcionario.findAll", query="SELECT f FROM Funcionario f"),
	@NamedQuery(name="Funcionario.login", query="SELECT f FROM Funcionario f inner join f.login l WHERE l.nmLogin=:login AND l.nmSenha=:senha AND l.snStatus=1")
})	
public class Funcionario extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_exame_medico")
	private Date dtExameMedico;

	@Column(name="nm_cbo")
	private String nmCbo;

	@Column(name="nm_estado_civil")
	private String nmEstadoCivil;

	@Column(name="nm_funcionario")
	private String nmFuncionario;

	@Column(name="nm_grau_instrucao")
	private String nmGrauInstrucao;

	@Column(name="nm_mae")
	private String nmMae;

	@Column(name="nm_pai")
	private String nmPai;

	@Column(name="nm_sexo")
	private String nmSexo;

	@Column(name="nm_tipo_contrato")
	private String nmTipoContrato;

	@Column(name="nr_horario_mensal")
	private int nrHorarioMensal;

	@Column(name="nr_horario_semanal")
	private int nrHorarioSemanal;

	@Column(name="sn_convenio")
	private byte snConvenio;

	@Column(name="sn_deficiente")
	private byte snDeficiente;

	@Column(name="sn_insalubridade")
	private byte snInsalubridade;
	
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<Campanha> campanha;
	
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<Produto> produto;
	
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<Contato> contato;

	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<ContatoLogado> contatoLogados;
		
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<CampanhaImagem> campanhaImagems;
		
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<ProdutoImagem> produtoImagem;
		
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<ProdutoBanner> produtoBanner;
	
	@OneToMany(mappedBy="funcionarioVendedo", fetch = FetchType.LAZY)
	private List<Cliente> clienteVendedo;
	
	@OneToMany(mappedBy="funcionarioLog", fetch = FetchType.LAZY)
	private List<Cliente> clienteLog;
	
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)	
	private List<PedidoStatusEmailEnviados> pedidoStatusEmailEnviados;
	
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<PedidoStatusEmail> pedidoStatusEmails;
	
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<NotaFiscalEletronica> notaFiscalEletronicas;
	
	@OneToMany(mappedBy="funcionarioCriacao", fetch = FetchType.LAZY)
	private List<Cotacao> cotacaoCriacao;
	
	@OneToMany(mappedBy="funcionarioAlteracao", fetch = FetchType.LAZY)
	private List<Cotacao> cotacaoAlteracao;
			
	public List<PedidoStatusEmailEnviados> getPedidoStatusEmailEnviados() {
		return pedidoStatusEmailEnviados;
	}

	public void setPedidoStatusEmailEnviados(List<PedidoStatusEmailEnviados> pedidoStatusEmailEnviados) {
		this.pedidoStatusEmailEnviados = pedidoStatusEmailEnviados;
	}

	public List<PedidoStatusEmail> getPedidoStatusEmails() {
		return pedidoStatusEmails;
	}

	public void setPedidoStatusEmails(List<PedidoStatusEmail> pedidoStatusEmails) {
		this.pedidoStatusEmails = pedidoStatusEmails;
	}

	public List<Cotacao> getCotacaoCriacao() {
		return cotacaoCriacao;
	}

	public void setCotacaoCriacao(List<Cotacao> cotacaoCriacao) {
		this.cotacaoCriacao = cotacaoCriacao;
	}

	public List<Cotacao> getCotacaoAlteracao() {
		return cotacaoAlteracao;
	}

	public void setCotacaoAlteracao(List<Cotacao> cotacaoAlteracao) {
		this.cotacaoAlteracao = cotacaoAlteracao;
	}

	public List<NotaFiscalEletronica> getNotaFiscalEletronicas() {
		return notaFiscalEletronicas;
	}

	public void setNotaFiscalEletronicas(List<NotaFiscalEletronica> notaFiscalEletronicas) {
		this.notaFiscalEletronicas = notaFiscalEletronicas;
	}

	public List<Cliente> getClienteVendedo() {
		return clienteVendedo;
	}

	public void setClienteVendedo(List<Cliente> clienteVendedo) {
		this.clienteVendedo = clienteVendedo;
	}

	public List<Cliente> getClienteLog() {
		return clienteLog;
	}

	public void setClienteLog(List<Cliente> clienteLog) {
		this.clienteLog = clienteLog;
	}

	public List<ProdutoBanner> getProdutoBanner() {
		return produtoBanner;
	}

	public void setProdutoBanner(List<ProdutoBanner> produtoBanner) {
		this.produtoBanner = produtoBanner;
	}

	public List<ProdutoImagem> getProdutoImagem() {
		return produtoImagem;
	}

	public void setProdutoImagem(List<ProdutoImagem> produtoImagem) {
		this.produtoImagem = produtoImagem;
	}

	public List<CampanhaImagem> getCampanhaImagems() {
		return campanhaImagems;
	}

	public void setCampanhaImagems(List<CampanhaImagem> campanhaImagems) {
		this.campanhaImagems = campanhaImagems;
	}

	public List<ContatoLogado> getContatoLogados() {
		return contatoLogados;
	}

	public void setContatoLogados(List<ContatoLogado> contatoLogados) {
		this.contatoLogados = contatoLogados;
	}

	public Date getDtExameMedico() {
		return dtExameMedico;
	}

	public void setDtExameMedico(Date dtExameMedico) {
		this.dtExameMedico = dtExameMedico;
	}

	public String getNmCbo() {
		return nmCbo;
	}

	public void setNmCbo(String nmCbo) {
		this.nmCbo = nmCbo;
	}

	public String getNmEstadoCivil() {
		return nmEstadoCivil;
	}

	public void setNmEstadoCivil(String nmEstadoCivil) {
		this.nmEstadoCivil = nmEstadoCivil;
	}

	public String getNmFuncionario() {
		return nmFuncionario;
	}

	public void setNmFuncionario(String nmFuncionario) {
		this.nmFuncionario = nmFuncionario;
	}

	public String getNmGrauInstrucao() {
		return nmGrauInstrucao;
	}

	public void setNmGrauInstrucao(String nmGrauInstrucao) {
		this.nmGrauInstrucao = nmGrauInstrucao;
	}

	public String getNmMae() {
		return nmMae;
	}

	public void setNmMae(String nmMae) {
		this.nmMae = nmMae;
	}

	public String getNmPai() {
		return nmPai;
	}

	public void setNmPai(String nmPai) {
		this.nmPai = nmPai;
	}

	public String getNmSexo() {
		return nmSexo;
	}

	public void setNmSexo(String nmSexo) {
		this.nmSexo = nmSexo;
	}

	public String getNmTipoContrato() {
		return nmTipoContrato;
	}

	public void setNmTipoContrato(String nmTipoContrato) {
		this.nmTipoContrato = nmTipoContrato;
	}

	public int getNrHorarioMensal() {
		return nrHorarioMensal;
	}

	public void setNrHorarioMensal(int nrHorarioMensal) {
		this.nrHorarioMensal = nrHorarioMensal;
	}

	public int getNrHorarioSemanal() {
		return nrHorarioSemanal;
	}

	public void setNrHorarioSemanal(int nrHorarioSemanal) {
		this.nrHorarioSemanal = nrHorarioSemanal;
	}

	public byte getSnConvenio() {
		return snConvenio;
	}

	public void setSnConvenio(byte snConvenio) {
		this.snConvenio = snConvenio;
	}

	public byte getSnDeficiente() {
		return snDeficiente;
	}

	public void setSnDeficiente(byte snDeficiente) {
		this.snDeficiente = snDeficiente;
	}

	public byte getSnInsalubridade() {
		return snInsalubridade;
	}

	public void setSnInsalubridade(byte snInsalubridade) {
		this.snInsalubridade = snInsalubridade;
	}

	public List<Campanha> getCampanha() {
		return campanha;
	}

	public void setCampanha(List<Campanha> campanha) {
		this.campanha = campanha;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	public List<Contato> getContato() {
		return contato;
	}

	public void setContato(List<Contato> contato) {
		this.contato = contato;
	}
}