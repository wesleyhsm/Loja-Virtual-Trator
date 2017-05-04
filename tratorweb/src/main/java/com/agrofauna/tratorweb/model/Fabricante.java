package com.agrofauna.tratorweb.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the fabricante database table.
 * 
 */
@Entity
@Table(name="fabricante")
@NamedQueries({
	@NamedQuery(name="Fabricante.todos", query="SELECT f FROM Fabricante f WHERE f.snStatus=1 order by f.nmRazaoSocial")
})
public class Fabricante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_fabricante")
	private long idFabricante;

	@Column(name="nm_nome_fantasia")
	private String nmNomeFantasia;

	@Column(name="nm_razao_social")
	private String nmRazaoSocial;
	
	@Column(name="nm_status")
	private int snStatus;

	//bi-directional many-to-one association to Produto
	@OneToMany(mappedBy="fabricante", fetch = FetchType.LAZY)
	private List<Produto> produtos;

	@OneToMany(mappedBy="fabricante", fetch = FetchType.LAZY)
	private List<ClienteFabricante> clienteFabricantes = new ArrayList<>();
	
	public List<ClienteFabricante> getClienteFabricantes() {
		return clienteFabricantes;
	}

	public void setClienteFabricantes(List<ClienteFabricante> clienteFabricantes) {
		this.clienteFabricantes = clienteFabricantes;
	}

	public long getIdFabricante() {
		return this.idFabricante;
	}

	public void setIdFabricante(long idFabricante) {
		this.idFabricante = idFabricante;
	}
	
	public String getNmNomeFantasia() {
		return this.nmNomeFantasia;
	}

	public void setNmNomeFantasia(String nmNomeFantasia) {
		this.nmNomeFantasia = nmNomeFantasia;
	}

	public String getNmRazaoSocial() {
		return this.nmRazaoSocial;
	}

	public void setNmRazaoSocial(String nmRazaoSocial) {
		this.nmRazaoSocial = nmRazaoSocial;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto addProduto(Produto produto) {
		getProdutos().add(produto);
		produto.setFabricante(this);

		return produto;
	}

	public Produto removeProduto(Produto produto) {
		getProdutos().remove(produto);
		produto.setFabricante(null);

		return produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idFabricante ^ (idFabricante >>> 32));
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
		Fabricante other = (Fabricante) obj;
		if (idFabricante != other.idFabricante)
			return false;
		return true;
	}
}