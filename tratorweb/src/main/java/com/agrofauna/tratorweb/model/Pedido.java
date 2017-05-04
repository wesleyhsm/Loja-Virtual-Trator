package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="pedido")
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name="Pedido.todos", query="SELECT p FROM Pedido p")
})
public class Pedido implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido")
	private long idPedido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_cliente")
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_funcionario")
	private Funcionario Funcionario;
	
	@Column(name="nm_online")
	private String nmTipoVenda;   
	
	@Column(name="nr_troca") 
	private int nrTroca;
	
	@Column(name="nr_prazo_pagamento")
	private int nrPrazoPagamento;
	
	@Column(name="nr_observacao")
	private String nmOberservacao;
	
	@Column(name="sn_status")
	private int snStatus;
	
	@Column(name="nr_total_pedido")
	private double nrTotalPedido;
	
	@Column(name="nr_total_preco_encomenda")
	private double nrTotalPrecoEncomenda;
	
	@Column(name="nr_valor_desconto")
	private double nrValorDesconto;
	
	@Column(name="nm_tipo_pedido")
	private String nmTipoPedido;
	
	@Column(name="sn_incluir_frete")
	private boolean snIncluirFrete;
	
	@Column(name="nr_prontos_compre_ganhe")
	private double nrPontosCompreGanhe;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_vencimento")
	private Date dtVencimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_atualizacao")
	private Date dtAtualizacao;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;
	
	@OneToMany(mappedBy="pedido",fetch = FetchType.LAZY, cascade={CascadeType.ALL})
	private List<PedidoProduto> pedidoProdutos = new ArrayList<>();
	
	@OneToMany(mappedBy="pedido",fetch = FetchType.LAZY)
	private List<PedidoStatusEmailEnviados> pedidoStatusEmailEnviados = new ArrayList<>();
	
	@OneToMany(mappedBy="pedido",fetch = FetchType.LAZY)
	private List<PedidoTransportadora> pedidoTransportadoras = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_cfop")	
	private Cfop cfop;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_forma_Pagamento")	
	private FormaPagamento formaPagamento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_icms_estado")
	private IcmsEstado icmsEstado;
			
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_tipo_cobranca")
	private TipoCobranca tipoCobranca;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pedido_status")	
	private PedidoStatus pedidoStatus;
			
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pedido_tipo_frete")	
	private PedidoTipoFrete pedidoTipoFrete;
	
	@OneToMany(mappedBy="pedido")
	private List<NotaFiscalEletronica> notaFiscalEletronicas;
		
	public List<PedidoStatusEmailEnviados> getPedidoStatusEmailEnviados() {
		return pedidoStatusEmailEnviados;
	}

	public void setPedidoStatusEmailEnviados(List<PedidoStatusEmailEnviados> pedidoStatusEmailEnviados) {
		this.pedidoStatusEmailEnviados = pedidoStatusEmailEnviados;
	}

	public List<NotaFiscalEletronica> getNotaFiscalEletronicas() {
		return notaFiscalEletronicas;
	}

	public void setNotaFiscalEletronicas(List<NotaFiscalEletronica> notaFiscalEletronicas) {
		this.notaFiscalEletronicas = notaFiscalEletronicas;
	}

	public PedidoTipoFrete getPedidoTipoFrete() {
		return pedidoTipoFrete;
	}

	public void setPedidoTipoFrete(PedidoTipoFrete pedidoTipoFrete) {
		this.pedidoTipoFrete = pedidoTipoFrete;
	}

	public PedidoStatus getPedidoStatus() {
		return pedidoStatus;
	}

	public void setPedidoStatus(PedidoStatus pedidoStatus) {
		this.pedidoStatus = pedidoStatus;
	}

	public boolean isSnIncluirFrete() {
		return snIncluirFrete;
	}

	public void setSnIncluirFrete(boolean snIncluirFrete) {
		this.snIncluirFrete = snIncluirFrete;
	}

	public int getNrPrazoPagamento() {
		return nrPrazoPagamento;
	}

	public void setNrPrazoPagamento(int nrPrazoPagamento) {
		this.nrPrazoPagamento = nrPrazoPagamento;
	}

	public String getNmOberservacao() {
		return nmOberservacao;
	}

	public void setNmOberservacao(String nmOberservacao) {
		this.nmOberservacao = nmOberservacao;
	}

	public TipoCobranca getTipoCobranca() {
		return tipoCobranca;
	}

	public void setTipoCobranca(TipoCobranca tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public String getNmTipoVenda() {
		return nmTipoVenda;
	}

	public void setNmTipoVenda(String nmTipoVenda) {
		this.nmTipoVenda = nmTipoVenda;
	}

	public int getNrTroca() {
		return nrTroca;
	}

	public void setNrTroca(int nrTroca) {
		this.nrTroca = nrTroca;
	}

	public double getNrTotalPedido() {
		return nrTotalPedido;
	}

	public void setNrTotalPedido(double nrTotalPedido) {
		this.nrTotalPedido = nrTotalPedido;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Cfop getCfop() {
		return cfop;
	}

	public void setCfop(Cfop cfop) {
		this.cfop = cfop;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public IcmsEstado getIcmsEstado() {
		return icmsEstado;
	}

	public void setIcmsEstado(IcmsEstado icmsEstado) {
		this.icmsEstado = icmsEstado;
	}

	public int getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(int snStatus) {
		this.snStatus = snStatus;
	}

	public List<PedidoProduto> getPedidoProdutos() {
		return pedidoProdutos;
	}

	public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
		this.pedidoProdutos = pedidoProdutos;
	}

	public long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return Funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		Funcionario = funcionario;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public List<PedidoTransportadora> getPedidoTransportadoras() {
		return pedidoTransportadoras;
	}

	public void setPedidoTransportadoras(List<PedidoTransportadora> pedidoTransportadoras) {
		this.pedidoTransportadoras = pedidoTransportadoras;
	}
	
	public PedidoProduto addPedidoProduto(PedidoProduto pedidoProduto) {
		getPedidoProdutos().add(pedidoProduto);
		pedidoProduto.setPedido(this);

		return pedidoProduto;
	}

	public PedidoProduto removePedidoProduto(PedidoProduto pedidoProduto) {
		getPedidoProdutos().remove(pedidoProduto);
		pedidoProduto.setPedido(null);

		return pedidoProduto;
	}
		
	public String getNmTipoPedido() {
		return nmTipoPedido;
	}

	public void setNmTipoPedido(String nmTipoPedido) {
		this.nmTipoPedido = nmTipoPedido;
	}
	
	public double getNrValorDesconto() {
		return nrValorDesconto;
	}

	public void setNrValorDesconto(double nrValorDesconto) {
		this.nrValorDesconto = nrValorDesconto;
	}
	
	public double getNrPontosCompreGanhe() {
		return nrPontosCompreGanhe;
	}

	public void setNrPontosCompreGanhe(double nrPontosCompreGanhe) {
		this.nrPontosCompreGanhe = nrPontosCompreGanhe;
	}
	
	public double getNrTotalPrecoEncomenda() {
		return nrTotalPrecoEncomenda;
	}

	public void setNrTotalPrecoEncomenda(double nrTotalPrecoEncomenda) {
		this.nrTotalPrecoEncomenda = nrTotalPrecoEncomenda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPedido ^ (idPedido >>> 32));
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
		Pedido other = (Pedido) obj;
		if (idPedido != other.idPedido)
			return false;
		return true;
	}
}
