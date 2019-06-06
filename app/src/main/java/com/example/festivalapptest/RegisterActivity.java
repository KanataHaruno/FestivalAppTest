package com.example.festivalapptest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvSignIn;
    private MediaPlayer mp;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private static final int MIN_LENGTH_PASSWORD = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btn_register);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        tvSignIn = findViewById(R.id.tv_signin);

        btnRegister.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity( new Intent(RegisterActivity.this, MainActivity.class));
        }

    }

    private void registerUser(){
        String email = etEmail.getText().toString();
        email.trim();
        String password = etPassword.getText().toString();
        password.trim();

        // User cannot login without filling anything in
        if (TextUtils.isEmpty(email)){
            // email is empty
            mp = MediaPlayer.create(this, R.raw.error);
            mp.start();
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
            // stopping the execution further
            return;
        }

        if (TextUtils.isEmpty(password)){
            // password is empty
            mp = MediaPlayer.create(this, R.raw.error);
            mp.start();
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
            // stopping the execution further
            return;
        }

        if(password.length() < MIN_LENGTH_PASSWORD)
        {
            mp = MediaPlayer.create(this, R.raw.error);
            mp.start();
           Toast.makeText(this, "The password must be minimal 6 characters long!", Toast.LENGTH_LONG).show();
            return;
        }


        // if validation is okay
        // show progress bar
        progressDialog.setMessage("Registering User... ");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            mp = MediaPlayer.create(RegisterActivity.this, R.raw.succes);
                            mp.start();
                            finish();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            mp = MediaPlayer.create(RegisterActivity.this, R.raw.error);
                            mp.start();
                            Toast.makeText(RegisterActivity.this, "Could not register, please try again.. ", Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        if (v == btnRegister){
            registerUser();
        }

        if (v == tvSignIn){
            finish();
            startActivity( new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }
}
