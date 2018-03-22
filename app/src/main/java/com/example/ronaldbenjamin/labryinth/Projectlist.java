package com.example.ronaldbenjamin.labryinth;

/**
 * Created by Ronald Benjamin on 2/21/2018.
 */

public class Projectlist {

   private String Image,Desc,Title;

    public Projectlist(){

    }

    public Projectlist(String image, String desc, String title) {
        Image = image;
        Desc = desc;
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
