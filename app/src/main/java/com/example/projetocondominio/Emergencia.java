package com.example.projetocondominio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Emergencia extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);


        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        Menu menu = bottomNavigationView.getMenu();

        menu.findItem(R.id.action_emergency).setChecked(true);

        if (user != null) {
            if (email.equals("admin@email.com")) {
                menu.findItem(R.id.action_turnos).setVisible(true);
                menu.findItem(R.id.action_profiles).setVisible(true);
                menu.findItem(R.id.action_horario).setVisible(false);

            } else {
                menu.findItem(R.id.action_turnos).setVisible(false);
                menu.findItem(R.id.action_profiles).setVisible(false);
                menu.findItem(R.id.action_horario).setVisible(true);

            }
            menu.findItem(R.id.action_profile).setVisible(true);
            menu.findItem(R.id.action_emergency).setVisible(true);
        }

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_horario:
                        // Ação para o item "Home"
                        Intent horario = new Intent(Emergencia.this,Horario.class);
                        startActivity(horario);
                        return true;
                    case R.id.action_profile:
                        // Ação para o item "Profile"
                        Intent profile = new Intent(Emergencia.this,Profile.class);
                        startActivity(profile);
                        return true;
                    case R.id.action_turnos:
                        // Ação para o item "Home"
                        Intent turnos = new Intent(Emergencia.this,Turnos.class);
                        startActivity(turnos);
                        return true;
                    case R.id.action_profiles:
                        // Ação para o item "Home"
                        Intent profiles = new Intent(Emergencia.this,Profiles.class);
                        startActivity(profiles);
                        return true;
                    case R.id.action_emergency:
                        // Ação para o item "Emergencia"
                        Intent emergencia = new Intent(Emergencia.this,Emergencia.class);
                        startActivity(emergencia);
                        return true;
                }
                return false;
            }
        });

        LinearLayout ambulancia = findViewById(R.id.ambulancia);
        ambulancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+192"));
                startActivity(intent);
            }
        });


        LinearLayout policia = findViewById(R.id.policia);
        policia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+190"));
                startActivity(intent);
            }
        });


        LinearLayout bombeiro = findViewById(R.id.bombeiro);
        bombeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+193"));
                startActivity(intent);
            }
        });


        LinearLayout denuncia = findViewById(R.id.denuncia);
        denuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+181"));
                startActivity(intent);
            }
        });


    }

}