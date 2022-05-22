package com.example.bubbleteti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Empresa extends AppCompatActivity  implements View.OnClickListener{
    private Button atualizar, criar, excluir; //Bot~~oes
    private EditText email, nome, nomeF;
    private EditText cnpj, data;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_empresa );


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        atualizar = (Button) findViewById( R.id.btnAtualizar );
        atualizar.setOnClickListener( this );

        criar = (Button) findViewById( R.id.btnCriar );
        criar.setOnClickListener( this );

        excluir = (Button) findViewById( R.id.btnExcluir );
        excluir.setOnClickListener( this );

        email = (EditText) findViewById( R.id.txtEmailEmpresa );
        nome = (EditText) findViewById( R.id.txtNomeEmpresa );
        nomeF = (EditText) findViewById( R.id.txtNomeFantasia );
        cnpj = (EditText) findViewById( R.id.txtCNPJ );
        data = (EditText) findViewById( R.id.txtData );


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAtualizar:
                Atualizar();
                break;
            case R.id.btnExcluir:
                Excluir();
                break;
            case R.id.btnCriar:
                Criar();
                break;
            default:
                Log.d( "Erro", "onClick: Oh ceus, deu erro" );
                break;
        }
    }

    private void Criar(){
        //Adicionar dados
        Map<String,Object> empresa = new HashMap<>();
        empresa.put("Nome Fantasia", nomeF.getText().toString());
        empresa.put("Nome",nome.getText().toString() );
        empresa.put( "CNPJ", cnpj.getText().toString());
        empresa.put( "E-mail", email.getText().toString());
        //String data = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        empresa.put("Criação",data.getText().toString());

        //Colocar no BD
        db.collection( "Empresas" ).document("Empresa").set( empresa );

                /*.addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d( "teste", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText( MainActivity.this, "Sucesso ao criar a bagaça", Toast.LENGTH_LONG ).show();
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("teste", "Error adding document", e);
                    }
                } );*/
        Toast.makeText(Empresa.this,"Empresa criada com sucesso!",Toast.LENGTH_LONG ).show();
    }

    private void Atualizar(){
        Map<String,Object> empresa = new HashMap<>();
        empresa.put("Nome Fantasia", nomeF.getText().toString());
        empresa.put("Nome",nome.getText().toString() );
        empresa.put( "CNPJ", cnpj.getText().toString());
        empresa.put( "E-mail", email.getText().toString());
        empresa.put("Criação",data.getText().toString());
        //String data = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //empresa.put("Criação",data);

        db.collection( "Empresas" ).document("Empresa").update( empresa );

        Toast.makeText(Empresa.this,"Empresa atualizada com sucesso!",Toast.LENGTH_LONG ).show();
    }

    private void Excluir(){
        db.collection( "Empresas" ).document("Empresa").delete();
        Toast.makeText(Empresa.this,"Empresa excluída com sucesso!",Toast.LENGTH_LONG ).show();
    }

}