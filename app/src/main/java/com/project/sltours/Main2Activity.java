package com.project.sltours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {

   private EditText edplaces;
   private Button btnupload;


   private DatabaseReference databaseReferenceplaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edplaces=findViewById(R.id.editText2);
        btnupload=findViewById(R.id.placesbutton);

        databaseReferenceplaces= FirebaseDatabase.getInstance().getReference("adminplaces");


       btnupload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getstringfromedittext();
           }
       });



    }

    private void getstringfromedittext() {
       String places=edplaces.getText().toString().trim();

       if(places.equals(""))
       {
           Toast.makeText(this, "Please Enter place name", Toast.LENGTH_SHORT).show();
       }
       else
       {
          String key=databaseReferenceplaces.push().getKey();

          databaseReferenceplaces.child(key).child("places name").setValue(places).addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                  Toast.makeText(Main2Activity.this, "data saves successfully", Toast.LENGTH_SHORT).show();
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  Toast.makeText(Main2Activity.this, "data not saved", Toast.LENGTH_SHORT).show();
              }
          });

       }


    }


}
