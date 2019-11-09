package com.project.sltours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private  ProgressBar pb;
   private EditText username,password;
   private Button btnregister,btngolog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pb=findViewById(R.id.progressbar);
        username=findViewById(R.id.emailid);
        password=findViewById(R.id.passid);

        btnregister=findViewById(R.id.registerid);
        btngolog=findViewById(R.id.login);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        btngolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (name.equals("")) {
                    Toast.makeText(Register.this, "Please fill the e-mail address", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(Register.this, "Please create the password", Toast.LENGTH_SHORT).show();

                } else {
                    pb.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(name, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, View_details.class);
                                    startActivity(intent);
                                    finish();
                                    pb.setVisibility(View.INVISIBLE);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(View.INVISIBLE);
                                }
                            });
                }


            }


        });


    }
}
