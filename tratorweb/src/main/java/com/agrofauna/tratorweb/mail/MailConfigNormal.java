package com.agrofauna.tratorweb.mail;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.agrofauna.tratorweb.controller.LoginBean;
import com.agrofauna.tratorweb.filtro.EncomendaNovoProduto;
import com.agrofauna.tratorweb.model.Cliente;
import com.agrofauna.tratorweb.model.Contato;
import com.agrofauna.tratorweb.model.ContatoLogado;
import com.agrofauna.tratorweb.model.Email;
import com.agrofauna.tratorweb.model.Login;
import com.agrofauna.tratorweb.model.Pedido;
import com.agrofauna.tratorweb.model.PedidoProduto;
import com.agrofauna.tratorweb.model.Telefone;
import com.agrofauna.tratorweb.repository.EnderecoRepository;

public class MailConfigNormal implements Serializable{
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private EnderecoRepository enderecoRepository;
	
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
	private DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
	
    private static final String CABECALHO2 = "<html>" +
								   			"<head>" +
								   			"	<meta charset='utf-8'/>" +
								   			"</head>" +
								   			"<body> " +
								   			" <table align='center' width='100%' style='font-family: Arial, Times, Times New Roman, serif; max-width: 800px; min-width: 600px; font-size: 14px; border: 2px solid #414141; border-radius: 8px;' border='0' cellspacing='0' cellpadding='0'>" +
								   			"  <tbody>" +
								   			"    <tr style='background: rgb(39, 93, 170);'>" +
								   			"      <td style='padding: 15px 0;' align='center'>" +
								   			"		 <a href='http://agrofauna.com.br' target='_blank' style='color: #FFF; font-weight: bold; text-decoration: none; letter-spacing: 2px;'>" +
								   			"		 	<img src='http://images.agro-fauna.com.br/mail/logotipo.png' style='width: 500px; max-width: 90%'>" +
								   			"		 	<br/>" +
								   			"		 	WWW.AGROFAUNA.COM.BR" +
								   			"		 </a>    	" +
								   			"      </td>" +
								   			"    </tr>";
   	
    private static final String RODAPE2 =   "<tr>" +
										   "      <td style='background: #414141; padding: 10px 15px; color: #FFF;' align='center'>" +
										   "      <table width='100%' border='0' cellspacing='0' cellpadding='0'>" +
										   "      	<tr>" +
										   "      		<td align='center'>" +
										   "      			<p style='font-weight: bold; font-size: 16px; color: #FFFFFF;'>" +
										   "		 		Agro-Fauna Comércio de Insumos Ltda" +
										   "		 		</p>" +
										   "				<p style='font-size: 14px; line-height: 1.3; color: #FFFFFF;'>" +
										   "				Rua Doutor Coutinho Cavalcanti, 1171 - Jardim America<br/>" +
										   "				CEP 15055-300, São Jose do Rio Preto - SP<br/>" +
										   "				Telefone: (17) 3224-1233" +
										   "				</p>" +
										   "      		</td>" +
										   "      		<!-- <td width='200px' align='right'>" +
										   "      			<a href='http://tratordecompras.com.br' target='_blank'>" +
										   "      			<img src='http://images.agro-fauna.com.br/mail/trator.png' width='200px'>" +
										   "      			</a>" +
										   "      		</td> -->" +
										   "      	</tr>" +
										   "      </table>" +
										   "" +
										   "      </td>" +
										   "    </tr>" +
										   "  </tbody>" +
										   "</table>" +
										   "</body>" +
										   "</html>";
   	
	public Session getMailConfigAutentica(Properties props){				
		MailAuthenticator auth = new MailAuthenticator( props.getProperty("mail.username"), props.getProperty("mail.password"));			
		return Session.getInstance(props, auth);
	}
	
	public void enviarEmail(MimeMessage mensagem, Properties props) throws MessagingException, IOException{										
		Transport transport = getMailConfigAutentica(props).getTransport();
		transport.connect(props.getProperty("mail.server.host"), props.getProperty("mail.username"), props.getProperty("mail.password"));
		transport.sendMessage(mensagem, mensagem.getRecipients(MimeMessage.RecipientType.BCC));
		transport.close();			
	}
	
	public void montagemEmailContato(Contato contato){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
			
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));
			mensagem.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(props.getProperty("mail.username")));
			mensagem.setSubject("Contato (Deslogado) ("+ contato.getNmNome() + ")");
			mensagem.setSentDate(new Date());
			           			
			String msg = CABECALHO2 +
							"<td style=\"padding: 15px;\">\n" +
							"		  <p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"			  Tipo: <span style=\"color: #414141\">Contato (Sem sessão)</span>\n" +
							"		  </p>\n" +
							"		  <p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"			  Data: <span style=\"color: #414141\">"+ df.format(contato.getDtCriacao()) +"</span>\n" +
							"		  </p>\n" +
							"      	\n" +
							"      	<h1 style=\"border-bottom: 1px solid #CCC; font-size: 16px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
							"      		Informações do Cliente\n" +
							"      	</h1>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Nome:\n" +
							"      		<span style=\"font-weight: normal;\">"+ contato.getNmNome() +"</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		E-mail:\n" +
							"      		<span style=\"font-weight: normal;\">"+ contato.getNmEmail() +"</span>\n" +
							"      	</p>\n" +
							"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Telefone:\n" +
							"      		<span style=\"font-weight: normal;\">"+ contato.getNmTelefone() +"</span>\n" +
							"      	</p>\n" +
							"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Celular:\n" +
							"      		<span style=\"font-weight: normal;\">"+ contato.getNmCelular() +"</span>\n" +
							"      	</p>\n" +
							"\n" +
							"		<h1 style=\"border-bottom: 1px solid #CCC; font-size: 16px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
							"      		Mensagem\n" +
							"      	</h1>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Descrição:\n" +
							"      		<span style=\"font-weight: normal;\">"+ contato.getNmDescricao() +"</span>\n" +
							"      	</p>\n" +
							"      	\n" +
							"      </td>\n" +
							"    </tr>" +									
						 RODAPE2;	
			
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}
	
	public void montagemEmailRecuperarSenha(Cliente cliente, List<Email> emails, List<Telefone> telefones, Login login){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
			
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));
			//mensagem.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress( cliente.getEmails().get(0).getNmEmail()));
			mensagem.setSubject("Recuperar Senha ("+ cliente.getNmRazaoSocial() + ")");
			mensagem.setSentDate(new Date());
			           
			String htmlEmail = "";
            int qtdEmails = emails.size();            
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emails.get(i).getNmEmail());                
                htmlEmail = htmlEmail + emails.get(i).getNmEmail() +"; ";
            }            
            mensagem.setRecipients(Message.RecipientType.BCC, arrAddresses);
			
            String telefone =  "";
            for(Telefone fone: telefones){
            	telefone = telefone + fone.getNmTelefone() +"; ";
			}
            
			String msg = CABECALHO2 +
							"<tr>\n" +
							"      <td style=\"padding: 15px;\">\n" +
							"      	<p style=\"color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"      	Cliente: <span style=\"color: #414141\">"+ cliente.getIdPessoa() +"</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"      	Tipo: <span style=\"color: #414141\">RECUPERAR SENHA</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"      	Data: <span style=\"color: #414141\">"+ df.format(new Date()) +"</span>\n" +
							"      	</p>\n" +
							"		\n" +
							"		<br style=\"clear: both;\"/>\n" +
							"\n" +
							"      	<h1 style=\"border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
							"      		Informações do Cliente\n" +
							"      	</h1>\n" +
							"      	\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Razão Social:\n" +
							"      		<span style=\"font-weight: normal;\">"+ cliente.getNmRazaoSocial() +"</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		E-mail:\n" +
							"      		<span style=\"font-weight: normal;\">"+ htmlEmail +"</span>\n" +
							"      	</p>\n" +
							"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Telefone:\n" +
							"      		<span style=\"font-weight: normal;\">"+ telefone +"</span>\n" +
							"      	</p>\n" +
							"		\n" +
							"				<h1 style=\"border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
							"      		Informações de Acesso\n" +
							"      	</h1>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Usuário:\n" +
							"      		<span style=\"font-weight: normal;\">"+ login.getNmLogin() +"</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Senha:\n" +
							"      		<span style=\"font-weight: normal;\">"+ login.getNmSenha() +"</span>\n" +
							"      	</p>\n" +
							"      	\n" +
							"      </td>\n" +
							"    </tr>"+					
						RODAPE2;
									
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}
	
	public void montagemEmailRecuperarSenhaAdmin(Cliente cliente, List<Email> emails, List<Telefone> telefones){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
			
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));
			mensagem.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(props.getProperty("mail.username")));
			mensagem.setSubject("Recuperar Senha ("+ cliente.getNmRazaoSocial() + ")");
			mensagem.setSentDate(new Date());
					
			String htmlEmail = "";
            int qtdEmails = emails.size();            
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emails.get(i).getNmEmail());                
                htmlEmail = htmlEmail + emails.get(i).getNmEmail() +"; ";
            }            
            //mensagem.setRecipients(Message.RecipientType.BCC, arrAddresses);
			
            String telefone =  "";
            for(Telefone fone: telefones){
            	telefone = telefone + fone.getNmTelefone() +"; ";
			}
            
			String msg = CABECALHO2 +
							"<tr>\n" +
							"      <td style=\"padding: 15px;\">\n" +
							"      	<p style=\"color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"      	Cliente: <span style=\"color: #414141\">"+ cliente.getIdPessoa() +"</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"      	Tipo: <span style=\"color: #414141\">RECUPERAR SENHA</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"      	Data: <span style=\"color: #414141\">"+ df.format(new Date()) +"</span>\n" +
							"      	</p>\n" +
							"		\n" +
							"		<br style=\"clear: both;\"/>\n" +
							"\n" +
							"      	<h1 style=\"border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
							"      		Informações do Cliente\n" +
							"      	</h1>\n" +
							"      	\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Razão Social:\n" +
							"      		<span style=\"font-weight: normal;\">"+ cliente.getNmRazaoSocial() +"</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		E-mail:\n" +
							"      		<span style=\"font-weight: normal;\">"+ htmlEmail +"</span>\n" +
							"      	</p>\n" +
							"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Telefone:\n" +
							"      		<span style=\"font-weight: normal;\">"+ telefone +"</span>\n" +
							"      	</p>\n" +
							"      	\n" +
							"      </td>\n" +
							"    </tr>"+
						RODAPE2;
			
            mensagem.setContent(msg, "text/html; charset=utf-8");           
            enviarEmail(mensagem, props);			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}
	
	public void montagemEmailNovoCliente(Cliente cliente, Email mail, Telefone telefone){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
			
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));
			mensagem.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(props.getProperty("mail.username")));
			mensagem.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(mail.getNmEmail()));
			
			mensagem.setSubject("Cliente Cadastrado ("+cliente.getNmRazaoSocial() +")");
			mensagem.setSentDate(new Date());		
			
			String msg = CABECALHO2 +
					"<tr>\n" +
					"      <td style=\"padding: 15px;\">\n" +
					"		  <p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
					"			  Tipo: <span style=\"color: #414141\">Novo Cliente</span>\n" +
					"		  </p>\n" +
					"		  <p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
					"			  Data: <span style=\"color: #414141\">"+ df.format(new Date()) +"</span>\n" +
					"		  </p>\n" +
					"      	\n" +
					"      	<h1 style=\"border-bottom: 1px solid #CCC; font-size: 16px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
					"      		Informações do Cliente\n" +
					"      	</h1>\n" +
					"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		Nome:\n" +
					"      		<span style=\"font-weight: normal;\">"+ cliente.getNmRazaoSocial() +"</span>\n" +
					"      	</p>\n" +
					"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		CNPJ/CPF:\n" +
					"      		<span style=\"font-weight: normal;\">"+ cliente.getNmCnpjCpf() +"</span>\n" +
					"      	</p>\n" +
					"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		Nome Contato:\n" +
					"      		<span style=\"font-weight: normal;\">"+ cliente.getNmContato() +"</span>\n" +
					"      	</p>\n" +
					"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		E-Mail:\n" +
					"      		<span style=\"font-weight: normal;\">"+ mail.getNmEmail() +"</span>\n" +
					"      	</p>\n" +
					"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		Telefone:\n" +
					"      		<span style=\"font-weight: normal;\">"+ telefone.getNmTelefone() +"</span>\n" +
					"      	</p>\n" +
					"\n" +					
					"      </td>\n" +
					"    </tr>" +					
				 RODAPE2;	
			
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}	
		
	public void montagemEmailEncomendaNovoProduto(Cliente cliente, List<Email> emails, List<Telefone> telefones, List<EncomendaNovoProduto> listEncomendaNovoProdutos){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
						
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));						
			mensagem.setSubject("Encomenda Solicitada ("+ cliente.getNmRazaoSocial() +")");
			mensagem.setSentDate(new Date());
			
			//Destinatário
            Email mail = new Email();
            mail.setNmEmail(props.getProperty("mail.username"));
            emails.add(mail);
            
            String htmlEmail = "";
            int qtdEmails = emails.size();            
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emails.get(i).getNmEmail());
                
                if(!emails.get(i).getNmEmail().equalsIgnoreCase(mail.getNmEmail())){
                	htmlEmail = htmlEmail + emails.get(i).getNmEmail() +"; ";
                }
            }            
            mensagem.setRecipients(Message.RecipientType.BCC, arrAddresses);
			           
			String telefone = "";
			for(Telefone fone: telefones){
				telefone = telefone + fone.getNmTelefone() +"; ";				
			}
			
			String msg = CABECALHO2 +
					 "<tr>\n" +
					 "      <td style=\"padding: 15px;\">\n" +					 
					 "      	<p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
					 "      	Tipo: <span style=\"color: #414141\">ENC. PERSONALIZADA</span>\n" +
					 "      	</p>\n" +
					 "      	<p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
					 "      	Data: <span style=\"color: #414141\">"+ df.format(new Date()) +"</span>\n" +
					 "      	</p>\n" +
					 "		\n" +
					 "		<br style=\"clear: both;\"/>\n" +
					 "\n" +
					 "      	<h1 style=\"border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
					 "      		Informações do Cliente\n" +
					 "      	</h1>\n" +
					 "      	\n" +
					 "      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					 "      		Razão Social:\n" +
					 "      		<span style=\"font-weight: normal;\">"+ cliente.getNmRazaoSocial() +"</span>\n" +
					 "      	</p>\n" +
					 "      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					 "      		CPF/CNPJ:\n" +
					 "      		<span style=\"font-weight: normal;\">"+ cliente.getNmCnpjCpf() +"</span>\n" +
					 "      	</p>\n" +
					 "      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					 "      		Contato:\n" +
					 "      		<span style=\"font-weight: normal;\">"+ htmlEmail +" / "+ telefone +"</span>\n" +
					 "      	</p>\n" +
					 "      	\n" +
					 "      	<h1 style=\"border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
					 "      		Informações do Pedido\n" +
					 "      	</h1>\n" +
					 "      	\n" +
					 "      	<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"font-size: 15px;\">\n" +
					 "      		<tr align=\"center\" style=\"font-weight: bold; background: #79B2FF;\">\n" +
					 "      			<td width=\"auto\">Produto</td>\n" +
					 "      			<td width=\"85px\" style=\"font-size: 14px;\">Qtd.</td>\n" +
					 "				<td width=\"90px\" style=\"font-size: 14px;\">Preço Inicial (R$)</td>\n" +
					 "				<td width=\"90px\" style=\"font-size: 14px;\">Preço Final (R$)</td>\n" +
					 "      			<td width=\"30%\" style=\"font-size: 14px;\">Observação</td>\n" +
					 "      		</tr>\n" +
					 "      		<!-- produtos -->\n";
			
					 formatoDois.setMinimumFractionDigits(2); 
				 	 formatoDois.setParseBigDecimal (true);
				 	 for(EncomendaNovoProduto enp: listEncomendaNovoProdutos){	
				 		msg = msg +  "      		<tr style=\"background: #E1E1E1;\">\n"+
									 "      			<td style=\"padding: 5px; font-size: 14px;\">"+ enp.getNomeProduto() +"</td>\n" +
									 "      			<td align=\"center\" style=\"font-size: 14px;\">"+ enp.getQtdProduto() +"</td>\n" +
									 "					<td align=\"center\" style=\"font-size: 14px;\">"+ formatoDois.format(enp.getPrecoInicio()) +"</td>\n" +
									 "					<td align=\"center\" style=\"font-size: 14px;\">"+ formatoDois.format(enp.getPrecoFinal()) +"</td>\n" +
									 "      			<td align=\"center\" style=\"font-size: 14px;\">"+ enp.getMsgObservacao() +"</td>\n" +
									 "      		</tr>\n";
				 	 }
				 	 
				 	msg = msg + "	</table>\n" +
					 "      	\n" +
					 "      </td>\n" +
					 "    </tr>"+				
					RODAPE2;
											
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);						
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
			
	public void montagemEmailContatoLogado(ContatoLogado contatoLogado, List<Email> emails, List<Telefone> telefones){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
					
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));
			mensagem.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(props.getProperty("mail.username")));
			mensagem.setSubject("Contato (Logado) ("+ contatoLogado.getCliente().getNmRazaoSocial() +")");
			mensagem.setSentDate(new Date());
			
			//Destinatário
            Email mail = new Email();
            mail.setNmEmail(props.getProperty("mail.username"));
            emails.add(mail);
			
			String htmlEmail = "";
            int qtdEmails = emails.size();            
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emails.get(i).getNmEmail());
                
                if(!emails.get(i).getNmEmail().equalsIgnoreCase(mail.getNmEmail())){
                	htmlEmail = htmlEmail + emails.get(i).getNmEmail() +"; ";
                }
            }            
            mensagem.setRecipients(Message.RecipientType.BCC, arrAddresses);
			           
			String telefone = "";
			for(Telefone fone: telefones){
				telefone = telefone + fone.getNmTelefone() +"; ";				
			}
			
			String msg = CABECALHO2 +
					"<tr>\n" +
					"      <td style=\"padding: 15px;\">\n" +
					"		  <p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
					"			  Tipo: <span style=\"color: #414141\">Contato (Com sessão)</span>\n" +
					"		  </p>\n" +
					"		  <p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
					"			  Data: <span style=\"color: #414141\">"+ df.format(new Date()) +"</span>\n" +
					"		  </p>\n" +
					"      	\n" +
					"      	<h1 style=\"border-bottom: 1px solid #CCC; font-size: 16px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
					"      		Informações do Cliente\n" +
					"      	</h1>\n" +
					"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		Razão Social:\n" +
					"      		<span style=\"font-weight: normal;\">"+ contatoLogado.getCliente().getNmRazaoSocial() +"</span>\n" +
					"      	</p>\n" +
					"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		CPF/CNPJ:\n" +
					"      		<span style=\"font-weight: normal;\">"+ contatoLogado.getCliente().getNmCnpjCpf() +"</span>\n" +
					"      	</p>\n" +
					"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		E-mail:\n" +
					"      		<span style=\"font-weight: normal;\">"+ htmlEmail +"</span>\n" +
					"      	</p>\n" +
					"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		Telefone:\n" +
					"      		<span style=\"font-weight: normal;\">"+ telefone +"</span>\n" +
					"      	</p>\n" +
					"\n" +
					"		<h1 style=\"border-bottom: 1px solid #CCC; font-size: 16px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
					"      		Mensagem\n" +
					"      	</h1>\n" +
					"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		Setor:\n" +
					"      		<span style=\"font-weight: normal;\">"+ contatoLogado.getSetor().getNmSetor() +"</span>\n" +
					"      	</p>\n" +
					"		<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		Título:\n" +
					"      		<span style=\"font-weight: normal;\">"+ contatoLogado.getNmTituloAssunto() +"</span>\n" +
					"      	</p>\n" +
					"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
					"      		Descrição:\n" +
					"      		<span style=\"font-weight: normal;\">"+ contatoLogado.getNmMensagem() +"</span>\n" +
					"      	</p>\n" +
					"      	\n" +
					"      </td>\n" +
					"    </tr>"+												
				 RODAPE2;
						
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}
	
	public void montagemEmailPedidoConvencional(Cliente cliente, List<Email> emails, List<Telefone> telefones, Pedido pedido, List<PedidoProduto> pedidoProdutos){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
			
			formatoDois.setMinimumFractionDigits(2); 
			formatoDois.setParseBigDecimal (true);
			
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));			
			mensagem.setSubject("Pedido Recebido ("+ cliente.getNmRazaoSocial() +")");
			mensagem.setSentDate(new Date());
			
			// Destinatário
            //Email mail = new Email();
            //mail.setNmEmail(props.getProperty("mail.username"));
            //emails.add(mail);
			
			String htmlEmail = "";
            int qtdEmails = emails.size();            
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emails.get(i).getNmEmail());
                
               //if(!emails.get(i).getNmEmail().equalsIgnoreCase(mail.getNmEmail())){
                	htmlEmail = htmlEmail + emails.get(i).getNmEmail() +"; ";
               //}
            }            
            mensagem.setRecipients(Message.RecipientType.BCC, arrAddresses);
			
            //String incluirFrete = "Frete não incluso";
			//if(pedido.isSnIncluirFrete()) incluirFrete = "Frete incluso";
            
			String telefone = " / ";
			for(Telefone fone: telefones){
				telefone = telefone + fone.getNmTelefone() +"; ";				
			}
			
			String msg = CABECALHO2 + 
							"<tr>\n" +
							"      <td style=\"padding: 15px;\">\n" +
							"      	<p style=\"color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"      	Número do Pedido: <span style=\"color: #414141\">"+ pedido.getIdPedido() +"</span>\n" +
							"      	</p>\n" +
							"		  <p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"			  Status: <span style=\"color: #414141\">RECEBIDO</span>\n" +
							"		  </p>\n" +
							"		  <p style=\"float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;\">\n" +
							"			  Data: <span style=\"color: #414141\">"+ df.format(pedido.getDtCriacao()) +"</span>\n" +
							"		  </p>\n" +
							"\n" +
							"		  <br/>\n" +
							"		\n" +
							"      	<p style=\"margin-top: 20px; background: #FF7E80; border-radius: 5px; padding: 10px; font-size: 14px; font-weight: bold; color: #414141; text-align: center;\">\n" +
							"      		Aviso Importante:\n" +
							"      		<span style=\"font-weight: normal; color: #414141;\">\n" +
							"      		Seu pedido foi recebido com sucesso. Em breve encaminharemos com as informações para confirmação em seu e-mail.\n" +
							"			</span>\n" +
							"      	</p>\n" +							
							"      	\n" +
							"		<p style='text-align: center; padding: 15px 0 15px 0;'>"+
							"			<a href='http://www.agrofauna.com.br/SiteAgroFauna/hotsite/encomenda/pedido.xhtml?cc="+ cliente.getIdPessoa() +"&cp="+ pedido.getIdPedido() +"' "+
							"				style='background: #275daa; color: #FFF; padding: 15px 20px; border-radius: 5px; font-weight: bold; text-decoration: none;' >"+
							"				ACOMPANHAR PEDIDO"+
							"			</a>"+
							"		</p>"+
							"		\n" +
							"      	<h1 style=\"border-bottom: 1px solid #CCC; font-size: 16px; color: #0244ff; padding: 0 0 5px 0;\">\n" +
							"      		Informações do Cliente\n" +
							"      	</h1>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		Razão Social:\n" +
							"      		<span style=\"font-weight: normal;\">"+ cliente.getNmRazaoSocial() +"</span>\n" +
							"      	</p>\n" +
							"      	<p style=\"font-size: 16px; font-weight: bold;\">\n" +
							"      		CPF/CNPJ:\n" +
							"      		<span style=\"font-weight: normal;\">"+ cliente.getNmCnpjCpf() +"</span>\n" +
							"      	</p>\n" +
							"      	\n" +
							"      </td>\n" +
							"    </tr>"+
						RODAPE2;
									
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		montagemEmailPedidoConvencionalInterno(cliente, emails, telefones, pedido, pedidoProdutos);
	}
	
	public void montagemEmailPedidoConvencionalInterno(Cliente cliente, List<Email> emails, List<Telefone> telefones, Pedido pedido, List<PedidoProduto> pedidoProdutos){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
			
			formatoDois.setMinimumFractionDigits(2); 
			formatoDois.setParseBigDecimal (true);
			
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));			
			mensagem.setSubject("Pedido Recebido ("+ cliente.getNmRazaoSocial() +")");
			mensagem.setSentDate(new Date());
			mensagem.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(props.getProperty("mail.username")));
			
			// Destinatário
            Email mail = new Email();
            mail.setNmEmail(props.getProperty("mail.username"));
            emails.add(mail);
			
			String htmlEmail = "";
            int qtdEmails = emails.size();            
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emails.get(i).getNmEmail());
                
                if(!emails.get(i).getNmEmail().equalsIgnoreCase(mail.getNmEmail())){
                	htmlEmail = htmlEmail + emails.get(i).getNmEmail() +"; ";
                }
            }            
            //mensagem.setRecipients(Message.RecipientType.BCC, arrAddresses);
			
            String incluirFrete = "Frete não incluso";
			if(pedido.isSnIncluirFrete()) incluirFrete = "Frete incluso";
            
			String telefone = " / ";
			for(Telefone fone: telefones){
				telefone = telefone + fone.getNmTelefone() +"; ";				
			}
				
			String msg = CABECALHO2 +
		            "<tr>" +
		            "      <td style='padding: 15px;'>" +
		            "      	<p style='color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Número do Pedido: <span style='color: #414141'>"+ pedido.getIdPedido() +" (COMUM)</span>" +
		            "      	</p>" +
		            "      	<p style='float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Status: <span style='color: #414141'>CONFIRMADO</span>" +
		            "      	</p>" +
		            "      	<p style='float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Data: <span style='color: #414141'>"+ df.format(pedido.getDtCriacao()) +"</span>" +
		            "      	</p>" +
		            "" +
		            "      	<h1 style='border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;'>" +
		            "      		Informações do Cliente" +
		            "      	</h1>" +
		            "      	" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Razão Social:" +
		            "      		<span style='font-weight: normal;'>"+ cliente.getNmRazaoSocial() +"</span>" +
		            "      	</p>" +		            
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		CPF/CNPJ:" +
		            "      		<span style='font-weight: normal;'>"+ cliente.getNmCnpjCpf() +"</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Cidade:" +
		            "      		<span style='font-weight: normal;'>"+ enderecoRepository.buscarEndereco(cliente).getNmCidade() +" - "+ loginBean.getEstado().getSgEstado() +"</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Contato:" +
		            "      		<span style='font-weight: normal;'>"+ htmlEmail + telefone + "</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Frete:" +
		            "      		<span style='font-weight: normal;'>"+ pedido.getPedidoTipoFrete().getNmPedidoTipoFrete() +" ("+ incluirFrete +")</span>" +
		            "      	</p>" +
		            "      	<p style='width: 50%; font-size: 16px; font-weight: bold;'>" +
		            "      		Forma de Pagamento:" +
		            "      		<span style='font-weight: normal;'>"+ pedido.getFormaPagamento().getNmFormaPagamento() +"</span>" +
		            "      	</p>" +
		            "      	" +
		            "      	<h1 style='border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;'>" +
		            "      		Informações do Pedido" +
		            "      	</h1>" +
		            "      	" +
		            "      	<table width='100%' border='1' cellspacing='0' cellpadding='0' style='font-size: 15px;'>" +
		            "      		<tr align='center' style='font-weight: bold; background: #79B2FF;'>" +
		            "      			<td>Produto</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Qtd. de Caixas</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Preço Unit. (R$)</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Qtd. Por Caixa</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Subtotal (R$)</td>" +
		            "      		</tr>" +
		            "      		<!-- produtos -->";
		            
					for(PedidoProduto pedpro: pedidoProdutos){
						msg = msg + "      		<tr style='background: #E1E1E1;'>" +
						            "      			<td style='padding: 5px; font-size: 14px;'>"+ pedpro.getProduto().getNmProduto() +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ (pedpro.getNrQuantidadeProduto() / pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida()) +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ formatoDois.format(pedpro.getNrPrecoVenda()) +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida() +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ formatoDois.format((pedpro.getNrQuantidadeProduto() / pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida()) * pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida() * pedpro.getNrPrecoVenda()) +"</td>" +
						            "      		</tr>";
					}	
		             
					msg = msg + 	"      		<!-- valor total -->" +
						            "      		<tr style='font-weight: bold; background: #999;'>" +
						            "      			<td colspan='4' style='padding: 5px; font-size: 14px;'>VALOR TOTAL</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ formatoDois.format(pedido.getNrTotalPedido()) +"</td>" +
						            "      		</tr>" +
						            "		</table>" +
						            "      	" +
						            "      </td>" +
						            "    </tr>" +			
						            RODAPE2;	
			
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}
	
	public void montagemEmailPedidoEncomenda(Cliente cliente, List<Email> emails, List<Telefone> telefones, Pedido pedido, List<PedidoProduto> pedidoProdutos){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
			
			formatoDois.setMinimumFractionDigits(2); 
			formatoDois.setParseBigDecimal (true);
			
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)"));			
			mensagem.setSubject("Pedido Recebido ("+ cliente.getNmRazaoSocial() +")");
			mensagem.setSentDate(new Date());
			
			// Destinatário
            Email mail = new Email();
            mail.setNmEmail(props.getProperty("mail.username"));
            emails.add(mail);
			
            String htmlEmail = "";
            int qtdEmails = emails.size();            
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emails.get(i).getNmEmail());
                
                if(!emails.get(i).getNmEmail().equalsIgnoreCase(mail.getNmEmail())){
                	htmlEmail = htmlEmail + emails.get(i).getNmEmail() +"; ";
                }
            }            
            mensagem.setRecipients(Message.RecipientType.BCC, arrAddresses);
			
            String incluirFrete = "Frete não incluso";
			if(pedido.isSnIncluirFrete()) incluirFrete = "Frete incluso";
            
			String telefone = " / ";
			for(Telefone fone: telefones){
				telefone = telefone + fone.getNmTelefone() +"; ";				
			}
			
			String msg = CABECALHO2 +
		            "<tr>" +
		            "      <td style='padding: 15px;'>" +
		            "      	<p style='color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Número do Pedido: <span style='color: #414141'>"+ pedido.getIdPedido() +" (ENCOMENDA)</span>" +
		            "      	</p>" +
		            "      	<p style='float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Status: <span style='color: #414141'>CONFIRMADO</span>" +
		            "      	</p>" +
		            "      	<p style='float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Data: <span style='color: #414141'>"+ df.format(pedido.getDtCriacao()) +"</span>" +
		            "      	</p>" +
		            "      	<h1 style='border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;'>" +
		            "      		Informações do Cliente" +
		            "      	</h1>" +		            
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Razão Social:" +
		            "      		<span style='font-weight: normal;'>"+ cliente.getNmRazaoSocial() +"</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		CPF/CNPJ:" +
		            "      		<span style='font-weight: normal;'>"+ cliente.getNmCnpjCpf() +"</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Contato:" +
		            "      		<span style='font-weight: normal;'>"+ htmlEmail + telefone + "</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Frete:" +
		            "      		<span style='font-weight: normal;'>"+ pedido.getPedidoTipoFrete().getNmPedidoTipoFrete() +" ("+ incluirFrete +")</span>" +
		            "      	</p>" +
		            "      	<p style='width: 50%; font-size: 16px; font-weight: bold;'>" +
		            "      		Forma de Pagamento:" +
		            "      		<span style='font-weight: normal;'>"+ pedido.getFormaPagamento().getNmFormaPagamento() +"</span>" +
		            "      	</p>" +		            
		            "      	<h1 style='border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;'>" +
		            "      		Informações do Pedido" +
		            "      	</h1>" +		            
		            "      	<table width='100%' border='1' cellspacing='0' cellpadding='0' style='font-size: 15px;'>" +
		            "      		<tr align='center' style='font-weight: bold; background: #79B2FF;'>" +
		            "      			<td width='auto'>Produto</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Qtd. de Caixas</td>" +
		            "      			<td width='18%' style='font-size: 14px;'>Preço Unit. (R$)</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Qtd. Por Caixa</td>" +
		            "      			<td width='18%' style='font-size: 14px;'>Subtotal (R$)</td>" +
		            "      		</tr>" +
		            "      		<!-- produtos -->";
		            
					for(PedidoProduto pedpro: pedidoProdutos){
						msg = msg + "      		<tr style='background: #E1E1E1;'>" +
						            "      			<td style='padding: 5px; font-size: 14px;'>"+ pedpro.getProduto().getNmProduto() +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ (pedpro.getNrQuantidadeProduto() / pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida()) +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>Entre "+ formatoDois.format(pedpro.getNrPrecoVenda()) + " e " + formatoDois.format(pedpro.getNrPrecoVenda() / pedpro.getNrClassificaPrecoEncomenda()) + "</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida() +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ formatoDois.format((pedpro.getNrQuantidadeProduto() / pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida()) * pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida() * pedpro.getNrPrecoVenda()) +" e "+ formatoDois.format(((pedpro.getNrQuantidadeProduto() / pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida()) * pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida() * pedpro.getNrPrecoVenda()) / pedpro.getNrClassificaPrecoEncomenda()) + "</td>" +
						            "      		</tr>";
					}	
		             
					msg = msg + 	"      		<!-- valor total -->" +
						            "      		<tr style='font-weight: bold; background: #999;'>" +
						            "      			<td colspan='4' style='padding: 5px; font-size: 14px;'>VALOR TOTAL</td>" +
						            "      			<td align='center' style='font-size: 14px;'>Entre "+ formatoDois.format(pedido.getNrTotalPedido()) + " e "+ formatoDois.format(pedido.getNrTotalPedido() / pedido.getNrTotalPrecoEncomenda()) +"</td>" +
						            "      		</tr>" +
						            "		</table>" +
						            "      	" +
						            "      </td>" +
						            "    </tr>" +			
						            RODAPE2;
            									
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}
	
	public void montagemEmailPedidoCompreGanhe(Cliente cliente, List<Email> emails, List<Telefone> telefones, Pedido pedido, List<PedidoProduto> pedidoProdutos){		
		try {			
			Properties props = new Properties();		
			props.load(getClass().getResourceAsStream("/mail.properties"));				
			props.getProperty("mail.server.host");
			props.getProperty("mail.server.port");
			props.getProperty("mail.enable.ssl");
			props.getProperty("mail.enable.tls");
			props.getProperty("mail.auth");
			props.getProperty("mail.username");
			props.getProperty("mail.password");
			
			formatoDois.setMinimumFractionDigits(2); 
			formatoDois.setParseBigDecimal (true);
			
			MimeMessage mensagem = new MimeMessage( getMailConfigAutentica(props) );
			mensagem.setFrom(new InternetAddress( props.getProperty("mail.username"), "Trator de Compras (Agro-Fauna)" ));			
			mensagem.setSubject("Pedido Recebido ("+ cliente.getNmRazaoSocial() +")");
			mensagem.setSentDate(new Date());
			
			// Destinatário
            Email mail = new Email();
            mail.setNmEmail(props.getProperty("mail.username"));
            emails.add(mail);
			
            String htmlEmail = "";
            int qtdEmails = emails.size();            
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emails.get(i).getNmEmail());
                
                if(!emails.get(i).getNmEmail().equalsIgnoreCase(mail.getNmEmail())){
                	htmlEmail = htmlEmail + emails.get(i).getNmEmail() +"; ";
                }
            }            
            mensagem.setRecipients(Message.RecipientType.BCC, arrAddresses);
			
            String incluirFrete = "Frete não incluso";
			if(pedido.isSnIncluirFrete()) incluirFrete = "Frete incluso";
            
			String telefone = " / ";
			for(Telefone fone: telefones){
				telefone = telefone + fone.getNmTelefone() +"; ";				
			}
			
			String msg = CABECALHO2 +
		            "<tr>" +
		            "      <td style='padding: 15px;'>" +
		            "      	<p style='color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Número do Pedido: <span style='color: #414141'>"+ pedido.getIdPedido() +" (COMUM)</span>" +
		            "      	</p>" +
		            "      	<p style='float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Status: <span style='color: #414141'>CONFIRMADO</span>" +
		            "      	</p>" +
		            "      	<p style='float: left; width: 50%; margin-top: 0; color: #0244F7; font-weight: bold; font-size: 20px;'>" +
		            "      	Data: <span style='color: #414141'>"+ df.format(pedido.getDtCriacao()) +"</span>" +
		            "      	</p>" +
		            "" +
		            "      	<h1 style='border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;'>" +
		            "      		Informações do Cliente" +
		            "      	</h1>" +
		            "      	" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Razão Social:" +
		            "      		<span style='font-weight: normal;'>"+ cliente.getNmRazaoSocial() +"</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		CPF/CNPJ:" +
		            "      		<span style='font-weight: normal;'>"+ cliente.getNmCnpjCpf() +"</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Contato:" +
		            "      		<span style='font-weight: normal;'>"+ htmlEmail + telefone + "</span>" +
		            "      	</p>" +
		            "      	<p style='font-size: 16px; font-weight: bold;'>" +
		            "      		Frete:" +
		            "      		<span style='font-weight: normal;'>"+ pedido.getPedidoTipoFrete().getNmPedidoTipoFrete() +" ("+ incluirFrete +")</span>" +
		            "      	</p>" +
		            "      	<p style='width: 50%; font-size: 16px; font-weight: bold;'>" +
		            "      		Forma de Pagamento:" +
		            "      		<span style='font-weight: normal;'>"+ pedido.getFormaPagamento().getNmFormaPagamento() +"</span>" +
		            "      	</p>" +
		            "      	" +
		            "      	<h1 style='border-bottom: 1px solid #CCC; font-size: 18px; color: #0244ff; padding: 0 0 5px 0;'>" +
		            "      		Informações do Pedido" +
		            "      	</h1>" +
		            "      	" +
		            "      	<table width='100%' border='1' cellspacing='0' cellpadding='0' style='font-size: 15px;'>" +
		            "      		<tr align='center' style='font-weight: bold; background: #79B2FF;'>" +
		            "      			<td>Produto</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Qtd. de Caixas</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Pontos Unit. (R$)</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Qtd. Por Caixa</td>" +
		            "      			<td width='85px' style='font-size: 14px;'>Subtotal (R$)</td>" +
		            "      		</tr>" +
		            "      		<!-- produtos -->";
		            
					for(PedidoProduto pedpro: pedidoProdutos){
						msg = msg + "      		<tr style='background: #E1E1E1;'>" +
						            "      			<td style='padding: 5px; font-size: 14px;'>"+ pedpro.getProduto().getNmProduto() +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ (pedpro.getNrQuantidadeProduto() / pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida()) +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ formatoDois.format(pedpro.getNrPrecoVenda()) +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida() +"</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ formatoDois.format((pedpro.getNrQuantidadeProduto() / pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida()) * pedpro.getProduto().getProdutoArmazenamento().getQtdEspecieMedida() * pedpro.getNrPrecoVenda()) +"</td>" +
						            "      		</tr>";
					}	
		             
					msg = msg + 	"      		<!-- valor total -->" +
						            "      		<tr style='font-weight: bold; background: #999;'>" +
						            "      			<td colspan='4' style='padding: 5px; font-size: 14px;'>TOTAL (PONTOS)</td>" +
						            "      			<td align='center' style='font-size: 14px;'>"+ formatoDois.format(pedido.getNrTotalPedido()) +"</td>" +
						            "      		</tr>" +
						            "		</table>" +
						            "      	" +
						            "      </td>" +
						            "    </tr>" +			
						            RODAPE2;	
									
            mensagem.setContent(msg, "text/html; charset=utf-8");            
			enviarEmail(mensagem, props);			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}
}
