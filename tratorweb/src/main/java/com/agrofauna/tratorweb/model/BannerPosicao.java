package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the banner_posicao database table.
 * 
 */
@Entity
@Table(name="banner_posicao")
@NamedQuery(name="BannerPosicao.findAll", query="SELECT b FROM BannerPosicao b")
public class BannerPosicao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_banner_posicao")
	private long idBannerPosicao;

	@Column(name="nm_banner_posicao")
	private String nmBannerPosicao;

	@Column(name="sn_status")
	private boolean snStatus;
	
	//bi-directional many-to-one association to ProdutoBanner
	@OneToMany(mappedBy="bannerPosicao", fetch = FetchType.LAZY)
	private List<ProdutoBanner> produtoBanners;

	public BannerPosicao() {
	}

	public long getIdBannerPosicao() {
		return this.idBannerPosicao;
	}

	public void setIdBannerPosicao(long idBannerPosicao) {
		this.idBannerPosicao = idBannerPosicao;
	}

	public String getNmBannerPosicao() {
		return this.nmBannerPosicao;
	}

	public void setNmBannerPosicao(String nmBannerPosicao) {
		this.nmBannerPosicao = nmBannerPosicao;
	}

	public List<ProdutoBanner> getProdutoBanners() {
		return this.produtoBanners;
	}

	public void setProdutoBanners(List<ProdutoBanner> produtoBanners) {
		this.produtoBanners = produtoBanners;
	}

	public ProdutoBanner addProdutoBanner(ProdutoBanner produtoBanner) {
		getProdutoBanners().add(produtoBanner);
		produtoBanner.setBannerPosicao(this);

		return produtoBanner;
	}

	public ProdutoBanner removeProdutoBanner(ProdutoBanner produtoBanner) {
		getProdutoBanners().remove(produtoBanner);
		produtoBanner.setBannerPosicao(null);

		return produtoBanner;
	}

	public boolean getSnStatus() {
		return snStatus;
	}

	public void setSnStatus(boolean snStatus) {
		this.snStatus = snStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idBannerPosicao ^ (idBannerPosicao >>> 32));
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
		BannerPosicao other = (BannerPosicao) obj;
		if (idBannerPosicao != other.idBannerPosicao)
			return false;
		return true;
	}
}