package com.example.bubbleteti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
        startActivity( new Intent(this, Login.class) );
    }

}