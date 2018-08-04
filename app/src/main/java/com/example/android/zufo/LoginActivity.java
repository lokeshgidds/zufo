package com.example.android.zufo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText myEmail,myPassword;
    Button button, button1;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        myEmail = findViewById(R.id.login_email);
        myPassword = findViewById(R.id.login_password);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        mAuth=FirebaseAuth.getInstance();

    }

    public void signinuser(View view) {
        String email=myEmail.getText().toString();
        String password=myPassword.getText().toString();
        if(email.equals("")){
            myEmail.setError("Enter  the Email");
        }
        else if (password.equals("")){
            myPassword.setError("Enter the Password");
        }
        else{
            Toast.makeText(this, "Logging You in.....", Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        showErrorBox("There was a problem in logging in");
                        Log.i("FINDCODE","message:"+task.getException());
                    }
                    else{
                        Intent i=new Intent(LoginActivity.this,MainChatActivity.class);
                        finish();
                        startActivity(i);
                    }
                }
            });
        }

    }

    public void registeruser(View view)
    {
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);


    }
    private void showErrorBox(String message){

        new AlertDialog.Builder(this)
                .setTitle("heyyyyy")
                .setMessage(message)
                .setPositiveButton("ok",null)
                .show();
    }
}
