package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetail extends AppCompatActivity {
    TextView name,price,desc;
    RelativeLayout R1,R2,R3;
    TextView S1,S2,S3;
    String selected_size="";
    TextView quantity;
    Button add , increment,decrement;
    ImageButton saved;
    int quantity_Product=1;
    FirebaseFirestore db;

    dataBase SQLdb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Gson gson = new Gson();
        Product product=gson.fromJson(getIntent().getStringExtra("product"),Product.class);
        Log.d("id", ""+product.getId());
        ImageSlider imageSlider=findViewById(R.id.slider);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        desc=findViewById(R.id.desc);
        R1=findViewById(R.id.R1);
        R2=findViewById(R.id.R2);
        R3=findViewById(R.id.R3);
        S1=findViewById(R.id.size1);
        S2=findViewById(R.id.size2);
        S3=findViewById(R.id.size3);
        quantity=findViewById(R.id.quantity);
        add=findViewById(R.id.add);
        increment=findViewById(R.id.increment);
        decrement=findViewById(R.id.decrement);
        saved=findViewById(R.id.save);


        SQLdb=new dataBase(getApplicationContext());

        db= FirebaseFirestore.getInstance();

        List<SlideModel> slideModelList = new ArrayList<>();
        for (int i=0 ; i< product.getImage().size();i++){
            slideModelList.add(new SlideModel(product.getImage().get(i),null));
        }
        imageSlider.setImageList(slideModelList,true);
        name.setText(product.getName());
        price.setText("$"+product.getPrice());
        desc.setText(product.getDesc());
        S1.setText(product.getSize().get(0));
        S2.setText(product.getSize().get(1));
        S3.setText(product.getSize().get(2));

        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                R1.setBackgroundResource(R.drawable.bkg_select_size);
                R2.setBackgroundResource(R.drawable.custom_input);
                R3.setBackgroundResource(R.drawable.custom_input);
                selected_size=S1.getText().toString();
            }
        });
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                R1.setBackgroundResource(R.drawable.custom_input);
                R2.setBackgroundResource(R.drawable.bkg_select_size);
                R3.setBackgroundResource(R.drawable.custom_input);
                selected_size=S2.getText().toString();
            }
        });
        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                R1.setBackgroundResource(R.drawable.custom_input);
                R2.setBackgroundResource(R.drawable.custom_input);
                R3.setBackgroundResource(R.drawable.bkg_select_size);
                selected_size=S3.getText().toString();
            }
        });

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity_Product++;
                quantity.setText(Integer.toString(quantity_Product));
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity_Product>1) {
                    quantity_Product--;
                    quantity.setText(Integer.toString(quantity_Product));
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!selected_size.isEmpty()){
                   /*

                            */
                    Global_listCart.listCart.add(new Commande(product.getName(),product.getId(),selected_size,Integer.toString(quantity_Product),product.getPrice(),product.getImage().get(0)));
                    Intent intent =new Intent(getApplicationContext(),Page_Principal.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(ProductDetail.this, "please select size ", Toast.LENGTH_SHORT).show();
                }


            }
        });
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLdb.insertData(product.getId());
                Toast.makeText(ProductDetail.this, "Pressed ", Toast.LENGTH_SHORT).show();


            }
        });
    }
}