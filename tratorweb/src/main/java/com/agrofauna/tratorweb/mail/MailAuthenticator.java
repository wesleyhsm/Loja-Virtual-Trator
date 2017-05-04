package com.agrofauna.tratorweb.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
	
	private String usuario;
    private String senha;
  
    public MailAuthenticator(String usuario, String senha) {
    	this.usuario = usuario;
    	this.senha = senha;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
    	return  new PasswordAuthentication(usuario,senha);
    }
}
