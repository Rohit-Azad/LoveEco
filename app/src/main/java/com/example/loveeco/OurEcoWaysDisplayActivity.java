package com.example.loveeco;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OurEcoWaysDisplayActivity extends AppCompatActivity {

    RecyclerView r;
    String COLLECTION_NAME="OurWaysDB";
    private FirebaseFirestore db;
    EcoWaysViewAdapter ad;
    ArrayList<String> names,desc,pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_eco_ways_display);

        r = (RecyclerView) findViewById(R.id.recyclerEco);

        names=new ArrayList<>();
        desc=new ArrayList<>();
        pic=new ArrayList<>();


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
                                desc.add(document.getData().get("Desc").toString());
                                pic.add(document.getData().get("Pictures").toString());




                                // setting properties for recycler view like how it scroll vertically , horizontally
                                r.setLayoutManager(new LinearLayoutManager(OurEcoWaysDisplayActivity.this , LinearLayoutManager.VERTICAL,false));

                                // setting adapter ad to recycler view r


                            }
                            Log.d("fergrg", "onComplete: "+names.size()+"");
                            ad = new EcoWaysViewAdapter(names ,desc,pic, OurEcoWaysDisplayActivity.this);
                            r.setAdapter(ad);
                        } else {

                        }

                    }
                });
    }
}
