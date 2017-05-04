package com.agrofauna.tratorweb.reltrab;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;

public class AjustarNome {
	
	public void ajustarNomeProduto(){
		try{			
			String queryAtividade = "SELECT ID_PRODUTO, NM_PRODUTO FROM mydb.produto ORDER BY NM_PRODUTO";
			Connection connAtividade = new ConexaoReltrab().getConnectionNovo();
			java.sql.PreparedStatement stmtAtividade = connAtividade.prepareStatement(queryAtividade);	    			
			ResultSet rsAtividade = stmtAtividade.executeQuery();	    			                
			int cont = 0;
			while(rsAtividade.next()) {
				
				try{
					
					//String nome  = StringUtils.capitalize(rsAtividade.getString("NM_PRODUTO"));
					String nome = WordUtils.capitalize(rsAtividade.getString("NM_PRODUTO"));	
					System.out.println("Codigo: "+ rsAtividade.getString("ID_PRODUTO") + " Nome: "+ nome);
					
					String query2 = "UPDATE mydb.produto SET NM_PRODUTO='"+ nome +"' WHERE ID_PRODUTO="+rsAtividade.getString("ID_PRODUTO"); 
							
					Connection connNovo = new ConexaoReltrab().getConnectionNovo();
	            	java.sql.PreparedStatement stmt = connNovo.prepareStatement(query2);
	            	stmt.execute(); 
	            	stmt.close();
	            	System.out.println("contando alterações: " + (cont++));
				}catch(Exception e){}	
			}
			stmtAtividade.close();
			connAtividade.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("termino de atualizar:");
	}
	
	public void ajustarNomeAtivos(){
		try{			
			String queryAtividade = "SELECT id_principio_ativo, nm_principio_ativo FROM mydb.principio_ativo ORDER BY nm_principio_ativo";
			Connection connAtividade = new ConexaoReltrab().getConnectionNovo();
			java.sql.PreparedStatement stmtAtividade = connAtividade.prepareStatement(queryAtividade);	    			
			ResultSet rsAtividade = stmtAtividade.executeQuery();	    			                
			int cont = 0;
			while(rsAtividade.next()) {				
				try{
					
					//String nome  = StringUtils.capitalize(rsAtividade.getString("nm_principio_ativo"));
					String nome = WordUtils.capitalize(rsAtividade.getString("nm_principio_ativo"));	
					System.out.println("Codigo: "+ rsAtividade.getString("id_principio_ativo") + " Nome: "+ nome);
					
					String query2 = "UPDATE mydb.principio_ativo SET nm_principio_ativo='"+ nome +"' WHERE id_principio_ativo="+rsAtividade.getString("id_principio_ativo"); 
							
					Connection connNovo = new ConexaoReltrab().getConnectionNovo();
	            	java.sql.PreparedStatement stmt = connNovo.prepareStatement(query2);
	            	stmt.execute(); 
	            	stmt.close();
	            	System.out.println("contando alterações: " + (cont++));
				}catch(Exception e){}	
			}
			stmtAtividade.close();
			connAtividade.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("termino de atualizar:");
	}
	
	public void ajustarEnderecoCliente(){
		try{			
			String queryAtividade = "SELECT F.CODCLI, F.ENDPRI, F.NUMRES, F.CXAPOS, F.BAIPRI, F.ESTPRI, F.CEPPRI, C.NOME"
					+" FROM"
					+" db_agrofauna.FATFCLIE F"
					+" INNER JOIN db_agrofauna.CIDADE C ON C.ID=F.CODCID"
					+" ORDER BY F.CODCLI";
			Connection connAtividade = new ConexaoReltrab().getConnectionVelho();
			java.sql.PreparedStatement stmtAtividade = connAtividade.prepareStatement(queryAtividade);	    			
			ResultSet rsAtividade = stmtAtividade.executeQuery();	    			                
			int cont = 0;
			while(rsAtividade.next()) {				
				try{
					
					//String nome  = StringUtils.capitalize(rsAtividade.getString("nm_principio_ativo"));
					//String nome = WordUtils.capitalize(rsAtividade.getString("nm_principio_ativo"));
					String cod = rsAtividade.getString("CODCLI");
					String end = WordUtils.capitalize(rsAtividade.getString("ENDPRI"));
					String num = rsAtividade.getString("NUMRES");
					String cxa = rsAtividade.getString("CXAPOS");
					String bai = WordUtils.capitalize(rsAtividade.getString("BAIPRI"));
					String est = WordUtils.capitalize(rsAtividade.getString("ESTPRI"));
					String cep = rsAtividade.getString("CEPPRI");
					String cid = WordUtils.capitalize(rsAtividade.getString("NOME"));
					
					long estado = 0;
					if(est.equalsIgnoreCase("ac")){
						estado = 1;
					}else if(est.equalsIgnoreCase("ac")){
						estado = 2;
					}else if(est.equalsIgnoreCase("al")){
						estado = 3;
					}else if(est.equalsIgnoreCase("am")){
						estado = 4;
					}else if(est.equalsIgnoreCase("ap")){
						estado = 5;
					}else if(est.equalsIgnoreCase("ba")){
						estado = 6;
					}else if(est.equalsIgnoreCase("ce")){
						estado = 7;
					}else if(est.equalsIgnoreCase("df")){
						estado = 8;
					}else if(est.equalsIgnoreCase("es")){
						estado = 9;
					}else if(est.equalsIgnoreCase("go")){
						estado = 10;
					}else if(est.equalsIgnoreCase("ma")){
						estado = 11;
					}else if(est.equalsIgnoreCase("mg")){
						estado = 12;
					}else if(est.equalsIgnoreCase("ms")){
						estado = 13;
					}else if(est.equalsIgnoreCase("mt")){
						estado = 14;
					}else if(est.equalsIgnoreCase("pa")){
						estado = 15;
					}else if(est.equalsIgnoreCase("pb")){
						estado = 16;
					}else if(est.equalsIgnoreCase("pe")){
						estado = 17;
					}else if(est.equalsIgnoreCase("pi")){
						estado = 18;
					}else if(est.equalsIgnoreCase("pr")){
						estado = 19;
					}else if(est.equalsIgnoreCase("rj")){
						estado = 20;
					}else if(est.equalsIgnoreCase("rn")){
						estado = 21;
					}else if(est.equalsIgnoreCase("ro")){
						estado = 22;
					}else if(est.equalsIgnoreCase("rr")){
						estado = 23;
					}else if(est.equalsIgnoreCase("rs")){
						estado = 24;
					}else if(est.equalsIgnoreCase("sc")){
						estado = 25;					
					}else if(est.equalsIgnoreCase("sp")){
						estado = 26;
					}else if(est.equalsIgnoreCase("to")){
						estado = 27;
					}
					
					String queryAtividade3 = "SELECT id_pessoa FROM mydb.pessoa WHERE id_fatfclie="+cod;							
					Connection connAtividade3 = new ConexaoReltrab().getConnectionNovo();
					java.sql.PreparedStatement stmtAtividade3 = connAtividade3.prepareStatement(queryAtividade3);	    			
					ResultSet rsAtividade3 = stmtAtividade3.executeQuery();
					while(rsAtividade3.next()) {				
						try{
														
							System.out.println("Codigo: "+ rsAtividade3.getString("id_pessoa"));				
							
							String query2 = "INSERT INTO mydb.endereco (nm_rua, nm_bairro, nm_caixa_postal, nm_cep, nm_cidade, nm_complemento, nm_numero, nr_tipo_endereco, id_estado ,id_pessoa) "
									+ "values ('"+ end +"', '"+ bai +"', '"+ cxa +"', '"+ cep +"', '"+ cid +"', '', '"+ num +"', 1, "+ estado +", "+rsAtividade3.getString("id_pessoa")+")"; 
									
							Connection connNovo = new ConexaoReltrab().getConnectionNovo();
			            	java.sql.PreparedStatement stmt = connNovo.prepareStatement(query2);
			            	stmt.execute(); 
			            	stmt.close();
			            	
			            	System.out.println("contando alterações: " + (cont++));
						}catch(Exception e){}						
					}
					stmtAtividade3.close();
					connAtividade3.close();
					
				}catch(Exception e){}	
			}
			stmtAtividade.close();
			connAtividade.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("termino de atualizar:");
	}
	
	public static void main(String[] args) {
		AjustarNome a = new AjustarNome();
		a.ajustarEnderecoCliente();
	}
}
