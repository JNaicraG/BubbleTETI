package com.example.bubbleteti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        entrar = (Button) findViewById( R.id.btnEntrar );
        entrar.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        Log.d( "Teste", "onClick: Entrando na tela de Login" );
        startActivity( new Intent(this, Login.class ) );
    }
}