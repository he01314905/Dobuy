package com.usedorder.controller;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    public static void sendEmail(String to, String subject, String content) throws MessagingException {
        // 設置郵件服務器的屬性
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail 的 SMTP 服務器
        properties.put("mail.smtp.port", "587"); // SMTP 端口
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // TLS 加密

        // 認證信息
        String username = "cia103g2.dobuy@gmail.com"; 
        String password = "rdvr ffgw xkot dzwm"; 

        // 創建一個會話對象
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // 創建一個郵件消息對象
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username)); // 發件人地址
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // 收件人地址
        message.setSubject(subject); // 郵件主題
        message.setText(content); // 郵件內容

        // 發送郵件
        Transport.send(message);
    }
}
