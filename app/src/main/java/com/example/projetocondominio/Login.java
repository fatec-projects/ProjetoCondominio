package com.example.projetocondominio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText login_email,login_pswd;
    private Button login_button;
    private ProgressBar progress_bar;
    String[] messages = {"Preencha todos os campos", "Login Efetuado com Sucesso"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        super.getSupportActionBar().hide();
        StartComponents();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString();
                String password = login_pswd.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,messages[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    AutenticarUsers(view);
                }
            }
        });

    }

    private void AutenticarUsers(View view){
        String email = login_email.getText().toString();
        String password = login_pswd.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful()){
                    progress_bar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaPrincipal(email);
                        }
                    }, 3000);
                }else{
                    String error;
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        error = "Erro ao logar usuário";
                    }
                    Snackbar snackbar = Snackbar.make(view,error,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if (usuarioAtual != null){
            TelaPrincipal(usuarioAtual.getEmail());
        }
    }

    private void TelaPrincipal(String email){
        if(email.equals("admin@email.com")){
            Intent intent = new Intent(Login.this, Turnos.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(Login.this, Horario.class);
            startActivity(intent);
            finish();
        }
    }
    private void StartComponents(){
        login_email = findViewById(R.id.login_email);
        login_pswd = findViewById(R.id.login_pswd);
        login_button = findViewById(R.id.login_button);
        progress_bar = findViewById(R.id.progress_bar);
    }
    public void SignIn(View view) {
        startActivity(new Intent(Login.this, Profile.class));
    }


}