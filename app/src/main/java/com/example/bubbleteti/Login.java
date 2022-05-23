package com.example.bubbleteti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button login, cadastro; //Bot~~oes
    private EditText email, senha;
    //private TextView uTexto;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById( R.id.btnLogin );
        login.setOnClickListener( this );

        cadastro = (Button) findViewById( (R.id.btnCadastro) );
        cadastro.setOnClickListener( this );

        email = (EditText) findViewById( R.id.txtEmail );
        senha = (EditText) findViewById( R.id.txtSenha );

        //uTexto = (TextView) findViewById( R.id.txtUser );
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //uTexto.setText( "E-mail Logado: " + currentUser.getEmail() );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                Logar();
                break;
            case R.id.btnCadastro:
                /*Intent myIntent = new Intent(this, Cadastro.class);
                startActivity(myIntent);*/
                startActivity( new Intent( this, Cadastro.class ) );
            default:
                break;

        }
    }

    private void Logar() {
        String e = email.getText().toString().trim();
        String s = senha.getText().toString().trim();

        Boolean valido = Validar( e, s );
        if (valido) {
            //Logar usu´´ario
            mAuth.signInWithEmailAndPassword( e, s ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText( Login.this, "Logado com Sucesso!\n" +"E-mail: " + user.getEmail().toString(), Toast.LENGTH_LONG ).show();
                        IrInicio();// quando implementada
                        //uTexto.setText( "E-mail Logado: " + user.getEmail() );
                    } else {
                        Toast.makeText( Login.this, "Erro ao logar! Tente novamente!", Toast.LENGTH_LONG ).show();
                    }
                }
            } );
        }
    }

    private void IrInicio(){
        startActivity( new Intent( this, Inicio.class ) );
    }

    private Boolean Validar(String e, String s) {
        if (e.isEmpty()) {
            email.setError( "Preencha o E-mail" );
            email.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher( e ).matches()) {
            email.setError( "Preencha com um e-mail válido" );
            email.requestFocus();
            return false;
        }
        if (s.isEmpty()) {
            senha.setError( "Preencha a Senha" );
            senha.requestFocus();
            return false;
        }
        return true;
    }
}
