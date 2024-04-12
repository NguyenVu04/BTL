package com.project.backend.email;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.backend.exceptionhandler.ExceptionLog;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private JavaMailSenderImpl mailSender;
    private final String form = "<div><img src='https://firebasestorage.googleapis.com/v0/b/database-6cded.appspot.com/o/ceobe.png?alt=media&token=e55a1c20-68bb-40d7-be5c-3e49a788eeda' style='width: 40%%; height: auto; border-radius: 10px; margin-left: 30%%'></div><div style='background-color: skyblue; font-family: Arial; padding: 2px 15px; border-radius: 10px;'><h1 style='color: red; font-size: 36px;'>From: %s</h1><p style='font-size: 24px;'>%s</p></div><button style='background-color: darkcyan; border: none; padding: 10px 20px; text-align: center; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer; border-radius: 10px;'><a href='#' style='text-decoration: none; color: white;'>GO TO CLASS</a></button>";
    @Autowired 
    private ExceptionLog exceptionLog;
    public EmailService() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("laughingjack750@gmail.com");
        mailSender.setPassword("qahl pkyt umdq eqzm");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
    }

    public boolean sendEmail (String from, String subject, String body, String to, List<Map.Entry<String, Resource>> files) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(String.format("%s,\n%s", from, body), String.format(form, from, body));
            if (files != null) {
                for (Map.Entry<String, Resource> file : files) {
                    helper.addAttachment(file.getKey(), file.getValue());
                }
            } 
            message = helper.getMimeMessage();
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            exceptionLog.log(e, this.getClass().getName());
            return false;
        }
    }
    public boolean sendEmail (String from, String subject, String body, List<String> to, List<Map.Entry<String, Resource>> files) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            for (String t : to) {
                helper.addTo(t);
            }
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(String.format("%s,\n%s", from, body), String.format(form, from, body));
            if (files != null) {
                for (Map.Entry<String, Resource> file : files) {
                    helper.addAttachment(file.getKey(), file.getValue());
                }
            } 
            message = helper.getMimeMessage();
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            exceptionLog.log(e, this.getClass().getName());
            return false;
        }
    }
}
