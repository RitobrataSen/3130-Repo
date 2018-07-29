package com.example.rito.groupapp;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * GMailSender functions as the main email object. It is where all email properties are initialized.
 * The user and password of the sending email address are directly specified as attributes,
 * although specifying them from the current user would be possible given valid database emails.
 *
 * [update]: ByteArrayDataSource class has been added, which is used to define the DataHandler in
 * the sendMail method. The sendMail method is here implemented to facilitate the instantiation of
 * the MimeMessage (email) details such as the sender, recipients, message, header, and datahandler.
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

    static {
        Security.addProvider(new com.example.rito.groupapp.JSSEProvider());
    }

    public GMailSender() {

        emailProps.setProperty("mail.transport.protocol", "smtp");
        emailProps.setProperty("mail.host", mailhost);

        emailProps.put("mail.smtp.auth", "true");
        emailProps.put("mail.smtp.port", "465");
        emailProps.put("mail.smtp.socketFactory.port", "465");
        emailProps.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        emailProps.put("mail.smtp.socketFactory.fallback", "false");
        emailProps.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(emailProps, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body) throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);

            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress("3130.group10@gmail.com"));
            message.setSubject(subject);

            message.setDataHandler(handler);
            if ("3130.group10@gmail.com".indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("3130.group10@gmail.com"));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress("3130.group10@gmail.com"));
            Transport.send(message);
        }
        catch (Exception e) {
            Log.e("debug.print","GMS error message: " + e);
        }
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

    public static String generateRandPassword() {
        int randLength = ThreadLocalRandom.current().nextInt(8, 12 + 1);
        String newPw = "";
        String alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZzyxwvutsrqponmlkjihgfedcba0123456789";
        Random randy = new Random();
        for(int i=0; i<randLength; i++)
            newPw += alphanum.charAt(randy.nextInt(alphanum.length()));
        return newPw;
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}