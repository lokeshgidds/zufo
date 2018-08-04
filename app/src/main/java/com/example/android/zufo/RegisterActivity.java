package com.example.android.zufo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    public static final String CHAT_PREF="ChatPref";
    public static final String DISPLAY_NAME="UserName";
    private EditText em,pass,cp;
    private AutoCompleteTextView acv;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        acv=findViewById(R.id.register_username);
        em=findViewById(R.id.register_email);
        pass=findViewById(R.id.register_password);
        cp=findViewById(R.id.register_confirmpassword);

        mAuth = FirebaseAuth.getInstance();


    }


    public void signUp(View view)
    {
        registerUser();
    }

    private void registerUser(){

       em.setError(null);
       pass.setError(null);
       acv.setError(null);
       cp.setError(null);

        String email=em.getText().toString();
        String password=pass.getText().toString();

        boolean cancel=false;
        View focusView=null;

        if(!TextUtils.isEmpty(password) && !checkPassword(password))
        {
            pass.setError(getString(R.string.incorrect_password));
            focusView=pass;
            cancel=true;

        }
        else if(!TextUtils.isEmpty(email)&& !checkEmail(email)){
            em.setError(getString(R.string.invalid_email));
            focusView=pass;
            cancel=true;

        }
        if(cancel){
            focusView.requestFocus();
        } else
            {
            createUser();
        }
    }

    private boolean checkEmail(String email){
        return email.contains("@");
    }

    private boolean checkPassword(String password){
        String confPassword=pass.getText().toString();
        return confPassword.equals(password);
    }


    public void createUser(){
       String email=em.getText().toString();
       String password=pass.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("FINDCODE","user creation was"+task.isSuccessful());
                if(!task.isSuccessful()){
                    showErrorBox("oops registration failed");
                }
                else{
                    saveUserName();
                    Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }


            }
        });



    }
    private void saveUserName(){
        String userName=acv.getText().toString();
        SharedPreferences pref=getSharedPreferences(CHAT_PREF,0);
        pref.edit().putString(DISPLAY_NAME,userName).apply();
    }

    private void showErrorBox(String message){

        new AlertDialog.Builder(this)
                .setTitle("heyyyyy")
                .setMessage(message)
                .setPositiveButton("ok",null)
                .show();
    }





}
