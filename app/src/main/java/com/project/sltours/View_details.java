package com.project.sltours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_details extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private Adapter_places_info madapter;
    private List<Model_Placedetails> muploads;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);



        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        searchView=findViewById(R.id.searchnn);

        databaseReference= FirebaseDatabase.getInstance().getReference("useruploads");

        recyclerView=findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLinearLayoutManager);

        muploads=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                muploads.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Model_Placedetails mp=dataSnapshot1.getValue(Model_Placedetails.class);
                        muploads.add(mp);
                }

                madapter=new Adapter_places_info(View_details.this,muploads);
                recyclerView.setAdapter(madapter);
                madapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(searchView!=null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;

                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }


    }

    private void search(String newText) {

        ArrayList<Model_Placedetails> mylist=new ArrayList<>();

        for(Model_Placedetails object:muploads)
        {
            if(object.getPlacename().toLowerCase().contains(newText.toLowerCase()))
            {
                mylist.add(object);
            }

        }

        Adapter_places_info adapter_search=new Adapter_places_info(View_details.this,mylist);
        recyclerView.setAdapter(adapter_search);

     //   adapter_search.setOnitemclicklistener(MainActivity.this);

        adapter_search.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.item1id:
                Intent op=new Intent(View_details.this,Weather.class);
                startActivity(op);
                break;

            case R.id.item2id:
                Intent opo=new Intent(View_details.this,Money.class);
                startActivity(opo);
                break;

            case R.id.item3id:
               Intent opoo=new Intent(View_details.this,Login.class);
               startActivity(opoo);
                finish();
                firebaseAuth.signOut();

                break;

            case R.id.item4id:
                Intent intr=new Intent(View_details.this,MainActivity.class);
                startActivity(intr);
                break;

            case R.id.item5id:
                Intent inn=new Intent(View_details.this,Time_different.class);
                startActivity(inn);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
