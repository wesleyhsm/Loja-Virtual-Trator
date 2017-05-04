package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
    public  final String DATABASE_NOVO = "db_agro_matriz";
     
    public  String url2 = "jdbc:mysql://127.0.0.1:10049/" + DATABASE_NOVO;
    
    protected  String user = "teste";
    protected  String passwd = "teste";
        
    //protected  String user2 = "root";
    //protected  String passwd2 = "root";
    
    /*
    public Connection getConnectionVelho() {
        try {
            return DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    
    public Connection getConnectionNovo() {
        try {
            return DriverManager.getConnection(url2, user, passwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}