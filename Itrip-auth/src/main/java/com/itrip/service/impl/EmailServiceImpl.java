package com.itrip.service.impl;

import com.itrip.service.EmailService;
import redis.clients.jedis.Jedis;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {


    //发件人地址
    public final  String rectAddress="18279265280@163.com";
    //163邮箱的授权码
    public final String rectPassword="";

    private Jedis redis;



    @Override
    public void sendEmail(String mailTo, String activatedCode) {
        Properties props=new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置用户的传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的邮件服务器地址
        props.setProperty("mail.smtp.host", "smtp.163.com");
        //2.创建会话
        Session session= Session.getInstance(props);
        //根据会话创建信息对象
        MimeMessage message=new MimeMessage(session);
//         2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        try {
            message.setFrom(new InternetAddress(rectAddress));

            // 3. To: 收件人（可以增加多个收件人、抄送、密送）
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailTo));
            // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
            message.setSubject("好好学习，天天向上", "UTF-8");
            // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
            message.setContent("亲爱的用户您好，您的激活码为："+activatedCode, "text/html;charset=UTF-8");
            // 6. 设置发件时间
            message.setSentDate(new Date());
            // 7. 保存设置
            message.saveChanges();
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            transport.connect(rectAddress, rectPassword);
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean valiCode(String userCode,String clientCode) {
        String serverCCode=redis.get(userCode);
        if (userCode.equals(clientCode)){
            return true;
        }else {
            return false;
        }
    }

}
