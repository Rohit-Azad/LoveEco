package com.example.loveeco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    RecyclerView r;
    String COLLECTION_NAME="CustomerRequestDB";
    private FirebaseFirestore db;
    ViewDriverAdapter ad;
    ArrayList<String> names,status,area,payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

      //  final TabLayout myTableLayout=(TabLayout) findViewById(R.id.tabLayout);
        r = (RecyclerView) findViewById(R.id.recyclerViewTabs);

        names=new ArrayList<>();
        status=new ArrayList<>();
        area=new ArrayList<>();
        payment=new ArrayList<>();


        initialize();

        show();
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
                                names.add(document.getData().get("Name").toString());
                                status.add(document.getData().get("Status").toString());
                                area.add(document.getData().get("Areas").toString());
                                payment.add(document.getData().get("payment").toString());




                                // setting properties for recycler view like how it scroll vertically , horizontally
                                r.setLayoutManager(new LinearLayoutManager(AdminMainActivity.this , LinearLayoutManager.VERTICAL,false));

                                // setting adapter ad to recycler view r


                            }
                            Log.d("fergrg", "onComplete: "+names.size()+"");
                            ad = new ViewDriverAdapter(names ,status,area,payment, AdminMainActivity.this);
                            r.setAdapter(ad);
                        } else {

                        }

                    }
                });
    }
}
