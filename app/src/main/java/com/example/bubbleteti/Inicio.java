package com.example.bubbleteti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    private Button bInicio;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_inicio );

        mAuth = FirebaseAuth.getInstance();

        bInicio = (Button) findViewById( R.id.btnInicio );
        bInicio.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        mAuth.signOut();
        Toast.makeText( Inicio.this, "Deslogado com Sucesso!\n" + "Retornando à tela de Login" ,Toast.LENGTH_LONG).show();
        startActivity( new Intent(this, Login.class) );
    }
}