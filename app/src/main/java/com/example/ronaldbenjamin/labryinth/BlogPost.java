package com.example.ronaldbenjamin.labryinth;

/**
 * Created by Ronald Benjamin on 20-Mar-18.
 */

public class BlogPost {
    public String desc,image_thumb,image_url,title,user_id;

    public BlogPost(){

    }
    public BlogPost(String desc, String image_thumb, String image_url, String title, String user_id) {
        this.desc = desc;
        this.image_thumb = image_thumb;
        this.image_url = image_url;
        this.title = title;
        this.user_id = user_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
