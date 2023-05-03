package com.example.projetocondominio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profiles extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        Menu menu = bottomNavigationView.getMenu();
        if (user != null) {
            menu.findItem(R.id.action_turnos).setVisible(false);
            menu.findItem(R.id.action_profiles).setVisible(false);
            menu.findItem(R.id.action_horario).setVisible(true);
            menu.findItem(R.id.action_profile).setVisible(true);
            menu.findItem(R.id.action_emergency).setVisible(true);
        }
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_horario:
                        // Ação para o item "Home"
                        Intent horario = new Intent(Profiles.this,Turnos.class);
                        startActivity(horario);
                        return true;
                    case R.id.action_profile:
                        // Ação para o item "Search"
                        Intent profile = new Intent(Profiles.this,Profiles.class);
                        startActivity(profile);
                        return true;
                    case R.id.action_emergency:
                        // Ação para o item "Profile"
                        Intent emergencia = new Intent(Profiles.this,Emergencia.class);
                        startActivity(emergencia);
                        return true;
                }
                return false;
            }
        });
    }
}