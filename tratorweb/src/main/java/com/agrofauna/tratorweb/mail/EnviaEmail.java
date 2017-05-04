package com.agrofauna.tratorweb.mail;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.agrofauna.tratorweb.model.Email;

public class EnviaEmail {

    public static boolean send(String assunto, String corpo, List<Email> emials) {
        ////////////////////////////////////////////////////////////////////////
        // Cria um objeto auxiliar que irá guardar algumas informações necessárias para o envio do email.
        Properties properties = new Properties();
        // Endereço do smpt     (nome_da_propriedade , valor_da_propriedade)
        properties.put("mail.smtp.host", "mail.teste.com.br");
        // Informa que deve ser enviado de forma autenticada
        properties.put("mail.smtp.auth", "true");
       
        Authenticator authenticator = new Authenticator() {

            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                /* string: usuário que será autenticado
                 * string1: senha para autenticação do usuário
                 */
                return new PasswordAuthentication("comercial@teste.com.br", "teste");
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        MimeMessage mimeMessage = new MimeMessage(session);

        try {            
            // Remetente
            mimeMessage.setFrom(new InternetAddress("comercial@teste.com.br", "Agro-Fauna, Trator de Compras"));

            // Destinatário
            Email mail = new Email();
            mail.setNmEmail("comercial@teste.com.br");
            emials.add(mail);
            
            int qtdEmails = emials.size();                        
            Address[] arrAddresses = new Address[qtdEmails];
            for (int i = 0; i < qtdEmails; i++) {
                arrAddresses[i] = new InternetAddress(emials.get(i).getNmEmail());
            }            
            mimeMessage.setRecipients(Message.RecipientType.BCC, arrAddresses);
          
            // Data da mensagem
            mimeMessage.setSentDate(new Date());
            mimeMessage.setSubject(assunto);
            mimeMessage.setContent(corpo, "text/html");
            // Envia a mensagem
            Transport.send(mimeMessage);

            return true; // E-mail enviado com sucesso
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        return false;
    }
}
