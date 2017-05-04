package com.agrofauna.tratorweb.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PedidoMd5 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String md5(String senha) {
		String md5 = null;
		if(null == senha) return null;
		
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(senha.getBytes(), 0, senha.length());
			md5 = new BigInteger(1,digest.digest()).toString(16);
		    System.out.println(md5);
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return md5;
	}
}
