package com.project.sltours;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Spinner plcaselectionspinner;
    ImageView placeimageview;
    Button btnplacechoose,btnupload;
    RatingBar ratingBar;
    EditText eddescription;

    DatabaseReference databaseReferencespinnerplaces;

    Button button;

    Button selectimg;


    private static final int PIC_IMG_REQUEST=1;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private ProgressBar progressBar;


    private Uri uri;

    String gratinfstr="";

    DatabaseReference databaseReferenceforuserdata;
    FirebaseStorage firebaseStorage;

    private StorageTask uploadtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar=findViewById(R.id.progressBar);

        plcaselectionspinner=findViewById(R.id.spinner);
        placeimageview=findViewById(R.id.imageView);
     //   btnplacechoose=findViewById(R.id.buttoncoose);

        btnupload=findViewById(R.id.button2);

        ratingBar=findViewById(R.id.ratingBar);
        eddescription=findViewById(R.id.editText);

        button=findViewById(R.id.button3);


        selectimg=findViewById(R.id.buttoncoose);


        databaseReference=FirebaseDatabase.getInstance().getReference("useruploads");
        storageReference = FirebaseStorage.getInstance().getReference("useruploads");



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

               String ratingstr=String.valueOf(rating);
               gratinfstr=ratingstr;

            }
        });


        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(in,PIC_IMG_REQUEST);
            }
        });


        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userplacesselection=plcaselectionspinner.getSelectedItem().toString();

                if(ratingBar==null)
                {
                    Toast.makeText(MainActivity.this, "Give rating", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(uri==null)
                    {
                        Toast.makeText(MainActivity.this, "Please Select image", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                       final String desc=eddescription.getText().toString();
                        if(desc==null)
                        {
                            Toast.makeText(MainActivity.this, "Please give place description", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            progressBar.setVisibility(View.VISIBLE);
                            final StorageReference filestoregereference = storageReference.child(System.currentTimeMillis() + "."
                                    + getpictureextenction(uri));

                          filestoregereference.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }
                                    return filestoregereference.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downUri = task.getResult();

                                        String key = databaseReference.push().getKey();
                                        Model_Placedetails ms = new Model_Placedetails(userplacesselection, downUri.toString(),gratinfstr,desc, key, key);

                                        databaseReference.child(key).setValue(ms);

                                    progressBar.setVisibility(View.INVISIBLE);
                                        Intent in=new Intent(MainActivity.this,View_details.class);
                                        startActivity(in);

                                    }
                                }
                            });

                        }
                    }

                }

            }
        });










        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Intent in=new Intent(MainActivity.this,Weather.class);
                Intent in=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(in);
                // or
              //  startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });

        databaseReferencespinnerplaces= FirebaseDatabase.getInstance().getReference("adminplaces");

        databaseReferencespinnerplaces.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> area=new ArrayList<>();

                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        String areaname=dataSnapshot1.child("places name").getValue(String.class);
                     //   plcaselectionspinner
                        area.add(areaname);

                        ArrayAdapter<String> areaadapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,area);
                            areaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        plcaselectionspinner.setAdapter(areaadapter);
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String getpictureextenction(Uri uri) {
        ContentResolver cn = getApplication().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cn.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PIC_IMG_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
           uri=data.getData();

            Picasso.get().load(uri).into(placeimageview);
        }
    }


}


