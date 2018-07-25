package com.example.rito.groupapp;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * GMailSender functions as the main email object. It is where all email properties are initialized.
 * The user and password of the sending email address are directly specified as attributes,
 * although specifying them from the current user would be possible given valid database emails.
 *
 * todo: implement class ByteArrayDataSource, .sendEmail() method
 *
 * @since 07-24-18
 * @author Shane, Gobii
 */

//GMailSender is one of three classes being used to programmatically send recovery email
//Full credit to Vinayak Bevinakatti (2010)
//https://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a/2033124#2033124

public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user = "3130.group10@gmail.com";
    private String password = "csci3130!";
    private Session session;
    private Properties emailProps = new Properties();

    public GMailSender() {

        emailProps.setProperty("mail.transport.protocol", "smtp");
        emailProps.setProperty("mail.host", mailhost);

        emailProps.put("mail.smtp.auth", "true");
        emailProps.put("mail.smtp.port", "465");
        emailProps.put("mail.smtp.socketFactory.port", "465");
        emailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        emailProps.put("mail.smtp.socketFactory.fallback", "false");

        emailProps.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(emailProps, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public Properties getEmailProps() {
        return emailProps;
    }

    public void setMailhost(String mHost) {
        mailhost = mHost;
    }
    //username and password are for the email sender
    public void setUser(String us) {
        user = us;
    }
    public void setPassword(String pw) {
        password = pw;
    }

    public String getMailhost() {
        return mailhost;
    }
    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }

}