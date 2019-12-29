package com.example.loveeco;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
 * code by gurman*/
public class ViewDriverViewHolder extends RecyclerView.ViewHolder {

    TextView txtViewName,txtViewStatus,tv1,/*tv2,tv3,tv4,tv5,tv6,tv7,tv8,*/tvBuy;
    Button update;
    public ViewDriverViewHolder(@NonNull View itemView) {
        super(itemView);
        txtViewName=itemView.findViewById(R.id.tvName);
        txtViewStatus=itemView.findViewById(R.id.tvStatus);
        tvBuy=itemView.findViewById(R.id.tvBuy);
        tv1=itemView.findViewById(R.id.tv1);
//        tv2=itemView.findViewById(R.id.tv2);
//        tv3=itemView.findViewById(R.id.tv3);
//        tv4=itemView.findViewById(R.id.tv4);
//        tv5=itemView.findViewById(R.id.tv5);
//        tv6=itemView.findViewById(R.id.tv6);
//        tv7=itemView.findViewById(R.id.tv7);
//        tv8=itemView.findViewById(R.id.tv8);
        update=itemView.findViewById(R.id.btnUpdate);

    }
}
