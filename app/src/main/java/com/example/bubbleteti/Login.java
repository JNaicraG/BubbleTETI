package com.example.bubbleteti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button login, cadastro; //Bot~~oes
    private EditText email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        login = (Button) findViewById( R.id.btnLogin );
        login.setOnClickListener(this);

        cadastro = (Button) findViewById( (R.id.btnCadastro) );
        cadastro.setOnClickListener( this );

        email = (EditText) findViewById( R.id.txtEmail );
        senha = (EditText) findViewById( R.id.txtSenha );

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnLogin:
                Logar();
                break;
            case R.id.btnCadastro:
                /*Intent myIntent = new Intent(this, Cadastro.class);
                startActivity(myIntent);*/
                startActivity( new Intent(this, Cadastro.class));
            default:
                break;

        }
    }

    private void Logar(){
        String e = email.getText().toString().trim();
        String s = senha.getText().toString().trim();

        Validar(e,s);
        //Logar usu´´ario

    }


    private void Validar(String e, String s){

        if(e.isEmpty()){
            email.setError( "Preencha o E-mail" );
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher( e ).matches()){
            email.setError( "Preencha com um e-mail válido" );
            email.requestFocus();
        }
        if(s.isEmpty()){
            senha.setError( "Preencha a Senha" );
            senha.requestFocus();
            return;
        }
    }
}