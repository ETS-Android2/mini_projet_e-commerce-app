package com.example.mini_projet;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Product> {

    private List<Product> myList;
    Context context;

    public MyAdapter(Context context,List<Product> myList) {
        super(context,R.layout.item_list_home,myList);
        this.myList = myList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.item_list_home, parent,false);
        ImageView imageView=rowView.findViewById(R.id.image);
        TextView name=rowView.findViewById(R.id.name);
        TextView price=rowView.findViewById(R.id.price);

      // imageView.setImageResource(R.drawable.image);
        Glide.with(context).load(myList.get(position).getImage().get(0)).into(imageView);
        name.setText(myList.get(position).getName());
        price.setText("$"+myList.get(position).getPrice());


return rowView;
    }
}
