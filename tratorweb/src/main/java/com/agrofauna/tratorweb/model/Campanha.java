package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the campanha database table.
 * 
**/
@Entity
@Table(name="campanha")
@NamedQuery(name="Campanha.findAll", query="SELECT c FROM Campanha c")
public class Campanha implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_campanha")
	private long idCampanha;

	@Column(name="desc_campanha")
	private String descCampanha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_atualizacao")
	private Date dtAtualizacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_final")
	private Date dtFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="dt_inicial")
	private Date dtInicial;
	
	@Column(name="nm_campanha")
	private String nmCampanha;

	@NotBlank
	@Column(name="nm_link_selo")
	private String nmLinkSelo;
	
	@Column(name="nm_tipo")
	private String nmTipo;
	
	@Column(name="nr_percentual_valor")
	private double nrPercentualValor;

	@Column(name="nr_prioridade")
	private int nrPrioridade;
	
	@Column(name="sn_promocao")
	private boolean snPromocao;
			
	@Column(name="sn_alteracao")
	private int snAlteracao;
	
	@Column(name="sn_preco_minimo")
	private int snPrecoMinimo;
	
	@Column(name="sn_encomenda")
	private int snEncomenda;
	
	@Column(name="sn_desconto")
	private int snDesconto;
	
	@Column(name="sn_base")
	private int snBase;
	
	@Column(name="sn_smart_mail")
	private int snSmartMail;
	
	@Column(name="sn_status")
	private int snStatus;
	
	@Column(name="origem")
	private String origem;
	
	//bi-directional many-to-one association to CampanhaCliente
	@OneToMany(mappedBy="campanha", fetch = FetchType.LAZY)
	private List<CampanhaCliente> campanhaClientes;
	
	//bi-directional many-to-one association to CampanhaProduto
	@OneToMany(mappedBy="campanha", fetch = FetchType.LAZY)
	private List<CampanhaProduto> campanhaProdutos;
		
	///bi-directional many-to-one association to Funcionario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_funcionario")
	private Funcionario funcionario;
		
	public long getIdCampanha() {
		return this.idCampanha;
	}

	public void setIdCampanha(long idCampanha) {
		this.idCampanha = idCampanha;
	}

	public String getDescCampanha() {
		return this.descCampanha;
	}

	public void setDescCampanha(String descCampanha) {
		this.descCampanha = descCampanha;
	}

	public Date getDtCriacao() {
		return this.dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtFinal() {
		return this.dtFinal;
	}

	public void setDtFinal(Date dtFinal) {
		this.dtFinal = dtFinal;
	}

	public Date getDtInicial() {
		return this.dtInicial;
	}

	public void setDtInicial(Date dtInicial) {
		this.dtInicial = dtInicial;
	}

	public String getNmCampanha() {
		return this.nmCampanha;
	}

	public void setNmCampanha(String nmCampanha) {
		this.nmCampanha = nmCampanha;
	}

	public double getNrPercentualValor() {
		return this.nrPercentualValor;
	}

	public void setNrPercentualValor(double nrPercentualValor) {
		this.nrPercentualValor = nrPercentualValor;
	}

	public int getNrPrioridade() {
		return this.nrPrioridade;
	}

	public void setNrPrioridade(int nrPrioridade) {
		this.nrPrioridade = nrPrioridade;
	}

	public String getOrigem() {
		return this.origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public List<CampanhaCliente> getCampanhaClientes() {
		return this.campanhaClientes;
	}

	public void setCampanhaClientes(List<CampanhaCliente> campanhaClientes) {
		this.campanhaClientes = campanhaClientes;
	}

	public CampanhaCliente addCampanhaCliente(CampanhaCliente campanhaCliente) {
		getCampanhaClientes().add(campanhaCliente);
		campanhaCliente.setCampanha(this);

		return campanhaCliente;
	}

	public CampanhaCliente removeCampanhaCliente(CampanhaCliente campanhaCliente) {
		getCampanhaClientes().remove(campanhaCliente);
		campanhaCliente.setCampanha(null);

		return campanhaCliente;
	}

	public List<CampanhaProduto> getCampanhaProdutos() {
		return this.campanhaProdutos;
	}

	public void setCampanhaProdutos(List<CampanhaProduto> campanhaProdutos) {
		this.campanhaProdutos = campanhaProdutos;
	}

	public CampanhaProduto addCampanhaProduto(CampanhaProduto campanhaProduto) {
		getCampanhaProdutos().add(campanhaProduto);
		campanhaProduto.setCampanha(this);

		return campanhaProduto;
	}

	public CampanhaProduto removeCampanhaProduto(CampanhaProduto campanhaProduto) {
		getCampanhaProdutos().remove(campanhaProduto);
		campanhaProduto.setCampanha(null);

		return campanhaProduto;
	}
	
	public boolean getSnPromocao() {
		return snPromocao;
	}

	public void setSnPromocao(boolean snPromocao) {
		this.snPromocao = snPromocao;
	}

	public int getSnAlteracao() {
		return snAlteracao;
	}

	public void setSnAlteracao(int snAlteracao) {
		this.snAlteracao = snAlteracao;
	}

	public int getSnPrecoMinimo() {
		return snPrecoMinimo;
	}

	public void setSnPrecoMinimo(int snPrecoMinimo) {
		this.snPrecoMinimo = snPrecoMinimo;
	}

	public int getSnEncomenda() {
		return snEncomenda;
	}

	public void setSnEncomenda(int snEncomenda) {
		this.snEncomenda = snEncomenda;
	}

	public int getSnDesconto() {
		return snDesconto;
	}

	public void setSnDesconto(int snDesconto) {
		this.snDesconto = snDesconto;
	}

	public int getSnBase() {
		return snBase;
	}

	public void setSnBase(int snBase) {
		this.snBase = snBase;
	}

	public int getSnSmartMail() {
		return snSmartMail;
	}

	public void setSnSmartMail(int snSmartMail) {
		this.snSmartMail = snSmartMail;
	}
	
	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}
	
	public String getNmTipo() {
		return nmTipo;
	}

	public void setNmTipo(String nmTipo) {
		this.nmTipo = nmTipo;
	}

	public String getNmLinkSelo() {
		return nmLinkSelo;
	}

	public void setNmLinkSelo(String nmLinkSelo) {
		this.nmLinkSelo = nmLinkSelo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCampanha ^ (idCampanha >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campanha other = (Campanha) obj;
		if (idCampanha != other.idCampanha)
			return false;
		return true;
	}
}