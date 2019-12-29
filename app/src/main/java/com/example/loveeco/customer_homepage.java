package com.example.loveeco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class customer_homepage extends AppCompatActivity {
    Button requestForm,ourEcoWays,processed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_homepage);

        requestForm=findViewById(R.id.btnRequestForm);
        ourEcoWays=findViewById(R.id.btnOurEcoWays);
        processed=findViewById(R.id.btnProcessed);

        requestForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(customer_homepage.this,RequestForm.class);
                startActivity(i);
            }
        });

        ourEcoWays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(customer_homepage.this,OurEcoWaysDisplayActivity.class);
                startActivity(i);
            }
        });
        processed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(customer_homepage.this,ProcessedRequestsActivity.class);
                startActivity(i);
            }
        });




    }
}
