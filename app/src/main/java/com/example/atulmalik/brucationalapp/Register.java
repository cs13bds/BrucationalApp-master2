package com.example.atulmalik.brucationalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;


public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etNameR = (EditText) findViewById(R.id.etNameR);
        final EditText etEmailR = (EditText) findViewById(R.id.etEmailR);
        final EditText etPassR = (EditText) findViewById(R.id.etPassR);
        final EditText etPasscR = (EditText) findViewById(R.id.etPasscR);
        final Button btRegister = (Button) findViewById(R.id.btRegister);
        final TextView tvLogin = (TextView) findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Register.this, Login.class);
                Register.this.startActivity(loginIntent);
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etNameR.getText().toString();
                final String email = etEmailR.getText().toString();
                final String password = etPassR.getText().toString();
                final String passwordC = etPasscR.getText().toString();

                if(name.isEmpty() || name.length()<3){
                    etNameR.setError("At least 3 character");
                }

                if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmailR.setError("Enter a valid email");
                }

                if(password.length()<4 || password.length()>10){
                    etPassR.setError("Must be between 4 and 10 characters");
                }
                else if (!(password.equals(passwordC))){
                    etPassR.setError("Passwords do not match");
                }


                else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                                    progressDialog.setIndeterminate(true);
                                    progressDialog.setMessage("Creating Account...");
                                    progressDialog.show();




                                /*
                                    // Recipient's email ID needs to be mentioned.
                                    String to = email;

                                    // Sender's email ID needs to be mentioned
                                    String from = "brucational@gmail.com";

                                    // Assuming you are sending email from localhost
                                    String host = "localhost";

                                    // Get system properties
                                    Properties properties = System.getProperties();

                                    // Setup mail server
                                    properties.setProperty("mail.smtp.host", host);

                                    // Get the default Session object.
                                    Session session = Session.getDefaultInstance(properties);

                                    try {
                                        // Create a default MimeMessage object.
                                        MimeMessage message = new MimeMessage(session);

                                        // Set From: header field of the header.
                                        message.setFrom(new InternetAddress(from));

                                        // Set To: header field of the header.
                                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                                        // Set Subject: header field
                                        message.setSubject("Welcome to Brucational");

                                        // Now set the actual message
                                        message.setText("Dear "+name+"\n Your are successfully registered with us. \n Enjoy " +
                                                "your time here \n \n Best of Luck \n Admin");

                                        // Send message
                                        Transport.send(message);
                                      //  System.out.println("Sent message successfully....");
                                    }catch (MessagingException mex) {
                                        mex.printStackTrace();
                                    }


                    */






                                    Intent intent = new Intent(Register.this, Login.class);
                                    Register.this.startActivity(intent);

                               /*       AlertDialog.Builder builder = new AlertDialog.Builder(Login.class);
                                    builder.setMessage("Sucessfully Registered")
                                            .setNegativeButton("Login", null)
                                            .create()
                                            .show();*/

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                    builder.setMessage("Registration Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    RegisterRequest registerrequest = new RegisterRequest(name, email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register.this);
                    queue.add(registerrequest);
                }
            }

        });

    }
}
