package com.example.loveeco;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.loveeco.LoginRegisterClasses.LoginActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner sp;
    Button submit,btn1;

    String COLLECTION_NAME="OurWaysDB";
    private FirebaseFirestore db;

    ArrayList<OurWaysDB> ourWaysList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        ourWaysList=processData();

        sp=findViewById(R.id.spinnerType);
        submit=findViewById(R.id.btnLogin);
        btn1=findViewById(R.id.btn2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp.getSelectedItem().equals("Administrator"))
                {
                    Intent i=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i=new Intent(MainActivity.this, customer_homepage.class);
                    startActivity(i);
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToFireStore();
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


    private void AddToFireStore() {
        CollectionReference hotelsDB = db.collection(COLLECTION_NAME);
        hotelsDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("ERROR", e.getMessage());
                    return;
                }
                if (queryDocumentSnapshots != null && queryDocumentSnapshots.size() > 0) {

                    List<DocumentChange> ldoc = queryDocumentSnapshots.getDocumentChanges();
                    Log.d("OnEvent", ldoc.size() + "");

                    // index 0 is used. We are assuming only 1 change was made
                    Log.d("OnEvent", ldoc.get(0).getDocument().getId() + "");

                    Log.d("OnEvent", ldoc.get(0).getDocument().getData() + "");
                    Map<String, Object> m = ldoc.get(0).getDocument().getData();

                    Toast.makeText(MainActivity.this, "Current data:" + ldoc.size(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        Map<String, Object> data;
        for(int i=0;i<ourWaysList.size();i++) {
            data = new HashMap<>();
            data.put("ID",ourWaysList.get(i).ourID);
            data.put("Name", ourWaysList.get(i).names);
            data.put("Desc", ourWaysList.get(i).descc);
            data.put("Pictures", ourWaysList.get(i).picture);
            data.put("Category", ourWaysList.get(i).category);

            hotelsDB.document(String.valueOf(ourWaysList.get(i).ourID)).set(data);
        }

    }


    private ArrayList<OurWaysDB> processData() {
        String json = null;
        try {
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open("ourways.json");


            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        ArrayList<OurWaysDB> temp = new ArrayList<>();
        try {

            JSONArray ar = new JSONArray(json);

            Log.d( "processData: ",json);
            JSONObject element;
            OurWaysDB ourWaysDB;

            for (int i=0 ; i < ar.length(); i++) {
                element = ar.getJSONObject(i);
                ourWaysDB = new OurWaysDB();
                ourWaysDB.ourID = element.getString("ID");
                ourWaysDB.picture = element.getString("Pictures");
                ourWaysDB.names = element.getString("Name");
                ourWaysDB.descc = element.getString("Desc");
                ourWaysDB.category = element.getString("Category");
                temp.add(ourWaysDB);
            }
            return temp;
        } catch (JSONException e) {
            Log.d("MainActivity", e.getMessage());
        }

        return null;
    }
}
