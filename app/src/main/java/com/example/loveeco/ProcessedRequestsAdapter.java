package com.example.loveeco;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class ProcessedRequestsAdapter extends RecyclerView.Adapter<ProcessedRequestsHolder> {


    ArrayList<String> name,name2,desc,pic,payment,areas,category;

    // creating Activity variable globally
    Activity a;

    public ProcessedRequestsAdapter(ArrayList<String> category,ArrayList<String> areas,ArrayList<String> name,ArrayList<String> name2,ArrayList<String> desc ,ArrayList<String> pic ,ArrayList<String> payment, Activity a)
    {

        this.name = name;
        this.name2 = name2;
        this.desc = desc;
        this.payment = payment;
        this.pic = pic;
        this.areas = areas;
        this.category = category;
        this.a = a;
    }
    @NonNull
    @Override
    public ProcessedRequestsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ProcessedRequestsHolder vd = new ProcessedRequestsHolder(LayoutInflater.from(a).inflate(R.layout.processed_layout,viewGroup,false));
        return vd;
    }

    @Override
    public void onBindViewHolder(final ProcessedRequestsHolder viewDriverViewHolder, int position) {

        viewDriverViewHolder.name.setText(name.get(position));
        String[]test=areas.get(position).substring(1,areas.get(position).length()-1).split(",");
        Log.d("dfvgv", "onBindViewHolder: "+areas.get(position));
        viewDriverViewHolder.desc.setText("EcoWays Advice based on your request:"+"\n\n");
        for(int a=0;a<test.length;a++)
        {
            for(int b=0;b<category.size();b++)
            {
                if(test[a].trim().equals(category.get(b)))
                {
                    viewDriverViewHolder.desc.append(name2.get(b)+"\n"+desc.get(b)+"\n\n");
                    Log.d("ferfvcfdf", "onBindViewHolder: "+category.get(b));
                }
            }
        }
        if(payment.get(position).equals("Consultation")) {
            viewDriverViewHolder.payment.setText(payment.get(position)+": $99");
        }
        else if(payment.get(position).equals("Borrow Resources"))
        {
            viewDriverViewHolder.payment.setText(payment.get(position)+": $199");
        }
        else if(payment.get(position).equals("Buy resources"))
        {
            viewDriverViewHolder.payment.setText(payment.get(position)+": $399");
        }
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
