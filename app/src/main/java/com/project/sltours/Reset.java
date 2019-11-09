package com.project.sltours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Reset extends AppCompatActivity {

    private EditText edmail;
    private Button btn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        edmail=findViewById(R.id.getemaiid);
        btn=findViewById(R.id.btnsendreset);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edmail.getText().toString();

                    if(email.isEmpty())
                    {
                        Toast.makeText(Reset.this, "Please Enter your Reset Email", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        PassResetViaEmail(email);
                    }




            }
        });

    }

    private void PassResetViaEmail(String email){
        if(mAuth != null) {
            //   Log.w(" if Email authenticated", "Recovery Email has been  sent to " + DummyEmail);
            Toast.makeText(this, "Recovery Email has been  sent to "+email, Toast.LENGTH_SHORT).show();
            mAuth.sendPasswordResetEmail(email);
            startActivity(new Intent(Reset.this,Login.class));
            finish();
        } else {
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
        }
    }
}
