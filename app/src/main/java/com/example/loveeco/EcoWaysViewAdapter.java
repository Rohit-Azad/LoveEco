package com.example.loveeco;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EcoWaysViewAdapter extends RecyclerView.Adapter<EcoWaysViewHolder> {


    ArrayList<String> name,desc,pic;

    // creating Activity variable globally
    Activity a;

    public EcoWaysViewAdapter(ArrayList<String> name ,ArrayList<String> desc ,ArrayList<String> pic , Activity a)
    {

        this.name = name;
        this.desc = desc;
        this.pic = pic;
        this.a = a;
    }
    @NonNull
    @Override
    public EcoWaysViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        EcoWaysViewHolder vd = new EcoWaysViewHolder(LayoutInflater.from(a).inflate(R.layout.eco_ways_layout,viewGroup,false));
        return vd;
    }

    @Override
    public void onBindViewHolder(final EcoWaysViewHolder viewDriverViewHolder, int position) {

        viewDriverViewHolder.name.setText(name.get(position));
        viewDriverViewHolder.desc.setText(desc.get(position));

        Picasso.get()
                .load(pic.get(position))
                // .placeholder(R.drawable.wait)
              //  .error(R.drawable.error)
                .into(viewDriverViewHolder.img);

    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}
