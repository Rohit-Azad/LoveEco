package com.example.loveeco;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProcessedRequestsActivity extends AppCompatActivity {
    RecyclerView r;
    String COLLECTION_NAME="CustomerRequestDB";
    String COLLECTION_NAME_2="OurWaysDB";
    private FirebaseFirestore db;
    ProcessedRequestsAdapter ad;
    ArrayList<String> names,category,area,payment,desc,name2,pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processed_requests);

        r = (RecyclerView) findViewById(R.id.recyclerEco);
        r.setLayoutManager(new LinearLayoutManager(ProcessedRequestsActivity.this, LinearLayoutManager.VERTICAL, false));


        names=new ArrayList<>();
        category=new ArrayList<>();
        area=new ArrayList<>();
        payment=new ArrayList<>();
        desc=new ArrayList<>();
        name2=new ArrayList<>();
        pic=new ArrayList<>();

        initialize();

        show();

        final Button show=findViewById(R.id.btnShow);
        final Button back=findViewById(R.id.btnGoBack);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad = new ProcessedRequestsAdapter(category,area,names,name2,desc,pic,payment, ProcessedRequestsActivity.this);

                r.setAdapter(ad);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProcessedRequestsActivity.this,customer_homepage.class);
                startActivity(i);

            }
        });



    }

    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void show(){

        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // numberOfSeats = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if((document.getData().get("Status").toString().equals("Processed")))
                                {
                                    names.add(document.getData().get("Name").toString());
                                   // String[]test=document.getData().get("Areas").toString().substring(1,document.getData().get("Areas").toString().length()-1).split(",");

                                    area.add(document.getData().get("Areas").toString());
                                    payment.add(document.getData().get("payment").toString());
                                }
                            }
                            Log.d("fergrg", "onComplete: "+names.size()+"");

                        } else {

                        }

                    }
                });
        db.collection(COLLECTION_NAME_2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // numberOfSeats = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name2.add(document.getData().get("Name").toString());
                                desc.add(document.getData().get("Desc").toString());
                                pic.add(document.getData().get("Pictures").toString());
                                category.add(document.getData().get("Category").toString());

                            }

                        } else {

                        }




                    }
                });



    }
}
