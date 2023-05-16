package com.example.projetocondominio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Button btnSingout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();
        Button btnSingout;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView emailTextView = findViewById(R.id.email_usuario);
        emailTextView.setText(email);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        Menu menu = bottomNavigationView.getMenu();
        if (user != null) {
            if (email.equals("test@email.com")) {
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

        btnSingout = findViewById(R.id.sigoutn_button);

        btnSingout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                singnOutUser();
            }
        });
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
                        // Ação para o item "Profile"
                        Intent profile = new Intent(Profile.this,Profile.class);
                        startActivity(profile);
                        return true;
                    case R.id.action_turnos:
                        // Ação para o item "Home"
                        Intent turnos = new Intent(Profile.this,Turnos.class);
                        startActivity(turnos);
                        return true;
                    case R.id.action_profiles:
                        // Ação para o item "Home"
                        Intent profiles = new Intent(Profile.this,Profiles.class);
                        startActivity(profiles);
                        return true;
                    case R.id.action_emergency:
                        // Ação para o item "Emergencia"
                        Intent emergencia = new Intent(Profile.this,Emergencia.class);
                        startActivity(emergencia);
                        return true;
                }
                return false;
            }
        });

    }

    private void singnOutUser() {
        Intent mainActivity = new Intent(Profile.this, Login.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
        finish();
    }
}