package com.example.ronaldbenjamin.labryinth;

/**
 * Created by Ronald Benjamin on 2/18/2018.
 */

public class Blog {

    public Blog(){

    }


    private String title;
    private String Desc;
    private String Image;



    public Blog(String title, String desc, String image) {
        this.title = title;
        Desc = desc;
        Image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}


