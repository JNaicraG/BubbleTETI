package com.example.bubbleteti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Clientes extends AppCompatActivity implements View.OnClickListener {

    private Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_clientes );

        voltar = (Button) findViewById( R.id.btnVoltarCliente );
        voltar.setOnClickListener( this );

    }

    private void AlterarConta(){
        startActivity( new Intent( this, AlterarCliente.class ) );
    }

    @Override
    public void onClick(View view) {

    }
}