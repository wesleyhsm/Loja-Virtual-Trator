package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the produto_imagem database table.
 * 
 */
@Entity
@Table(name="produto_imagem")
@NamedQueries({
	@NamedQuery(name="ProdutoImagem.findAll", query="SELECT p FROM ProdutoImagem p"),
	@NamedQuery(name="ProdutoImagem.todasImagensProduto", query="SELECT i FROM CampanhaProduto c inner join c.produto p inner join p.produtoImagems i WHERE c.idProdutoCampanha=:idProdutoCampanha AND i.snStatus=true"),
	@NamedQuery(name="ProdutoImagem.todasImagensProdutoCompreGanhe", query="SELECT i FROM ProdutoImagem i WHERE i.produto.idProduto=:idProduto AND i.snStatus=true")
})	
public class ProdutoImagem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_imagem")
	private long idProdutoImagem;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_atualizacao")
	private Date dtAtualizacao;
	
	@Column(name="nm_produto_imagem")
	private String nmProdutoImagem;

	@Column(name="nm_link")
	private String nmLink;
	
	@Column(name="sn_status")
	private boolean snStatus;
	
	@Column(name="sn_principal")
	private boolean snPrincipal;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch = FetchType.EAGER) // TODO: Verificar
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_funcionario")
	private Funcionario funcionario;
		
	public boolean isSnPrincipal() {
		return snPrincipal;
	}

	public void setSnPrincipal(boolean snPrincipal) {
		this.snPrincipal = snPrincipal;
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

	public String getNmLink() {
		return nmLink;
	}

	public void setNmLink(String nmLink) {
		this.nmLink = nmLink;
	}

	public long getIdProdutoImagem() {
		return this.idProdutoImagem;
	}

	public void setIdProdutoImagem(long idProdutoImagem) {
		this.idProdutoImagem = idProdutoImagem;
	}

	public Date getDtCriacao() {
		return this.dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmProdutoImagem() {
		return this.nmProdutoImagem;
	}

	public void setNmProdutoImagem(String nmProdutoImagem) {
		this.nmProdutoImagem = nmProdutoImagem;
	}

	public boolean getSnStatus() {
		return this.snStatus;
	}

	public void setSnStatus(boolean snStatus) {
		this.snStatus = snStatus;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idProdutoImagem ^ (idProdutoImagem >>> 32));
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
		ProdutoImagem other = (ProdutoImagem) obj;
		if (idProdutoImagem != other.idProdutoImagem)
			return false;
		return true;
	}

}