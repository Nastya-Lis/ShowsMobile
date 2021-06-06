package com.example.shows.utils.mailService;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.os.AsyncTask;

import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;

public class JavaMailApi extends AsyncTask<Void,Void,Void> {

    private Session mSession;
    private String mEmail;
    private String mSubject;
    private Performance performance;
    private Booking booking;
    private String mMessage;


    public JavaMailApi(String mEmail, String mSubject, Performance performance,Booking booking) {
      //  this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.performance = performance;
        this.booking = booking;
    }


    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        mSession = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MailConfigApi.EMAIL, MailConfigApi.PASSWORD);
                    }
                });


        try {
            MimeMessage mm = new MimeMessage(mSession);

            mm.setFrom(new InternetAddress(MailConfigApi.EMAIL));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mm.setSubject(mSubject);
            mMessage = "Вы забронировали " + booking.getAmount() + " билетов на спектакль "
                    + performance.getName() + ". Желаем хорошо провести время";

            mm.setText(mMessage);
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

