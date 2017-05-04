package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

public class FiltroCampanhaMaiorPrioridade implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long idCampanha;
	private int nrPrioridade;
	
	public FiltroCampanhaMaiorPrioridade(long idCampanha, int nrPrioridade){
		this.idCampanha = idCampanha;
		this.nrPrioridade =  nrPrioridade;
	}
	
	public long getIdCampanha() {
		return idCampanha;
	}
	public void setIdCampanha(long idCampanha) {
		this.idCampanha = idCampanha;
	}

	public int getNrPrioridade() {
		return nrPrioridade;
	}

	public void setNrPrioridade(int nrPrioridade) {
		this.nrPrioridade = nrPrioridade;
	}
}
