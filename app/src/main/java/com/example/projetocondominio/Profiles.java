package com.example.projetocondominio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Profiles extends AppCompatActivity {
    private Button profiles_button;
    private BottomNavigationView bottomNavigationView;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseUser user = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        ImageButton btnPopup = findViewById(R.id.btn_popup);

        btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(view);


            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        Menu menu = bottomNavigationView.getMenu();

        menu.findItem(R.id.action_profiles).setChecked(true);

        if (user != null) {
            menu.findItem(R.id.action_turnos).setVisible(true);
            menu.findItem(R.id.action_profiles).setVisible(true);
            menu.findItem(R.id.action_horario).setVisible(false);
            menu.findItem(R.id.action_profile).setVisible(true);
            menu.findItem(R.id.action_emergency).setVisible(true);
        }
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_turnos:
                        // Ação para o item "Home"
                        Intent turnos = new Intent(Profiles.this, Turnos.class);
                        startActivity(turnos);
                        return true;
                    case R.id.action_profiles:
                        // Ação para o item "Search"
                        Intent profiles = new Intent(Profiles.this, Profiles.class);
                        startActivity(profiles);
                        return true;
                    case R.id.action_profile:
                        // Ação para o item "Profile"
                        Intent profile = new Intent(Profiles.this,Profile.class);
                        startActivity(profile);
                        return true;
                    case R.id.action_emergency:
                        // Ação para o item "Profile"
                        Intent emergencia = new Intent(Profiles.this, Emergencia.class);
                        startActivity(emergencia);
                        return true;
                }
                return false;
            }
        });


    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        profiles_button = popupView.findViewById(R.id.profiles_button);
        EditText adc_nome = popupView.findViewById(R.id.adc_nome);
        EditText adc_email = popupView.findViewById(R.id.adc_email);
        EditText adc_pswd = popupView.findViewById(R.id.adc_pswd);

        profiles_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = adc_nome.getText().toString();
                String email = adc_email.getText().toString();
                String password = adc_pswd.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,"Preencha todos os campos!",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if( task.isSuccessful()){
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                FirebaseUser new_user = task.getResult().getUser();
                                String userId = new_user.getUid();

                                // Create a User object with name and default profile photo URL
                                User newUser = new User(nome,"https://cdn-icons-png.flaticon.com/512/3135/3135715.png");

                                // Save the User object to the Realtime Database under the user's ID
                                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                                usersRef.child(userId).setValue(newUser)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {} else {
                                                    // Failed to save user data
                                                    Snackbar snackbar = Snackbar.make(view,"Error saving user data to the database.",Snackbar.LENGTH_SHORT);
                                                    snackbar.setBackgroundTint(Color.WHITE);
                                                    snackbar.setTextColor(Color.BLACK);
                                                    snackbar.show();
                                                }
                                            }
                                        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                FirebaseAuth.getInstance().signInWithEmailAndPassword("admin@email.com", "000000");
                                Snackbar snackbar = Snackbar.make(view,"Usuário criado com sucesso!",Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();
                                Intent intent = new Intent(Profiles.this, Profiles.class);
                                startActivity(intent);
                                finish();
                            }else{
                                String error;
                                try {
                                    throw task.getException();
                                }catch (Exception e){
                                    error = "Erro ao criar usuário";
                                }
                                Snackbar snackbar = Snackbar.make(view,error,Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();
                            }
                        }
                    });
                }
                }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}