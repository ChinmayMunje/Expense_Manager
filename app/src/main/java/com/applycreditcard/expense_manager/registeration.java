package com.applycreditcard.expense_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class registeration extends AppCompatActivity {
    CardView c1;
    EditText e1,e2,e3;
    TextView t1;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        c1=(CardView)findViewById(R.id.firstbuttons);
        e2=(EditText)findViewById(R.id.editTextmail);
        e3=(EditText)findViewById(R.id.editTextPassword);
        t1=(TextView)findViewById(R.id.login25);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=e2.getText().toString().trim();
                String pass=e3.getText().toString().trim();
                if(email.isEmpty()||pass.isEmpty())
                {
                    Toast.makeText(registeration.this,"enter all information",Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(e2.getText().toString()).matches())
                {
                    e2.setError("enter valid email");
                }
//                else if(!e1.getText().toString().matches("[a-zA-Z]"))
//                {
//                    e1.setError("enter valid name");
//                }
                else if(pass.length()<=6)
                {
                    e3.setError("enter at least 6 character");
                    if (e3.getText().toString().matches("[a-z,A-Z,0-9]"))
                   e3.setError("enter valid password");
                }
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(registeration.this,"user created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(registeration.this,"error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registeration.this,login.class));
            }
        });

    }
}