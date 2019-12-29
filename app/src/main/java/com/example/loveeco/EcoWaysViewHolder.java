package com.example.loveeco;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EcoWaysViewHolder  extends RecyclerView.ViewHolder {

    TextView name,desc;;
    ImageView img;
    public EcoWaysViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.tvName);
        desc=itemView.findViewById(R.id.tvDesc);
        img=itemView.findViewById(R.id.imageView);
    }
}
