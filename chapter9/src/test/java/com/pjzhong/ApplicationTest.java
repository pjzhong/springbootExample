package com.pjzhong;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.ITemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.File;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class ApplicationTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    ITemplateEngine engine;

    @Test
    public void sendSimpleMail () throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pj_zhong@163.com");
        message.setTo("935124604@qq.com");
        message.setSubject("主题：测试测试");
        message.setText("测试邮件");

        mailSender.send(message);
    }

    @Test
    public void sendAttachmentsMail() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("pj_zhong@163.com");
        helper.setTo("935124604@qq.com");
        helper.setSubject("主题:看附件");
        helper.setText("看附件");

        FileSystemResource file = new FileSystemResource(new File("01.jpg"));
        helper.addAttachment("attachement.jpg", file);

        mailSender.send(mimeMessage);
    }
}
