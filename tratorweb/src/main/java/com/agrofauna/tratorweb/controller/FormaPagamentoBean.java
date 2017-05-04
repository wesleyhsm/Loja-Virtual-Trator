package com.agrofauna.tratorweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.agrofauna.tratorweb.mail.MailConfigNormal;
import com.agrofauna.tratorweb.model.Cfop;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.FormaPagamento;
import com.agrofauna.tratorweb.model.Pedido;
import com.agrofauna.tratorweb.model.PedidoProduto;
import com.agrofauna.tratorweb.model.PedidoStatus;
import com.agrofauna.tratorweb.model.PedidoTipoFrete;
import com.agrofauna.tratorweb.model.Produto;
import com.agrofauna.tratorweb.model.Telefone;
import com.agrofauna.tratorweb.model.TipoCobranca;
import com.agrofauna.tratorweb.repository.EmailRepository;
import com.agrofauna.tratorweb.repository.IcmsEstadoRepository;
import com.agrofauna.tratorweb.repository.PedidoProdutoRepository;
import com.agrofauna.tratorweb.repository.PedidoRepository;
import com.agrofauna.tratorweb.repository.PedidoReserva;
import com.agrofauna.tratorweb.repository.PedidoStatusEmailEnviadosRepository;
import com.agrofauna.tratorweb.repository.TelefoneRepository;
import com.agrofauna.tratorweb.util.jsf.FacesUtil;
import com.agrofauna.tratorweb.util.relarotio.TodosRelatorio;

@Named
@ViewScoped
public class FormaPagamentoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CarrinhoDeComprasBean carrinhoDeComprasBean;
	
	@Inject
	private CarrinhodeComprasCompreGanheBean carrinhodeComprasCompreGanheBean;
	
	@Inject
	private CarrinhoDeComprasEncomendaBean carrinhoDeComprasEncomendaBean;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private PedidoProdutoRepository pedidoProdutoRepository;
	
	@Inject
	private IcmsEstadoRepository icmsEstadoRepository; 
			
	@Inject
	private EmailRepository emailRepository;
	
	@Inject
	private TelefoneRepository telefoneRepository;
	
	@Inject
	private MailConfigNormal mailConfigNormal;
	
	@Inject
	private TodosRelatorio todosRelatorio;
	
	@Inject
	private PedidoReserva pedidoReserva;
	
	@Inject
	private PedidoStatusEmailEnviadosRepository pedidoStatusEmailEnviadosRepository;
	
	private Pedido pedido = new Pedido();
	private List<PedidoProduto> pedidoProdutos = new ArrayList<PedidoProduto>();
	private FormaPagamento formaPagamento = new FormaPagamento();	
	private Cfop cfop = new Cfop();	
	private TipoCobranca tipoCobranca = new TipoCobranca();
	private boolean liberaBotaoBaixarPedido;
	private int tipoCompra = 0; 
	
	public FormaPagamentoBean(){
		this.formaPagamento.setIdFormaPagamento(1l);
		this.tipoCobranca.setIdTipoCobranca(27);
		this.liberaBotaoBaixarPedido = false;
	}
	
	public void finalizarPedido(){									
		
		if(loginBean.getSuframa() != null && loginBean.getSuframa().getSnRegular() == 1 && loginBean.getSuframa().getSnIsencao() == 1){
			cfop.setIdCfop(69l);//se cliente for suframa e ele tiver regular
			
		}else if(loginBean.getEstado().getIdEstado() == loginBean.getEstadoOrigem().getIdEstado()){
			cfop.setIdCfop(10l);//se cliente for do estado de sao paulo
			
		}else{
			cfop.setIdCfop(11l);//cliente for fora do estado de sao paulo
		}
		
		double totalPedido = 0;
		double totalClassificaPrecoEncomenda = 0;
		String mensagemTela = "";
		double adicionarPontosCompreGanhe = 0;
		
		PedidoStatus pedidoStatus = new PedidoStatus();
		pedidoStatus.setIdPedidoStatus(11l);
						
		PedidoTipoFrete tipoFrete = new PedidoTipoFrete();
				
		pedido.setCliente(loginBean.getCliente());
		pedido.setDtCriacao(new Date());
		pedido.setFormaPagamento(formaPagamento);
		pedido.setCfop(cfop);
		pedido.setIcmsEstado(icmsEstadoRepository.buscarIcmsEstado(loginBean.getEstadoOrigem().getIdEstado(), loginBean.getEstado().getIdEstado()));
		pedido.setPedidoStatus(pedidoStatus);
		pedido.setNmOberservacao("novo trator de compras");
		pedido.setNmTipoVenda("TRATOR");		
		pedido.setSnStatus(1);
		pedido.setTipoCobranca(tipoCobranca);
		pedido.setNrPrazoPagamento(0);
		pedido.setNrTroca(2);		
		pedido.setDtAtualizacao(new Date());		
		pedido.setDtVencimento(new Date());		
		
		int contadorCalculaTotalEncomenda = 0;		
		if(carrinhoDeComprasBean.isLiberaFinalizaCompra() == true){		
			for(int cont=0; cont < carrinhoDeComprasBean.getListaCarrinhoDeCompras().size() ; cont++){
				
				PedidoProduto pedidoProduto = new PedidoProduto();
				
				//dessa forma não da certo pq não tem como jogar os produtos para o reltrab
				//CampanhaProduto campanhaProduto = new CampanhaProduto();				
				//campanhaProduto.setIdProdutoCampanha(carrinhoDeComprasBean.getListaCarrinhoDeCompras().get(cont).getIdProdutoCampanha());				
				//pedidoProduto.setCampanhaProduto(campanhaProduto);
				
				//PRODUTOS DO COMPRE GANHE NÃO TEM CAMPANHA, JOGAR PRODUTO DIRETO
				Produto produto = new Produto();				
				produto.setIdProduto(carrinhoDeComprasBean.getListaCarrinhoDeCompras().get(cont).getIdProduto());												
				pedidoProduto.setProduto(produto);
				
				pedidoProduto.setNrPrecoVenda(carrinhoDeComprasBean.getListaCarrinhoDeCompras().get(cont).getValorUnitario());
				pedidoProduto.setNrQuantidadeProduto(carrinhoDeComprasBean.getListaCarrinhoDeCompras().get(cont).getQtdProduto() * carrinhoDeComprasBean.getListaCarrinhoDeCompras().get(cont).getQtdPorCaixa());
				pedidoProduto.setNrCompreGanhe(0);
				pedidoProduto.setNrPrecoCusto(0);
				pedidoProduto.setNrClassificaPrecoEncomenda(0);
				
				totalPedido = totalPedido + carrinhoDeComprasBean.getListaCarrinhoDeCompras().get(cont).getSubTotal();
							
				pedido.addPedidoProduto(pedidoProduto);
				
				//verifica se compre ganhe esta ativo
				if(loginBean.getClienteCompreGanhe().getSnStatus() == true){				
					//calcula pontos compre ganhe gerado pelo pedido 
					adicionarPontosCompreGanhe = adicionarPontosCompreGanhe + (totalPedido * 0.001);
				}
			}			
			pedido.setNmTipoPedido("COMUM"); //alterar para normal aqui
			pedido.setSnIncluirFrete(carrinhoDeComprasBean.isIncluirFrete());
			pedido.setNrPontosCompreGanhe(adicionarPontosCompreGanhe);
			tipoFrete.setIdPedidoTipoFrete(carrinhoDeComprasBean.getTipoFrete());
			mensagemTela = "Pedido finalizado com sucesso.";
						
		}else if(carrinhoDeComprasEncomendaBean.isLiberaFinalizaCompra() == true){
			for(int cont=0; carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().size() > cont; cont++){
				PedidoProduto pedidoProduto = new PedidoProduto();		
				
				//dessa forma não da certo pq não tem como jogar os produtos para o reltrab
				//CampanhaProduto campanhaProduto = new CampanhaProduto();				
				//campanhaProduto.setIdProdutoCampanha(carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getIdProdutoCampanha());								
				//pedidoProduto.setCampanhaProduto(campanhaProduto);				
				
				//PRODUTOS DO COMPRE GANHE NÃO TEM CAMPANHA, JOGAR PRODUTO DIRETO
				Produto produto = new Produto();				
				produto.setIdProduto(carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getIdProduto());												
				pedidoProduto.setProduto(produto);
				
				pedidoProduto.setNrPrecoVenda(carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getValorUnitario() / carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getClassificaPrecoEncomenda());
				pedidoProduto.setNrQuantidadeProduto(carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getQtdProduto() * carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getQtdPorCaixa());
				pedidoProduto.setNrCompreGanhe(0);
				pedidoProduto.setNrPrecoCusto(0);
				pedidoProduto.setNrClassificaPrecoEncomenda(carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getClassificaPrecoEncomenda());
				
				totalPedido = totalPedido + (carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getSubTotal() / carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getClassificaPrecoEncomenda());
				totalClassificaPrecoEncomenda = totalClassificaPrecoEncomenda + carrinhoDeComprasEncomendaBean.getListaCarrinhoDeCompras().get(cont).getClassificaPrecoEncomenda();
				
				pedido.addPedidoProduto(pedidoProduto);
				contadorCalculaTotalEncomenda = contadorCalculaTotalEncomenda + 1;
				//verifica se compre ganhe esta ativo
				if(loginBean.getClienteCompreGanhe().getSnStatus() == true){					
					//calcula pontos compre ganhe gerado pelo pedido 
					adicionarPontosCompreGanhe = adicionarPontosCompreGanhe + (totalPedido * 0.001);
				}
			}
			pedido.setNmTipoPedido("ENCOMENDA");
			pedido.setSnIncluirFrete(carrinhoDeComprasEncomendaBean.isIncluirFrete());
			pedido.setNrPontosCompreGanhe(adicionarPontosCompreGanhe);
			tipoFrete.setIdPedidoTipoFrete(carrinhoDeComprasEncomendaBean.getTipoFrete());
			mensagemTela = "Encomenda finalizada com sucesso.";
			
		}else if(carrinhodeComprasCompreGanheBean.isLiberaFinalizaCompra() == true){
			for(int cont=0; carrinhodeComprasCompreGanheBean.getListaCarrinhoDeCompras().size() > cont; cont++){
				PedidoProduto pedidoProduto = new PedidoProduto();								
				Produto produto = new Produto();
				//PRODUTOS DO COMPRE GANHE NÃO TEM CAMPANHA, JOGAR PRODUTO DIRETO
				produto.setIdProduto(carrinhodeComprasCompreGanheBean.getListaCarrinhoDeCompras().get(cont).getIdProduto());
												
				pedidoProduto.setProduto(produto);				
				pedidoProduto.setNrPrecoVenda(carrinhodeComprasCompreGanheBean.getListaCarrinhoDeCompras().get(cont).getValorUnitario());
				pedidoProduto.setNrQuantidadeProduto(carrinhodeComprasCompreGanheBean.getListaCarrinhoDeCompras().get(cont).getQtdProduto() * carrinhodeComprasCompreGanheBean.getListaCarrinhoDeCompras().get(cont).getQtdPorCaixa());
				pedidoProduto.setNrCompreGanhe(carrinhodeComprasCompreGanheBean.getListaCarrinhoDeCompras().get(cont).getValorUnitario());
				pedidoProduto.setNrPrecoCusto(0);
				pedidoProduto.setNrClassificaPrecoEncomenda(0);
				
				totalPedido = totalPedido + carrinhodeComprasCompreGanheBean.getListaCarrinhoDeCompras().get(cont).getSubTotal();
							
				pedido.addPedidoProduto(pedidoProduto);
				
			}
			pedido.setSnIncluirFrete(carrinhodeComprasCompreGanheBean.isIncluirFrete());
			pedido.setNmTipoPedido("COMPRE GANHE");
			pedido.setNrPontosCompreGanhe(totalPedido);
			tipoFrete.setIdPedidoTipoFrete(carrinhodeComprasCompreGanheBean.getTipoFrete());
			mensagemTela = "Pedido compre ganhe finalizado com sucesso.";
		}
				
		pedido.setNrTotalPedido(totalPedido);
		pedido.setPedidoTipoFrete(tipoFrete);
		
		if(totalClassificaPrecoEncomenda == 0 || contadorCalculaTotalEncomenda == 0){
			pedido.setNrTotalPrecoEncomenda(0);
		}else{	
			pedido.setNrTotalPrecoEncomenda( totalClassificaPrecoEncomenda / contadorCalculaTotalEncomenda );
		}	
		
		if(pedidoRepository.salvarPedido(pedido)){		
			pedido = pedidoRepository.buscarUltimoPedido(loginBean.getCliente());
			List<PedidoProduto> pedidoProdutos = pedidoProdutoRepository.pedidoProdutoDetalhado(pedido);
			List<Email> emails = emailRepository.buscarEmail(loginBean.getCliente());
			List<Telefone> telefones = telefoneRepository.buscarTelefone(loginBean.getCliente());
			liberaBotaoBaixarPedido = true;
					
			//aqui entra o codigo da reserva			
			pedidoReserva.pedidoRevervaProduto(pedido, pedidoProdutos);
			
			if(carrinhoDeComprasBean.isLiberaFinalizaCompra() == true){				
				mailConfigNormal.montagemEmailPedidoConvencional(loginBean.getCliente(), emails, telefones, pedido, pedidoProdutos);
				carrinhoDeComprasBean.removeTodosProdutoCarrinhoCompras();				
				tipoCompra = 1;
				
			}else if(carrinhoDeComprasEncomendaBean.isLiberaFinalizaCompra() == true){
				mailConfigNormal.montagemEmailPedidoEncomenda(loginBean.getCliente(), emails, telefones, pedido, pedidoProdutos);
				carrinhoDeComprasEncomendaBean.removeTodosProdutoCarrinhoCompras();
				tipoCompra = 2;
				
			}else if(carrinhodeComprasCompreGanheBean.isLiberaFinalizaCompra() == true){
				mailConfigNormal.montagemEmailPedidoCompreGanhe(loginBean.getCliente(), emails, telefones, pedido, pedidoProdutos);				
				carrinhodeComprasCompreGanheBean.removeTodosProdutoCarrinhoCompras();
				tipoCompra = 3;
			}
			
			//cadastra no log que teve pedido
			long idPessoa = 7059;
			if(loginBean.getFuncionario() == null){
				loginBean.atualizaLog();				
			}else{				
				loginBean.atualizaLogFuncionario();
				idPessoa = loginBean.getFuncionario().getIdPessoa(); 
			}	
			
			//cadastra os status do pedido para acompanhamento
			pedidoStatusEmailEnviadosRepository.salvarPedidoStatusEmail(pedido, 1, idPessoa, 3);
			pedidoStatusEmailEnviadosRepository.salvarPedidoStatusEmail(pedido, 3, idPessoa, 3);
			
			FacesUtil.addInfoMessage(mensagemTela);			
		}else{			
			FacesUtil.addErrorMessage("Selecionar forma de pagamento.");
		}		
	}

	public void downloadPedido(){
		if(tipoCompra == 1){			
			todosRelatorio.emitirComprasConvencional(pedido);
			
		}else if(tipoCompra == 2){			
			todosRelatorio.emitirComprasEncomenda(pedido);
			
		}else if(tipoCompra == 3){			
			todosRelatorio.emitirComprasCompreGanhe(pedido);			
		}
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Cfop getCfop() {
		return cfop;
	}

	public void setCfop(Cfop cfop) {
		this.cfop = cfop;
	}

	public TipoCobranca getTipoCobranca() {
		return tipoCobranca;
	}

	public void setTipoCobranca(TipoCobranca tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public List<PedidoProduto> getPedidoProdutos() {
		return pedidoProdutos;
	}

	public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
		this.pedidoProdutos = pedidoProdutos;
	}

	public boolean isLiberaBotaoBaixarPedido() {
		return liberaBotaoBaixarPedido;
	}

	public void setLiberaBotaoBaixarPedido(boolean liberaBotaoBaixarPedido) {
		this.liberaBotaoBaixarPedido = liberaBotaoBaixarPedido;
	}

	public int getTipoCompra() {
		return tipoCompra;
	}

	public void setTipoCompra(int tipoCompra) {
		this.tipoCompra = tipoCompra;
	}	
}
