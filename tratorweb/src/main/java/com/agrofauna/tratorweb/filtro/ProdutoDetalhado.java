package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

public class ProdutoDetalhado implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long id_produto;
	private String nm_produto;
	private String desc_concentracao_quimica;												 
	private long id_produto_campanha; 
	private double vl_preco_campanha;
	private long id_fabricante;
	private String nm_razao_social;
	private long id_grupo_quimico;
	private String nm_grupo_quimico;
	private long id_principio_ativo;
	private String nm_principio_ativo;
	private int qtd_especie_medida;
	private double nr_peso_bruto;
	private long id_campanha;
	private double nr_classifica_preco_encomenda;		
	private String sn_epi;
	private double nr_percentual_valor;
	private int nr_prioridade;
			
	public int getNr_prioridade() {
		return nr_prioridade;
	}
	public void setNr_prioridade(int nr_prioridade) {
		this.nr_prioridade = nr_prioridade;
	}
	public double getNr_percentual_valor() {
		return nr_percentual_valor;
	}
	public void setNr_percentual_valor(double nr_percentual_valor) {
		this.nr_percentual_valor = nr_percentual_valor;
	}
	public String getSn_epi() {
		return sn_epi;
	}
	public void setSn_epi(String sn_epi) {
		this.sn_epi = sn_epi;
	}
	public double getNr_classifica_preco_encomenda() {
		return nr_classifica_preco_encomenda;
	}
	public void setNr_classifica_preco_encomenda(double nr_classifica_preco_encomenda) {
		this.nr_classifica_preco_encomenda = nr_classifica_preco_encomenda;
	}
	public long getId_campanha() {
		return id_campanha;
	}
	public void setId_campanha(long id_campanha) {
		this.id_campanha = id_campanha;
	}
	public long getId_produto() {
		return id_produto;
	}
	public void setId_produto(long id_produto) {
		this.id_produto = id_produto;
	}
	public String getNm_produto() {
		return nm_produto;
	}
	public void setNm_produto(String nm_produto) {
		this.nm_produto = nm_produto;
	}
	public String getDesc_concentracao_quimica() {
		return desc_concentracao_quimica;
	}
	public void setDesc_concentracao_quimica(String desc_concentracao_quimica) {
		this.desc_concentracao_quimica = desc_concentracao_quimica;
	}
	public long getId_produto_campanha() {
		return id_produto_campanha;
	}
	public void setId_produto_campanha(long id_produto_campanha) {
		this.id_produto_campanha = id_produto_campanha;
	}
	public double getVl_preco_campanha() {
		return vl_preco_campanha;
	}
	public void setVl_preco_campanha(double vl_preco_campanha) {
		this.vl_preco_campanha = vl_preco_campanha;
	}
	public long getId_fabricante() {
		return id_fabricante;
	}
	public void setId_fabricante(long id_fabricante) {
		this.id_fabricante = id_fabricante;
	}
	public String getNm_razao_social() {
		return nm_razao_social;
	}
	public void setNm_razao_social(String nm_razao_social) {
		this.nm_razao_social = nm_razao_social;
	}
	public long getId_grupo_quimico() {
		return id_grupo_quimico;
	}
	public void setId_grupo_quimico(long id_grupo_quimico) {
		this.id_grupo_quimico = id_grupo_quimico;
	}
	public String getNm_grupo_quimico() {
		return nm_grupo_quimico;
	}
	public void setNm_grupo_quimico(String nm_grupo_quimico) {
		this.nm_grupo_quimico = nm_grupo_quimico;
	}
	public long getId_principio_ativo() {
		return id_principio_ativo;
	}
	public void setId_principio_ativo(long id_principio_ativo) {
		this.id_principio_ativo = id_principio_ativo;
	}
	public String getNm_principio_ativo() {
		return nm_principio_ativo;
	}
	public void setNm_principio_ativo(String nm_principio_ativo) {
		this.nm_principio_ativo = nm_principio_ativo;
	}
	public int getQtd_especie_medida() {
		return qtd_especie_medida;
	}
	public void setQtd_especie_medida(int qtd_especie_medida) {
		this.qtd_especie_medida = qtd_especie_medida;
	}
	public double getNr_peso_bruto() {
		return nr_peso_bruto;
	}
	public void setNr_peso_bruto(double nr_peso_bruto) {
		this.nr_peso_bruto = nr_peso_bruto;
	}
}
