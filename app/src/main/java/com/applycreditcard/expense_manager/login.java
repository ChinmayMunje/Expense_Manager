package com.applycreditcard.expense_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class login extends AppCompatActivity {
    Button btn_login;
    EditText t1,t2;
    TextView textView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login=(Button)findViewById(R.id.btn_login);
        t1=(EditText)findViewById(R.id.et_line1);
        t2=(EditText)findViewById(R.id.et_line2);
        textView=(TextView)findViewById(R.id.register);
        firebaseAuth=FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=t1.getText().toString().trim();
                String pass=t2.getText().toString().trim();
                if(username.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(login.this,"enter all information",Toast.LENGTH_SHORT).show();
                }
               else if(!Patterns.EMAIL_ADDRESS.matcher(t1.getText().toString()).matches())
                {
                    t1.setError("enter valid email");
                }
               else  if (pass.length()<6)
                {
                    t2.setError("enter at least 6 no of character");
                    if (t2.getText().toString().matches("[a-z,A-Z,0-9]"))

                        t2.setError("enter the valid password");

                }
               //authentication user
                firebaseAuth.signInWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(login.this,"logged successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(login.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registration.class));
            }
        });


    }
}