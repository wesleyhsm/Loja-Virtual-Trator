package com.agrofauna.tratorweb.filtro;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class FiltroAlterarSenha implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String novaSenha1;
	
	@NotBlank
	private String novaSenha2;
	
	public String getSenhaAtual() {
		return senhaAtual;
	}
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	public String getNovaSenha1() {
		return novaSenha1;
	}
	public void setNovaSenha1(String novaSenha1) {
		this.novaSenha1 = novaSenha1;
	}
	public String getNovaSenha2() {
		return novaSenha2;
	}
	public void setNovaSenha2(String novaSenha2) {
		this.novaSenha2 = novaSenha2;
	}
}
