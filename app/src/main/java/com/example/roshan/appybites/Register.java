package com.example.roshan.appybites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roshan.appybites.Nav_view_menu.Cart;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private Button btn_register;
    private TextView txt_alreadyuser,txt_forgetpsw;
    private EditText editTextemail,editTextpassword;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseauth;
    private Firebase mref;

    private int id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        progressdialog = new ProgressDialog(this);
        firebaseauth= FirebaseAuth.getInstance();

        if(firebaseauth.getCurrentUser() != null){
           finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

        btn_register=(Button)findViewById(R.id.btn_register);
        txt_alreadyuser=(TextView)findViewById(R.id.txt_alreadyuser);
        editTextemail=(EditText)findViewById(R.id.email);
        editTextpassword=(EditText)findViewById(R.id.password);
        txt_forgetpsw=(TextView)findViewById(R.id.txt_forgetpassword);
        txt_forgetpsw.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        txt_alreadyuser.setOnClickListener(this);
        Register.this.setTitle("Register");
    }

    @Override
    public void onClick(View view) {
        if(view==btn_register){
            registerUser();
        }
        if (view==txt_alreadyuser){

            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }
        if (view==txt_forgetpsw){
            startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
        }

    }
    private void registerUser(){
        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
    return;
        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
     return;
        }


        progressdialog.setMessage("Registering User...");
        progressdialog.show();
        firebaseauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();

                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                            Toast.makeText(Register.this, "Register Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Register.this, "Couldn't register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
