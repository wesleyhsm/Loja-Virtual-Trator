package com.agrofauna.tratorweb.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.agrofauna.tratorweb.model.Login;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;	
	private Login login;
	
	public UsuarioSistema(Login login, Collection<? extends GrantedAuthority> authorities) {
		super(login.getNmLogin(), login.getNmSenha(), authorities);
		this.login = login;
	}

	public Login getLogin() {
		return login;
	}
}
