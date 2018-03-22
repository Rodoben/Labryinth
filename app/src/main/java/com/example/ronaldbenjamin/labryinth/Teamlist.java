package com.example.ronaldbenjamin.labryinth;

/**
 * Created by Ronald Benjamin on 2/20/2018.
 */

public class Teamlist {
  private   String Name,Semester,Work,Email,Image;

    public Teamlist(){

    }

    public Teamlist(String name, String semester, String work, String email, String image) {
        Name = name;
        Semester = semester;
        Work = work;
        Email = email;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getWork() {
        return Work;
    }

    public void setWork(String work) {
        Work = work;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
