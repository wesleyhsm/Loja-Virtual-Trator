package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the produto_dados_tecnicos database table.
 * 
 */
@Entity
@Table(name="produto_dados_tecnicos")
@NamedQuery(name="ProdutoDadosTecnico.findAll", query="SELECT p FROM ProdutoDadosTecnico p")
public class ProdutoDadosTecnico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto_dados_tecnicos")
	private long idProdutoDadosTecnicos;

	@Column(name="desc_classificacao_toxica")
	private String descClassificacaoToxica;

	@Column(name="desc_formulacao")
	private String descFormulacao;

	@Column(name="desc_tipo_acao")
	private String descTipoAcao;
	
	//bi-directional many-to-one association to PrincipioAtivo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_principio_ativo")
	private PrincipioAtivo principioAtivo;

	//bi-directional many-to-one association to GrupoQuimico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_grupo_quimico")
	private GrupoQuimico grupoQuimico;
	
	//bi-directional many-to-one association to Produto
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	public ProdutoDadosTecnico() {
	}
			
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public long getIdProdutoDadosTecnicos() {
		return this.idProdutoDadosTecnicos;
	}

	public void setIdProdutoDadosTecnicos(long idProdutoDadosTecnicos) {
		this.idProdutoDadosTecnicos = idProdutoDadosTecnicos;
	}

	public String getDescClassificacaoToxica() {
		return this.descClassificacaoToxica;
	}

	public void setDescClassificacaoToxica(String descClassificacaoToxica) {
		this.descClassificacaoToxica = descClassificacaoToxica;
	}

	public String getDescFormulacao() {
		return this.descFormulacao;
	}

	public void setDescFormulacao(String descFormulacao) {
		this.descFormulacao = descFormulacao;
	}

	public String getDescTipoAcao() {
		return this.descTipoAcao;
	}

	public void setDescTipoAcao(String descTipoAcao) {
		this.descTipoAcao = descTipoAcao;
	}
	
	public PrincipioAtivo getPrincipioAtivo() {
		return this.principioAtivo;
	}

	public void setPrincipioAtivo(PrincipioAtivo principioAtivo) {
		this.principioAtivo = principioAtivo;
	}

	public GrupoQuimico getGrupoQuimico() {
		return this.grupoQuimico;
	}

	public void setGrupoQuimico(GrupoQuimico grupoQuimico) {
		this.grupoQuimico = grupoQuimico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ (int) (idProdutoDadosTecnicos ^ (idProdutoDadosTecnicos >>> 32));
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
		ProdutoDadosTecnico other = (ProdutoDadosTecnico) obj;
		if (idProdutoDadosTecnicos != other.idProdutoDadosTecnicos)
			return false;
		return true;
	}
	
}