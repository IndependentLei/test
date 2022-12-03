package com.shebao.test.controller;

import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

import static com.shebao.test.controller.MailConst.NOTIFICATION_MAIL_CONTENT;
import static com.shebao.test.controller.MailConst.NOTIFICATION_MAIL_TITLE;

@RestController
public class Send163EMailController {


    public void  send(){

    }
    public static void main(String[] args) {
        MailSenderUtil.sendMailTOSingleUser("1265105759@qq.com","哈哈哈哈哈哈哈哈哈","哈哈哈哈哈哈哈哈哈");
    }

}



class MailConst {
    //你的进行发送邮件的邮箱
    public static final String USER_NAME = "jdl1026@163.com";
    //授权密码
    public static final String  PWD_CODE = "CHSXJMJKXXADFZSY";
    public static final String MAIL_HOST = "smtp.163.com";
    public static final String MAIL_SMTP_AUTH = "true";

    //邮件主题
    public static final String NOTIFICATION_MAIL_TITLE = "xxxxxxxxx邮件主题";
    //邮件内容
    public static final String NOTIFICATION_MAIL_CONTENT = "xxxxxxxxx你有一个新的通知，请前往xxxx进行查看！";

}

class MailSenderUtil {


    //建立服务器的邮件连接，从而可以进行发送邮件
    public static Session getMailSession(){
        //0.1 确定连接位置
        Properties properties = new Properties();
        //获取163邮箱smtp服务器的地址，
        properties.setProperty("mail.host", MailConst.MAIL_HOST);
        //是否进行权限验证。
        properties.setProperty("mail.smtp.auth", MailConst.MAIL_SMTP_AUTH);
        //0.2确定权限（账号和密码）
        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //填写自己的163邮箱的登录帐号和授权密码（不是登录密码）。
                return new PasswordAuthentication(MailConst.USER_NAME,MailConst.PWD_CODE);
            }
        };
        //1 获得连接
        /**
         * properties：包含配置信息的对象，Properties类型
         *         配置邮箱服务器地址、配置是否进行权限验证(帐号密码验证)等
         *
         * authenticator：确定权限(帐号和密码)
         *
         * 所以就要在上面构建这两个对象。
         */
        Session session = Session.getDefaultInstance(properties, authenticator);
        return session;
    }

    /**
     *  指定一个人发送邮件，单个发送
     * @Param toEmailAddress  单个接受邮件的邮箱地址
     * @Param title 主题
     * @Param content  内容
     * @Author 林泽鸿
     * @Date 2020/4/3 22:00
     **/
    public static void sendMailTOSingleUser(String toEmailAddress,String title, String content){
        //获得邮箱服务器的连接
        Session session = getMailSession();
        //2 创建消息
        Message message = new MimeMessage(session);
        // 2.1 发件人 我们自己的邮箱地址，就是名称
        try {
            message.setFrom(new InternetAddress(MailConst.USER_NAME));
            /**
             * 2.2 收件人
             *         第一个参数：
             *             RecipientType.TO    代表收件人
             *             RecipientType.CC    抄送
             *             RecipientType.BCC    暗送
             *         比如A要给B发邮件，但是A觉得有必要给要让C也看看其内容，就在给B发邮件时，
             *         将邮件内容抄送给C，那么C也能看到其内容了，但是B也能知道A给C抄送过该封邮件
             *         而如果是暗送(密送)给C的话，那么B就不知道A给C发送过该封邮件。
             *     第二个参数
             *         收件人的地址，或者是一个Address[]，用来装抄送或者暗送人的名单。或者用来群发。可以是相同邮箱服务器的，也可以是不同的
             *         这里我们发送给我们的qq邮箱
             */
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmailAddress));
            //设置抄送人,这里设置自己的是抄送人，免得被网易邮箱列为垃圾邮件
            message.setRecipient(Message.RecipientType.CC, new InternetAddress( MailConst.USER_NAME ));
            // 2.3 主题（标题）
            message.setSubject(NOTIFICATION_MAIL_TITLE);
            //设置编码，防止发送的内容中文乱码。
            message.setContent(content, "text/html;charset=UTF-8");
            //3发送消息
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发邮件,也可以只发一个人。可设置主题和邮件内容，无附件
     * @Param toEmailAddressArray 接受邮件的所有邮箱地址
     * @Param title 主题
     * @Param content  内容
     * @Author 林泽鸿
     * @Date 2020/4/3 21:59
     **/
    public static void sendMailToUserArray(ArrayList<String> toEmailAddressArrayList , String title, String content) {
        String[] toEmailAddressArray = (String[]) toEmailAddressArrayList.toArray(new String[0]);
        //获得邮箱服务器的连接
        Session session = getMailSession();
        //2 创建消息
        Message message = new MimeMessage(session);
        // 2.1 发件人 我们自己的邮箱地址，就是名称
        try {
            message.setFrom(new InternetAddress(MailConst.USER_NAME));

            if (toEmailAddressArray != null) {
                // 为每个邮件接收者创建一个地址
                Address[] toEmailRecipients = null;
                toEmailRecipients = new InternetAddress[toEmailAddressArray.length];
                for (int i = 0; i < toEmailAddressArray.length; i++) {
                    toEmailRecipients[i] = new InternetAddress(toEmailAddressArray[i]);
                }
                message.setRecipients(MimeMessage.RecipientType.TO, toEmailRecipients);

            }
            //设置抄送人,这里设置自己的是抄送人，免得被网易邮箱列为垃圾邮件
            message.setRecipient(Message.RecipientType.CC, new InternetAddress( MailConst.USER_NAME ));
            // 2.3 主题（标题）
            message.setSubject(title);
            //设置编码，防止发送的内容中文乱码。
            message.setContent(NOTIFICATION_MAIL_CONTENT, "text/html;charset=UTF-8");
            //3发送消息
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }


}
