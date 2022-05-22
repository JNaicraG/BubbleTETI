package com.example.bubbleteti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button entrar, empresa;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        db = FirebaseFirestore.getInstance();

        entrar = (Button) findViewById( R.id.btnEntrar );
        entrar.setOnClickListener( this );

        empresa = (Button) findViewById( R.id.btnEmpresa );
        empresa.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnEntrar:
                Log.d( "Teste", "onClick: Entrando na tela de Login" );
                startActivity( new Intent(this, Login.class ) );
                //CriarEmpresa();
                break;
            case R.id.btnEmpresa:
                Log.d( "Teste", "onClick: Entrando na tela de EMPRESAS" );
                startActivity( new Intent(this, Empresa.class) );
                break;
            default:
                Log.d( "Erro", "onClick: Oh ceus, deu erro" );
                break;
        }
        Log.d( "Teste", "onClick: AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAS" );

        //AtualizarEmpresa();
    }



    private void CriarEmpresa(){
        //Adicionar dados
        Map<String,Object> empresa = new HashMap<>();
        empresa.put("Nome Fantasia", "Empresa");
        empresa.put("Nome","Empresa Ltda" );
        empresa.put( "CNPJ", "XX.XXX.XXX/YYYY-AA");
        String data = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        empresa.put("Criação",data);

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