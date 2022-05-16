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
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_inicio );
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bInicio = (Button) findViewById( R.id.btnInicio );
        bInicio.setOnClickListener( this );
    }



    @Override
    public void onClick(View view) {
        CriarEmpresa();
        Toast.makeText( Inicio.this, "Dados da Empresa", Toast.LENGTH_LONG ).show();
        //startActivity( new Intent(this, Login.class) );
    }

    private void CriarEmpresa(){
        //Adicionar dados
        Map<String,Object> empresa = new HashMap<>();
        empresa.put("Nome Fantasia", "Empresa");
        empresa.put("Nome","Empresa Ltda" );
        empresa.put( "CNPJ", "XX.XXX.XXX/YYYY-AA");
        String data = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        empresa.put("Criação",data);

        Toast.makeText( Inicio.this, "Dados da Empresa", Toast.LENGTH_LONG ).show();
        //Colocar no BD
        db.collection( "Empresas" ).add( empresa )
                .addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d( "teste", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText( Inicio.this, "Sucesso ao criar a bagaça", Toast.LENGTH_LONG ).show();
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("teste", "Error adding document", e);
                    }
                } );
    }

    private void AtualizarEmpresa(){
        Map<String,Object> empresa = new HashMap<>();
        empresa.put("Nome Fantasia", "Bubble.TETI");
        empresa.put("Nome","BUBBLE TETI ASSOCIAÇÃO S.A" );
        empresa.put( "CNPJ", "XX.XXX.XXX/YYYY-ZZ");
        //String data = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //empresa.put("Criação",data);

        db.collection( "Empresas" ).document("Empresa").update( empresa );

    }


}