package com.brevitylink.api.service;

import com.brevitylink.api.model.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${MAIL_USER}")
    private String username;

    @Value("${APP_URL}")
    private String url;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String buildForgotPasswordEmail(Users user, String token) {
        String link = url + "reset-password?token=" + token;
        return """
                <div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; text-align: center; border: 1px solid #eee; padding: 20px;'>
                
                    <h2 style='color: #333;'>Recuperação de senha</h2>
                
                    <p style='color: #666;'>Olá, <strong>%s</strong></p>
                
                    <p style='color: #666;'>Recebemos uma solicitação para redefinir sua senha.</p>
                
                    <div style='margin: 30px 0;'>
                        <a href='%s' 
                           style='background-color: #007bff; color: white; padding: 15px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;'>
                           Redefinir senha
                        </a>
                    </div>
                
                    <p style='font-size: 12px; color: #999;'>Se você não solicitou isso, ignore este email.</p>
                
                </div>
                """.formatted(user.getName(), link);
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar o email em formato HTML", e);
        }
    }
}
