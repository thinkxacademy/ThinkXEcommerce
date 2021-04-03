package com.ecommerce.thinkx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout mEmail,mPassword;
    Button mLogin,mRegister;
    private FirebaseAuth mAuth;
    String email,password;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bind Xml Elements
        tv1 = findViewById(R.id.ErrorInfo);
        tv1.setVisibility(View.GONE);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.log_in);
        mRegister = findViewById(R.id.register);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(MainActivity.this,UserActivity.class);
            MainActivity.this.startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.log_in:
                email = Objects.requireNonNull(mEmail.getEditText()).getText().toString();
                password = Objects.requireNonNull(mPassword.getEditText()).getText().toString();
                loginToFirebase();
                break;
            case R.id.register:
                email = Objects.requireNonNull(mEmail.getEditText()).getText().toString();
                password = Objects.requireNonNull(mPassword.getEditText()).getText().toString();
                registerToFirebase();
                break;
        }
    }

    private void registerToFirebase() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        tv1.setVisibility(View.VISIBLE);
                        tv1.setText(getResources().getString(R.string.successregister));
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        tv1.setVisibility(View.VISIBLE);
                        tv1.setText(Objects.requireNonNull(task.getException()).getMessage());
                        //updateUI(null);
                    }
                });
    }

    private void loginToFirebase() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            //Redirect the user to the UserActivity
                            Intent intent = new Intent(MainActivity.this,UserActivity.class);
                            MainActivity.this.startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            tv1.setVisibility(View.VISIBLE);
                            tv1.setText(Objects.requireNonNull(task.getException()).getMessage());
                            //updateUI(null);
                            // ...
                        }
                    }
                });
    }
}