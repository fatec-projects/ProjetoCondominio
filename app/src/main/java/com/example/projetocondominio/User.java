package com.example.projetocondominio;

public class User {
    private String name, photo, email;

    public String getName() {
        return name;
    }


    public String getPhoto() {
        return photo;
    }
    public String getEmail() {
        return email;
    }

    public User() {
        // Construtor vazio necessário para o Firebase
    }
    public User(String name, String photo, String email) {
        this.name = name;
        this.photo = photo;
        this.email = email;
    }
}
