package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;


/**
 * The persistent class for the campanha_imagem database table.
 * 
 */
@Entity
@Table(name="campanha_imagem")
@NamedQueries({
	@NamedQuery(name="CampanhaImagem.bannerCampanha", query="SELECT i FROM CampanhaImagem i")
})	
public class CampanhaImagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_campanha_imagem")
	private long idCampanhaImagem;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	private Date dtCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_atualizacao")
	private Date dtAtualizacao;
	
	@NotBlank
	@Column(name="nm_campanha_imagem")
	private String nmCampanhaImagem;
	
	@NotBlank
	@Column(name="nm_link")
	private String nmlink;
	
	@Column(name="sn_status")
	private boolean snStatus;
	
	@Column(name="nm_cor_lateral")
	private String nmCorLateral;
	
	@Column(name="nr_id_produto")
	private long nrIdProduto;
	
	@Column(name="nm_tipo_imagem")
	private String nmTipoImagem;
	
	@Transient
	private double precoProduto;
	
	@Transient
	private long idCampanhaProduto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa_funcionario")
	private Funcionario funcionario;
			
	public long getIdCampanhaProduto() {
		return idCampanhaProduto;
	}

	public void setIdCampanhaProduto(long idCampanhaProduto) {
		this.idCampanhaProduto = idCampanhaProduto;
	}

	public double getPrecoProduto() {
		return precoProduto;
	}

	public void setPrecoProduto(double precoProduto) {
		this.precoProduto = precoProduto;
	}

	public String getNmCorLateral() {
		return nmCorLateral;
	}

	public void setNmCorLateral(String nmCorLateral) {
		this.nmCorLateral = nmCorLateral;
	}

	public long getNrIdProduto() {
		return nrIdProduto;
	}

	public void setNrIdProduto(long nrIdProduto) {
		this.nrIdProduto = nrIdProduto;
	}

	public String getNmTipoImagem() {
		return nmTipoImagem;
	}

	public void setNmTipoImagem(String nmTipoImagem) {
		this.nmTipoImagem = nmTipoImagem;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public long getIdCampanhaImagem() {
		return this.idCampanhaImagem;
	}

	public void setIdCampanhaImagem(long idCampanhaImagem) {
		this.idCampanhaImagem = idCampanhaImagem;
	}

	public Date getDtCriacao() {
		return this.dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmCampanhaImagem() {
		return this.nmCampanhaImagem;
	}

	public void setNmCampanhaImagem(String nmCampanhaImagem) {
		this.nmCampanhaImagem = nmCampanhaImagem.trim();
	}

	public boolean getSnStatus() {
		return this.snStatus;
	}

	public void setSnStatus(boolean snStatus) {
		this.snStatus = snStatus;
	}
		
	public String getNmlink() {
		return nmlink;
	}

	public void setNmlink(String nmlink) {
		this.nmlink = nmlink.trim();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idCampanhaImagem ^ (idCampanhaImagem >>> 32));
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
		CampanhaImagem other = (CampanhaImagem) obj;
		if (idCampanhaImagem != other.idCampanhaImagem)
			return false;
		return true;
	}	
}