package com.example.bubbleteti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity implements View.OnClickListener {

    private Button voltar, cadastrar; //Bot~~oes
    private EditText email, senha, senhaRep;
    private EditText nome, cpf;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    //private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastro );

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        cadastrar = (Button) findViewById( R.id.btnCadastrar );
        cadastrar.setOnClickListener( this );

        voltar = (Button) findViewById( (R.id.btnVoltar) );
        voltar.setOnClickListener( this );

        email = (EditText) findViewById( R.id.txtEmailC );
        senha = (EditText) findViewById( R.id.txtSenhaC );
        senhaRep = (EditText) findViewById( R.id.txtEmailSenhaRep );

        nome = (EditText) findViewById( R.id.txtNome);
        cpf = (EditText) findViewById( R.id.txtCPF );
    }


    @Override
    public void onClick(View view) {

        String e = email.getText().toString().trim();
        String s = senha.getText().toString().trim();
        String sR = senhaRep.getText().toString().trim();
        String n = nome.getText().toString().trim();
        String c = cpf.getText().toString().trim();
        Boolean valido = Validar( e, s, sR, n, c );
        switch (view.getId()) {
            case R.id.btnCadastrar:
                if (valido) {
                    CadastrarUsuário(e,s);
                    CadastrarCliente();
                }
                break;
            case R.id.btnVoltar:
                IrLogin();
                break;
            default:
                Log.d( "Erro", "onClick: Oh ceus, deu erro" );
                break;
        }
    }

    private void IrLogin() {
        startActivity( new Intent( this, Login.class ) );
    }


    private void CadastrarUsuário(String e, String s) {

            mAuth.createUserWithEmailAndPassword( e, s ) //salvar email e senha
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) { //se os dados foram salvos sem problema
                                Usuario usuario = new Usuario( e, s ); //usuario armazenado

                                //teste de msg
                                Toast.makeText( Cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG ).show();

                                Map<String,Object> user = new HashMap<>(); //Hash do usuário criado agora
                                user.put("E-mail",email.getText().toString().trim()); //pegar e-mail
                                user.put( "UserID", task.getResult().getUser().getUid() ); //pegar id que acabou de ser cadastrado com o logado agora
                                user.put( "Cliente", true ); //Tipo de conta criado para pesquisa e classificação posterior
                                db.collection( "Usuários" ).document(email.getText().toString().trim()).set( user );
                                //Adiciona pro banco
                                /*db.collection("Usuários")
                                        .add(user)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) { //Se deu certo

                                                //Log.d("Teste","OnClick ID: " + mAuth.getUid().toString());
                                                //Log.d( "teste", "DocumentSnapshot added with ID: " + documentReference.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() { //Se deu errado
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("teste", "Error adding document", e);
                                            }
                                        });*/


                                //instanciar banco de dados com o tipo de dados usuario
                                //´pegar o usuario salvo e comparar
                                FirebaseDatabase.getInstance().getReference( "Usuario" )
                                        .child( FirebaseAuth.getInstance().getCurrentUser().getUid() )
                                        .setValue( usuario ).addOnCompleteListener( new OnCompleteListener<Void>() { //com o ID do usuario que salvamos(obj), para confirmar se foi correto
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //por algum motivo o c´´odigo nao ta vindo aqui para confirmar sucesso? Oh Well
                                        //o codigo vai continuar aqui por precaucao e pra caso de correçao necessaria ja estar aqui

                                        if (task.isSuccessful()) { //se o usuario foi salvo corretamente no banco de dados
                                            //notificaç~~ao de sucesso na tela
                                            Log.d( "teste", "onComplete: PORQUE TU TA AQUI???" );

                                            //por algum motivo o c´´odigo nao ta vindo aqui para confirmar sucesso? Oh Well
                                            Toast.makeText( Cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG ).show();
                                            //IrLogin(); //Voltar ao login p´´os cadastro
                                        } else {
                                            Toast.makeText( Cadastro.this, "Erro ao cadastrar Usuário", Toast.LENGTH_LONG ).show();
                                        }
                                    }
                                } );
                                //como ele n ta entrando na  verificacao anterior, mas por precaucao, vai o IrLogin aqui
                                Log.d("Teste","Tamo voltando");
                                CadastrarCliente();
                                IrLogin(); //Voltar ao login p´´os cadastro
                            } else { //Caso dados n~~ao tenham sido sequer tentado de salvos
                                Toast.makeText( Cadastro.this, "Erro ao cadastrar Usuário", Toast.LENGTH_LONG ).show();
                            }
                        }
                    } );

    }

    private void CadastrarCliente(){

        Map<String,Object> cliente = new HashMap<>();
        cliente.put("Nome", nome.getText().toString().trim());
        cliente.put("CPF",cpf.getText().toString().trim());
        cliente.put( "E-mail", email.getText().toString().trim() );
        db.collection("Clientes").document().set( cliente );
        //cliente.put( "E-mail", db.collection( "Usuários" ).document(email.getText().toString().trim()));
        //cliente.put("UserID",userID);
        //db.collection("Clientes").document(cpf.getText().toString().trim()).set( cliente );
        //usar set invés de add pra ter um id customizado
                /*.add(cliente)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) { //Se deu certo

                        Log.d( "teste", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() { //Se deu errado
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("teste", "Error adding document", e);
                    }
                });*/
    }

    private Boolean Validar(String e, String s, String sR,String n, String c) {

        if (n.isEmpty()) {
            nome.setError( "Preencha o Nome" );
            nome.requestFocus();
            return false;
        }
        if (c.isEmpty()) {
            cpf.setError( "Preencha o CPF" );
            cpf.requestFocus();
            return false;
        }
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
        if (sR.isEmpty()) {
            senha.setError( "Preencha a Senha" );
            senha.requestFocus();
            return false;
        }
        if (!sR.matches( s )) {
            senhaRep.setError( "Senhas não coincidem!" );
            senhaRep.requestFocus();
            return false;
        }
        if (s.length() < 6) {
            senha.setError( "Senha deve ter ao menos 6 caracteres!" );
            senha.requestFocus();
            return false;
        }
        return true;
    }
}