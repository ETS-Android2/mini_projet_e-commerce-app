package com.example.mini_projet;

import java.io.Serializable;
import java.util.ArrayList;

public class Product   {
    private  String id;
   private String name;
    private String price;
    private String desc ;
    private  ArrayList<String> size;
    private ArrayList<String> image;

    public Product(String id,String name, String price, String desc, ArrayList<String> size,ArrayList<String> image) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.size = size;
        this.image=image;
    }
    public Product(String id,String name, String price, String desc) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.desc = desc;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<String> getSize() {
        return size;
    }

    public void setSize(ArrayList<String> size) {
        this.size = size;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }
}
