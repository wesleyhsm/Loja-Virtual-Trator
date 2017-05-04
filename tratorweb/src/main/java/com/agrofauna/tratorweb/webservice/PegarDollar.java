package com.agrofauna.tratorweb.webservice;

import java.io.IOException;
import java.io.Serializable;
  
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.HttpException;  
import org.apache.commons.httpclient.HttpStatus;  
import org.apache.commons.httpclient.methods.GetMethod;  
import org.apache.commons.httpclient.params.HttpMethodParams;  
  
public class PegarDollar implements Serializable{  
     
	 private static final long serialVersionUID = 1L;
	 private static String url = "http://www.debit.com.br/resumogratuito.php?info=novo_dolar";  
	 
	 private String dolarCompra;
	 private String dolarVenda;
	 private String euroCompra;
	 private String eurorVenda;
	 private String dolarDataAtualizazao;
	 private String dolarHoraAtualizazao;
	 
	 public PegarDollar(){ 
		 
		 HttpClient client = new HttpClient();  
		   
	     // Create a method instance.  
	     GetMethod method = new GetMethod(url);  
	       
	     // Provide custom retry handler is necessary  
	     method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,   
	             new DefaultHttpMethodRetryHandler(3, false));  
	   
	     try { 
	    	 System.out.println("ok1");
	    	 // Execute the method.  
	    	 int statusCode = client.executeMethod(method);  
		   
	    	 if (statusCode != HttpStatus.SC_OK) {  
	    		 System.err.println("Method failed: " + method.getStatusLine());  
	    	 }  
		   
	    	 // Read the response body.  
	    	 String pagina = method.getResponseBodyAsString();	       
	      	 
	    	 System.out.println("teste: " + pagina);
	    	 
	      	 setDolarCompra(pagina.substring(pagina.indexOf("Comercial") + 145, pagina.indexOf("Comercial") + 151));
	      	 //System.out.println("teste1: " + getDolarCompra());
	      	
	      	 setDolarVenda(pagina.substring(pagina.indexOf("Comercial") + 283, pagina.indexOf("Comercial") + 289));
	      	 //System.out.println("teste2: " + getDolarVenda());
	      	 	      	 	      	 
	      	 setEuroCompra(pagina.substring(pagina.indexOf("Real") + 141 , pagina.indexOf("Real") + 147));
	      	 //System.out.println("teste3: " + getEuroCompra());
	      	 
	      	 setEurorVenda(pagina.substring(pagina.indexOf("Real") + 279, pagina.indexOf("Real") + 285));
	      	 //System.out.println("teste4: " + getEurorVenda());
	      	 
	      	setDolarDataAtualizazao(pagina.substring(pagina.indexOf("Atualizado") + 11, pagina.length()-38));
	      	//System.out.println("teste5: " + getDolarDataAtualizazao());
	      	
	      	setDolarHoraAtualizazao(pagina.substring(pagina.indexOf("Atualizado") + 22, pagina.length()-32));
	      	//System.out.println("teste6: " + getDolarHoraAtualizazao());
	      	 
	     } catch (HttpException e) {  
	    	 System.err.println("Fatal protocol violation: " + e.getMessage());  
	    	 e.printStackTrace();  
	     } catch (IOException e) {  
	    	 System.err.println("Fatal transport error: " + e.getMessage());  
	    	 e.printStackTrace();  
	     } finally {    	   
	    	 method.releaseConnection();  
	     }    
	}
	 	 
	//public static void main(String[] args) {
	//	PegarDollar pegarDollar = new PegarDollar();
	//} 
	 
	public String getDolarCompra() {
		return dolarCompra;
	}

	public void setDolarCompra(String dolarCompra) {
		this.dolarCompra = dolarCompra;
	}

	public String getDolarVenda() {
		return dolarVenda;
	}

	public void setDolarVenda(String dolarVenda) {
		this.dolarVenda = dolarVenda;
	}

	public String getDolarDataAtualizazao() {
		return dolarDataAtualizazao;
	}

	public void setDolarDataAtualizazao(String dolarDataAtualizazao) {
		this.dolarDataAtualizazao = dolarDataAtualizazao;
	}

	public String getEuroCompra() {
		return euroCompra;
	}

	public void setEuroCompra(String euroCompra) {
		this.euroCompra = euroCompra;
	}

	public String getEurorVenda() {
		return eurorVenda;
	}

	public void setEurorVenda(String eurorVenda) {
		this.eurorVenda = eurorVenda;
	}

	public String getDolarHoraAtualizazao() {
		return dolarHoraAtualizazao;
	}

	public void setDolarHoraAtualizazao(String dolarHoraAtualizazao) {
		this.dolarHoraAtualizazao = dolarHoraAtualizazao;
	}
}