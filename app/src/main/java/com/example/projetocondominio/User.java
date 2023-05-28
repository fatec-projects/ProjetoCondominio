package com.example.projetocondominio;

public class User {
    private String name;
    private String photo;

    public String getName() {
        return name;
    }


    public String getPhoto() {
        return photo;
    }

    public User(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }
}
