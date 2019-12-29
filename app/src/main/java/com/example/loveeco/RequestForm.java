package com.example.loveeco;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestForm extends AppCompatActivity {

    EditText name,email,phone,address;
    CheckBox air,water,vedic,ayurvedic,yoga,breathing,food,lifestyle;
    RadioGroup payment;
    Button submit;

    ArrayList<String> selectedAreas;

    RadioButton rb;

    String selection;

    String COLLECTION_NAME="CustomerRequestDB";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        initialize();

        selectedAreas=new ArrayList<>();

        name=findViewById(R.id.etName);
        email=findViewById(R.id.etEmail);
        phone=findViewById(R.id.etPhone);
        address=findViewById(R.id.etAddress);

        air=findViewById(R.id.checkBoxAir);
        water=findViewById(R.id.checkBoxWater);
        vedic=findViewById(R.id.checkBoxVedic);
        ayurvedic=findViewById(R.id.checkBoxAyurvedic);
        yoga=findViewById(R.id.checkBoxYoga);
        breathing=findViewById(R.id.checkBoxBreathing);
        food=findViewById(R.id.checkBoxFood);
        lifestyle=findViewById(R.id.checkBoxLifeStyle);

        payment =findViewById(R.id.rdgrpPayment);

        submit=findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(air.isChecked())
                {
                    selectedAreas.add("Air");
                }
                if(water.isChecked())
                {
                    selectedAreas.add("Water");
                }
                if(vedic.isChecked())
                {
                    selectedAreas.add("Vedic");
                }
                if(ayurvedic.isChecked())
                {
                    selectedAreas.add("Ayurvedic");
                }
                if(yoga.isChecked())
                {
                    selectedAreas.add("Yoga");
                }
                if(breathing.isChecked())
                {
                    selectedAreas.add("Breathing");
                }
                if(food.isChecked())
                {
                    selectedAreas.add("Food");
                }
                if(lifestyle.isChecked())
                {
                    selectedAreas.add("LifeStyle");
                }
                if(name.getText().toString().isEmpty()||
                        email.getText().toString().isEmpty()||
                        phone.getText().toString().isEmpty()||
                        address.getText().toString().isEmpty())
                {
                    Toast.makeText(RequestForm.this,"Please fill the required fields",Toast.LENGTH_SHORT).show();

                }
                else {
                    AddToFireStore();
                    Toast.makeText(RequestForm.this, "Request submitted successfully", Toast.LENGTH_SHORT).show();

                    Intent z = new Intent(RequestForm.this, customer_homepage.class);
                    startActivity(z);
                }
            }
        });

        payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = payment.getCheckedRadioButtonId();

                rb=findViewById(selectedId);

                selection=rb.getText().toString();

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

                    Toast.makeText(RequestForm.this, "Current data:" + ldoc.size(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        Map<String, Object> data;

            data = new HashMap<>();
            data.put("Name",name.getText().toString());
            data.put("Address", address.getText().toString());
            data.put("Phone", phone.getText().toString());
            data.put("Email", email.getText().toString());
            data.put("payment",selection);
            data.put("Status","in Progress");
            data.put("Areas", Arrays.asList(removeDuplicates(selectedAreas).toArray()));
            hotelsDB.document(name.getText().toString()).set(data);


    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();
        // Traverse through the first list
        for (T element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }
}
