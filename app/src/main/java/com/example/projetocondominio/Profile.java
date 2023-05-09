package com.example.projetocondominio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView emailTextView = findViewById(R.id.email_usuario);
        emailTextView.setText(email);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        Menu menu = bottomNavigationView.getMenu();
        if (user != null) {
                menu.findItem(R.id.action_turnos).setVisible(false);
                menu.findItem(R.id.action_profiles).setVisible(false);
                menu.findItem(R.id.action_horario).setVisible(true);
                menu.findItem(R.id.action_profile).setVisible(true);
                menu.findItem(R.id.action_emergency).setVisible(true);
                menu.findItem(R.id.action_logout).setVisible(true);
        }
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_horario:
                        // Ação para o item "Home"
                        Intent horario = new Intent(Profile.this,Horario.class);
                        startActivity(horario);
                        return true;
                    case R.id.action_profile:
                        // Ação para o item "Search"
                        Intent profile = new Intent(Profile.this,Profile.class);
                        startActivity(profile);
                        return true;
                    case R.id.action_emergency:
                        // Ação para o item "Profile"
                        Intent emergencia = new Intent(Profile.this,Emergencia.class);
                        startActivity(emergencia);
                        return true;
                    case R.id.action_logout:
                        return logout();
                }
                return false;
            }
        });
    }

    private boolean logout() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(Profile.this, Login.class);
        startActivity(intent);
        finish();
    }
}