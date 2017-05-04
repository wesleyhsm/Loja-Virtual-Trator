package com.agrofauna.tratorweb.reltrab;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoReltrab implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public  final String DATABASE_NAME = "db_agrofauna";
    public  final String DATABASE_NOVO = "mydb";
        
    public  String url = "jdbc:mysql://192.168.0.104:10049/" + DATABASE_NAME + "?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false&autoReconnect=true&holdResultsOpenOverStatementClose=true";
    public  String url2 = "jdbc:mysql://localhost:3306/" + DATABASE_NOVO + "?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false&autoReconnect=true&holdResultsOpenOverStatementClose=true";
    //public  String url = "jdbc:mysql://192.168.0.188:3306/" + DATABASE_NAME + "?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false&autoReconnect=true&holdResultsOpenOverStatementClose=true";
    //public  String url = "jdbc:mysql://192.168.0.191:10049/" + DATABASE_NAME + "?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false&autoReconnect=true&holdResultsOpenOverStatementClose=true";
    //public  String url2 = "jdbc:mysql://192.168.0.191:10049/" + DATABASE_NOVO + "?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false&autoReconnect=true&holdResultsOpenOverStatementClose=true";
    
    protected  String user = "SYSTEMUSER";
    protected  String passwd = "senha5dosistema1";
        
    protected  String user2 = "root";
    protected  String passwd2 = "root";
    
    public Connection getConnectionVelho() {
        try {
            return DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Connection getConnectionNovo() {
        try {
            return DriverManager.getConnection(url2, user2, passwd2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
