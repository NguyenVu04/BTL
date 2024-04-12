package com.project.backend.email;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.backend.exceptionhandler.ExceptionLog;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private JavaMailSenderImpl mailSender;
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
            helper.setText(body);
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
    public boolean sendEmail (String from, String subject, String body, List<String> to, List<MultipartFile> files) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            for (String t : to) {
                helper.addTo(t);
            }
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(body);
            if (files != null) {
                for (MultipartFile file : files) {
                    helper.addAttachment(file.getOriginalFilename(), file);
                }
            } 
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            exceptionLog.log(e);
            return false;
        }
    }
}
