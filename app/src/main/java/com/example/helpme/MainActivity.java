package com.example.helpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPassword;
    private EditText editTextEmail , getEditTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.registerUser);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        getEditTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.signIn:
                userLogin();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;


        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = getEditTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is reguired");
            editTextEmail.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter A Valid Email");
            editTextEmail.requestFocus();
            return;

        }


        if (password.isEmpty()){
            getEditTextPassword.setError("Passwor is Reguired");
            getEditTextPassword.requestFocus();
            return;

        }


        if (password.length()< 6) {
            getEditTextPassword.setError("MIN length is 6 Charachers");
            getEditTextPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.GONE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));

                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Please Check Your EMAIL to Verify your Account",Toast.LENGTH_LONG).show();
                    }
                    //redirect to user profile

                }else {

                    Toast.makeText(MainActivity.this,"Login is failed,Check Your Information",Toast.LENGTH_LONG).show();

                }

            }
        });


    }

}