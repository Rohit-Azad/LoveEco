package com.example.loveeco;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProcessedRequestsHolder extends RecyclerView.ViewHolder {

    TextView name,desc,payment;
    ImageView img;
    public ProcessedRequestsHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.tvName);
        desc=itemView.findViewById(R.id.tvDesc);
        payment=itemView.findViewById(R.id.tvPayment);
        img=itemView.findViewById(R.id.imageView);
    }
}
