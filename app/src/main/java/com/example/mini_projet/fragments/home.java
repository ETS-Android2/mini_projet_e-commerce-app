package com.example.mini_projet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mini_projet.CartActivity;
import com.example.mini_projet.Global_listCart;
import com.example.mini_projet.MyAdapter;
import com.example.mini_projet.Product;
import com.example.mini_projet.ProductDetail;
import com.example.mini_projet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class home extends Fragment {
   ArrayList<Product> mylist ;
   ListView listView;
    FirebaseFirestore db;
    RelativeLayout cart;
    TextView textNbCommande;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        listView=(ListView)view.findViewById(R.id.list);
        cart=view.findViewById(R.id.cart);
        textNbCommande=view.findViewById(R.id.lengthCart);
        mAuth = FirebaseAuth.getInstance();
        int nbCommande= Global_listCart.listCart.size();

        if (nbCommande==0){
            textNbCommande.setVisibility(View.GONE);
        }else {
            textNbCommande.setVisibility(View.VISIBLE);
            textNbCommande.setText(Integer.toString(nbCommande));
        }

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
        mylist=new ArrayList<Product>();
        db= FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser()!=null){

            fetchData();
        }


listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i=new Intent(getActivity(), ProductDetail.class);
        Gson gson = new Gson();
        String myProduct = gson.toJson(mylist.get(position));
        i.putExtra("product", myProduct);
        startActivity(i);



    }
});





        return view;



    }
    void fetchData(){

        db.collection("Products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        mylist.clear();
                        if (value!=null){
                            for (DocumentSnapshot document : value){
                                mylist.add(
                                        new Product(
                                                document.getId(),
                                                document.get("name").toString(),
                                                document.get("price").toString(),
                                                document.get("description").toString(),
                                                (ArrayList<String>)document.get("sizes"),
                                                (ArrayList<String>) document.get("image")
                                        )
                                );
                                // Log.d("Lien Image", ""+((ArrayList<String>) document.get("image")).get(0));
                            }

                            MyAdapter myAdapter=new MyAdapter(getContext(),mylist);
                            myAdapter.notifyDataSetChanged();
                            listView.setAdapter(myAdapter);
                        }

                    }
                });

    }
}