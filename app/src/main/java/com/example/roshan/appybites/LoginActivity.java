package com.example.roshan.appybites;

import android.app.FragmentTransaction;
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

import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.Nav_view_menu.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.roshan.appybites.Db_Handler.DbHandler.table_name;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_lgoin;
    private TextView txt_signup,txt_forgetpsw;
    private EditText editTextemail,editTextpassword;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseauth;
    private DbHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressdialog = new ProgressDialog(this);
        myDb =new DbHandler(this);
        firebaseauth= FirebaseAuth.getInstance();

        if(firebaseauth.getCurrentUser() != null){
           finish();
            myDb.deletedata(table_name);
        }

        btn_lgoin=(Button)findViewById(R.id.btn_signin);
        txt_signup=(TextView)findViewById(R.id.txt_signup);
        editTextemail=(EditText)findViewById(R.id.email);
        editTextpassword=(EditText)findViewById(R.id.password);
        txt_forgetpsw=(TextView)findViewById(R.id.txt_forgetpassword);
        txt_forgetpsw.setOnClickListener(this);
        btn_lgoin.setOnClickListener(this);
        txt_signup.setOnClickListener(this);
        LoginActivity.this.setTitle("Login");
    }

    @Override
    public void onClick(View view) {
        if(view==btn_lgoin){
            loginUser();
        }
        if (view==txt_signup){

            startActivity(new Intent(getApplicationContext(),Register.class));
        }
        if (view==txt_forgetpsw){
            startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
        }

    }
    private void loginUser(){
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
        progressdialog.setMessage("Login User...");
        progressdialog.show();
//

        firebaseauth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            myDb.deletedata(table_name);
                        }else {
                            Toast.makeText(LoginActivity.this, "Couldn't Login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
