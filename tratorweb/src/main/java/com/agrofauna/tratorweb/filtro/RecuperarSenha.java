package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class RecuperarSenha implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String nmCampo;
	private int nrTipo;
	
	public int getNrTipo() {
		return nrTipo;
	}

	public void setNrTipo(int nrTipo) {
		this.nrTipo = nrTipo;
	}

	public String getNmCampo() {
		return nmCampo;
	}

	public void setNmCampo(String nmCampo) {
		this.nmCampo = nmCampo;
	}
}
