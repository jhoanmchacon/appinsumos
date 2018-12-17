package com.insumoskeij.appaksu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class SendMailActivity extends AppCompatActivity {

    private EditText toEmailEditText;
    private EditText subjectEditText;
    private EditText messageEditText;
    private Button sendButton;
    private Button clearButton;
    private Handler mHandler = new Handler();

    /**CHANGE ACCORDINGLY**/
    private static final String SMTP_HOST_NAME = "smtp.gmail.com"; //can be your host server smtp@yourdomain.com
    private static final String SMTP_AUTH_USER = "appaksu@gmail.com"; //your login username/email
    private static final String SMTP_AUTH_PWD  = "4pp4ksu2018"; //password/secret

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
                    final String to = "inversiones.aksu@gmail.com";
                    final String from = toEmailEditText.getText().toString().trim();
                    final String subject = subjectEditText.getText().toString().trim();
                    final String message = messageEditText.getText().toString().trim();

                    Boolean emailValido = isEmailValid(from);

                    if(from.isEmpty()){
                        //Toast.makeText(getBaseContext(), "Debe ingresar un correo electrónico.", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Debe ingresar un correo electrónico.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else if(!emailValido){
                        //Toast.makeText(getBaseContext(), "Debe ingresar un correo electrónico.", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Debe ingresar un correo electrónico válido.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else if(subject.isEmpty()){
                        //Toast.makeText(getBaseContext(), "Debe ingresar un Asunto.", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Debe ingresar un asunto.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else if(message.isEmpty()){
                        //Toast.makeText(getBaseContext(), "Debe introducir un mensaje.", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Debe introducir un mensaje.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        if (!compruebaConexion(getApplicationContext()))
                        {
                            Snackbar.make(v, "¡No hay conexión a internet!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else {

                            new AlertDialog.Builder(SendMailActivity.this)
                                    .setTitle("AKSU GLOBAL")
                                    .setIcon(R.drawable.aksu_icon)
                                    .setMessage("¿Desea enviar éste mensaje?")
                                    .setNegativeButton(android.R.string.cancel, null)// sin listener
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {// Salir
                                            //everything is filled out
                                            //send email
                                            String messageBody = "<strong><h1>Remitente:</strong> " + from + " <br><br> " + "<strong><h1>Mensaje:</strong> <br><br>" + message;
                                            Html.fromHtml(messageBody);
                                            sendEmail(to, from, subject, messageBody);
                                            //toEmailEditText.setText(null);
                                            //subjectEditText.setText(null);
                                            //messageEditText.setText(null);
                                            sendButton.setEnabled(false);
                                            sendButton.setClickable(false);
                                        }
                                    }).show();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SendMailActivity.this)
                        .setTitle("AKSU GLOBAL")
                        .setIcon(R.drawable.aksu_icon)
                        .setMessage("¿Desea limpiar el formulario?")
                        .setNegativeButton(android.R.string.cancel, null)// sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which) {// Salir
                                //toEmailEditText.setText(null);
                                //subjectEditText.setText(null);
                                //messageEditText.setText(null);

                                Intent intent = new Intent(SendMailActivity.this, SendMailActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
        });
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void sendEmail(String to, String from, String subject, String msg){
        final ProgressDialog pDialog = new ProgressDialog(SendMailActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Enviando...");
        pDialog.show();
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

                        pDialog.dismiss();
                        View listview = findViewById(R.id.send_view);
                        Snackbar.make(listview.getRootView(), "Mensaje enviado con éxito.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        //Intent intent = new Intent(SendMailActivity.this, SendMailActivity.class);
                        //startActivity(intent);
                        //finish();
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(SendMailActivity.this, SendMailActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 3500);

                    } catch (Exception e) {
                        e.printStackTrace();
                        pDialog.dismiss();
                        View listview = findViewById(R.id.send_view);
                        Snackbar.make(listview.getRootView(), "Ocurrió un error al intentar enviar el mensaje. Intente más tarde.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(SendMailActivity.this, SendMailActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 3500);
                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private boolean compruebaConexion(Context applicationContext) {

        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;

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
