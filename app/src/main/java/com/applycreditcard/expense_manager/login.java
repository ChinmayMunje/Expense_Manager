package com.applycreditcard.expense_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class login extends AppCompatActivity {
    EditText e5,e6;
    CardView b3;
    TextView t3;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e5=(EditText)findViewById(R.id.editTextmails);
        e6=(EditText)findViewById(R.id.editTextTextPasswords);
        b3=(CardView) findViewById(R.id.firstbuttons);
        t3=(TextView)findViewById(R.id.login26);
        firebaseAuth=FirebaseAuth.getInstance();
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails=e5.getText().toString().trim();
                String passs=e6.getText().toString().trim();
                if(emails.isEmpty()||passs.isEmpty())
                {
                    Toast.makeText(login.this,"enter all information",Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(e5.getText().toString()).matches())
                {
                    e5.setError("enter valid email");
                }
                else if(passs.length()<=6)
                {
                    e6.setError("enter at least 6 character");
                    if (e6.getText().toString().matches("[a-z,A-Z,0-9]"))
                        e6.setError("enter valid password");
                }
                firebaseAuth.signInWithEmailAndPassword(emails,passs).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(login.this,"user created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(login.this,"error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registeration.class));
            }
        });


    }
}