package com.insumoskeij.appaksu;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class sendMail extends AppCompatActivity {

    private EditText toEmailEditText;
    private EditText subjectEditText;
    private EditText messageEditText;
    private Button sendButton;
    private Button clearButton;

    /**CHANGE ACCORDINGLY**/
    private static final String SMTP_HOST_NAME = "smtp.gmail.com"; //can be your host server smtp@yourdomain.com
    private static final String SMTP_AUTH_USER = "jhoanmchacon@gmail.com"; //your login username/email
    private static final String SMTP_AUTH_PWD  = "jh04n2104"; //password/secret

    private static Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /******Cambio de icono de Fecha atras toolbar****/
        final Drawable upArrow = ResourcesCompat.getDrawable(
                getResources(),
                R.drawable.ic_arrow_back_black_24dp, //this is the <- arrow from android resources. Change this to the thing you want.
                null);
        assert upArrow != null;
        upArrow.setColorFilter(
                ContextCompat.getColor(
                        getApplicationContext(),
                        R.color.text_color // change this to the color you want (or remove the setColorFilter call)
                ),
                PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        /************fin cambio de icono****/

        //assign views
        toEmailEditText = findViewById(R.id.toEmailEditText);
        subjectEditText = findViewById(R.id.subjectEditText);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendMessageButton);
        clearButton = findViewById(R.id.clearButton);


        //listen to send button click
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String to = toEmailEditText.getText().toString().trim();
                    String from = toEmailEditText.getText().toString().trim();
                    String subject = subjectEditText.getText().toString();
                    String message = messageEditText.getText().toString();

                    if(to.isEmpty()){
                        //Toast.makeText(getBaseContext(), "Debe ingresar un correo electrónico.", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Debe ingresar un correo electrónico.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else if(subject.isEmpty()){
                        //Toast.makeText(getBaseContext(), "Debe ingresar un Asunto.", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Debe ingresar un Asunto.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else if(message.isEmpty()){
                        //Toast.makeText(getBaseContext(), "Debe introducir un mensaje.", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Debe introducir un mensaje.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        //everything is filled out
                        //send email
                        sendEmail(to, from, subject, message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }

        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEmailEditText.setText(null);
                subjectEditText.setText(null);
                messageEditText.setText(null);
            }
        });
    }

    public static void sendEmail(String to, String from, String subject, String msg){
        // Recipient's email ID needs to be mentioned.

        // Sender's email ID needs to be mentioned
        //String from = "jhoanmchacon@gmail.com"; //from

        final String username = SMTP_AUTH_USER;
        final String password = SMTP_AUTH_PWD;

        // Assuming you are sending email through relay.jangosmtp.net
        String host = SMTP_HOST_NAME;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setContent(msg, "text/html");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

//            // Part two is attachment
//            messageBodyPart = new MimeBodyPart();
//            String filename = Context.;
//            DataSource source = new FileDataSource(filename);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(filename);
//            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {

                        // Send message
                        Transport.send(message);
                        System.out.println("Sent message successfully....");

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
