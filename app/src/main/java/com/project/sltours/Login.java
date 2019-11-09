package com.project.sltours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

   private ProgressBar pb;
   private EditText username,password;
   private Button btnlogin,btnregistergo;
   private TextView tv;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        pb=findViewById(R.id.progressbar);
        username=findViewById(R.id.emailid);
        password=findViewById(R.id.passid);

        btnlogin=findViewById(R.id.registerid);
        btnregistergo=findViewById(R.id.login);

        tv=findViewById(R.id.reset);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Reset.class));
            }
        });

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        btnregistergo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=username.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if(name.equals(""))
                {
                    Toast.makeText(Login.this, "Please fill the e-mail address", Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals(""))
                {
                    Toast.makeText(Login.this, "Please enter the password", Toast.LENGTH_SHORT).show();

                }
                else{
                    pb.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(name,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pb.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Login.this, "Log in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,View_details.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pb.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


            }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(Login.this,View_details.class));
        }
    }
}
