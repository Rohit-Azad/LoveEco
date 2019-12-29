package com.example.loveeco;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ViewDriverAdapter extends RecyclerView.Adapter<ViewDriverViewHolder> {

    String COLLECTION_NAME="CustomerRequestDB";
    private FirebaseFirestore db;

    ArrayList<String> name,status,area,payment;

            // creating Activity variable globally
    Activity a;

    public ViewDriverAdapter(ArrayList<String> name ,ArrayList<String> status ,ArrayList<String> area ,ArrayList<String> payment , Activity a)
    {

        this.name = name;
        this.status = status;
        this.area = area;
        this.payment = payment;
        this.a = a;
    }
    @NonNull
    @Override
    public ViewDriverViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewDriverViewHolder vd = new ViewDriverViewHolder(LayoutInflater.from(a).inflate(R.layout.admin_layout,viewGroup,false));
        return vd;
    }

    @Override
    public void onBindViewHolder(final ViewDriverViewHolder viewDriverViewHolder, int position) {

            viewDriverViewHolder.txtViewName.setText(name.get(position));
            viewDriverViewHolder.txtViewStatus.setText(status.get(position));
            viewDriverViewHolder.tvBuy.setText(payment.get(position));
            String[]test=area.get(position).substring(1,area.get(position).length()-1).split(",");
            for(int x=0;x<test.length;x++) {
                viewDriverViewHolder.tv1.append("* "+test[x].trim()+"\n");
            }

            viewDriverViewHolder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initialize();

//                    SharedPreferences.Editor sp =a.getSharedPreferences("admin_info",MODE_PRIVATE).edit();
//
//                        sp.putString("editName", viewDriverViewHolder.name.getText().toString());
//                    sp.apply();
                    Map<String, Object> updateDetails = new HashMap<>();
                    updateDetails.put("Status", "Processed");
                    db.collection(COLLECTION_NAME).document( viewDriverViewHolder.txtViewName.getText().toString()).update(updateDetails);


                    Intent i = new Intent(a,AdminMainActivity.class);
                    a.startActivity(i);
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

    @Override
    public int getItemCount() {
        return name.size();
    }
}
