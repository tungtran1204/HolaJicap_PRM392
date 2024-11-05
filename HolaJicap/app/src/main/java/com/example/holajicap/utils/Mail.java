package com.example.holajicap.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    private static final String TAG = "Mail"; // Tag để lọc log dễ dàng hơn

    public static void send(final String toEmail, final String subject, final String body) {
        new SendEmailTask().execute(toEmail, subject, body);
    }

    private static class SendEmailTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String toEmail = params[0];
            String subject = params[1];
            String body = params[2];
            try {
                final String fromEmail = "tst12042003@gmail.com";
                final String password = "dcdg uerg svgs pprg";

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                Authenticator auth = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                };
                Session session = Session.getInstance(props, auth);

                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(fromEmail, "HolaJicap"));
                msg.setReplyTo(InternetAddress.parse(fromEmail, false));
                msg.setSubject(subject, "UTF-8");
                msg.setContent(body, "text/html; charset=UTF-8");
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

                // Gửi email
                Transport.send(msg);
                Log.d(TAG, "Email sent successfully to " + toEmail); // In log nếu thành công
                return true;

            } catch (Exception e) {
                Log.e(TAG, "Error sending email: " + e.getMessage(), e); // In log nếu gặp lỗi
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Log.d(TAG, "Email send success");
            } else {
                Log.d(TAG, "Email send failed");
            }
        }
    }
}
