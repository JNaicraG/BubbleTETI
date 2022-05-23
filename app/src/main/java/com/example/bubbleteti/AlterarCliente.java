package com.example.bubbleteti;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlterarCliente extends AppCompatActivity implements View.OnClickListener {

    private Button atualizar, voltar, excluir; //Bot~~oes
    private EditText nome, cpf, email;
    private ArrayList<ClienteBeans> clienteArrayList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_alterar_cliente );

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        atualizar = (Button) findViewById( (R.id.btnAtualizarCliente) );
        atualizar.setOnClickListener( this );

        excluir = (Button) findViewById( (R.id.btnExcluirCliente) );
        excluir.setOnClickListener( this );

        voltar = (Button) findViewById( (R.id.btnVoltarCliente) );
        voltar.setOnClickListener( this );

        email = (EditText) findViewById( R.id.txtEmailCC );
        nome = (EditText) findViewById( R.id.txtNomeCC );
        cpf = (EditText) findViewById( R.id.txtCPFCC );

        clienteArrayList = new ArrayList<ClienteBeans>();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAtualizarCliente:
                Atualizar();
                break;
            case R.id.btnExcluirCliente:
                Excluir();
                break;
            case R.id.btnVoltarCliente:
                Voltar();
                break;
            default:
                Log.d( "Erro", "onClick: Oh ceus, deu erro" );
                break;
        }
    }



    private void Pesquisar(){
        //DocumentReference clienteDoc = db.collection( "Clientes" ).document(email.getText().toString().trim());


        db.collection( "Clientes" ).addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.e("erro", error.getMessage());
                    return;
                }
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    if(documentChange.getType() == DocumentChange.Type.ADDED){
                        //clienteArrayList.add(documentChange.getDocument().toObject(ClienteBeans.class));
                        clienteArrayList.add(documentChange.getDocument().toObject(ClienteBeans.class));
                    }
                }
            }
        } );
        ClienteBeans clienteB = clienteArrayList.get( 0);

        cpf.setText( clienteB.getCpf().toString() );
        nome.setText( clienteB.getNome().toString() );
        email.setText( clienteB.getEmail().toString() );
    }

    private void Atualizar(){
        Map<String,Object> cliente = new HashMap<>();
        cliente.put("Nome", nome.getText().toString().trim());
        cliente.put( "CPF" , cpf.getText().toString().trim());
        //String data = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //empresa.put("Criação",data);

        db.collection( "Clientes" ).document(cpf.getText().toString().trim()).update( cliente );

        Toast.makeText(AlterarCliente.this,"Cliente " + nome.getText().toString().trim() + " atualizada com sucesso!",Toast.LENGTH_LONG ).show();
    }

    private void Excluir(){
        db.collection( "Clientes" ).document(cpf.getText().toString().trim()).delete();
        db.collection( "Usuários" ).document(email.getText().toString().trim()).delete();
        //mAuth.getCurrentUser().delete();

        //Descobrir como excluir usuário antes de só excluir o cliente
        //no caso, excluir um usuário que não o atualmente logado (isso dá pra fazer), como se isso fosse um
        Toast.makeText(AlterarCliente.this,"Cliente " + nome.getText().toString().trim() +" excluída com sucesso!",Toast.LENGTH_LONG ).show();
        Voltar();
    }

    private void Voltar(){
        startActivity( new Intent( this, MainActivity.class ) );
    }
}