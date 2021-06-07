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

public class registration extends AppCompatActivity {
    EditText e1, e2, e3, e4;
    TextView t1;
    Button b1;
    FirebaseAuth firebaseAuth;
    public final String username1="[a-z]";
    public final String username2="[a-z0-9@a-z.a-z]";
    public final String username3="[a-z0-9]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.et_move1);
        e2 = (EditText) findViewById(R.id.et_move2);
        e3 = (EditText) findViewById(R.id.et_move3);
        t1 = (TextView) findViewById(R.id.login);
        b1 = (Button) findViewById(R.id.btn_register);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = e1.getText().toString().trim();
                String email = e2.getText().toString().trim();
                String pass = e3.getText().toString().trim();
                if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(registration.this, "enter all information", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(e2.getText().toString()).matches()) {
                    e2.setError("enter valid email");
                } else if (!e1.getText().toString().matches("[a-z,A-Z]")) {
                    e1.setError("enter valid name");
                } else if (pass.length() < 6) {
                    e3.setError("enter atleast 6no of character");
                    if (e3.getText().toString().matches("[a-z,A-Z,0-9]"))

                        e3.setError("enter valid password");

                }
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(registration.this, "user created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(registration.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

    }

}