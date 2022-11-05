package com.ladeus.paseo_viernes;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText jetcodigo,jetnombre,jetciudad,jetcantidad;
    CheckBox jcactivo;
    String codigo,nombre,ciudad,cantidad,codigoId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    byte  sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ocultar barra de titulo y asociar objetos Java y XML
        jetcodigo = findViewById(R.id.etcodigo);
        jetnombre = findViewById(R.id.etnombre);
        jetciudad = findViewById(R.id.etciudad);
        jetcantidad = findViewById(R.id.etcantidad);
        jcactivo = findViewById(R.id.cbactivo);
        sw = 0;
    }
    public  void Adicionar(View view){
        codigo = jetcodigo.getText().toString();
        nombre = jetnombre.getText().toString();
        ciudad = jetciudad.getText().toString();
        cantidad = jetcantidad.getText().toString();
        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty() || cantidad.isEmpty()){
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Codigo", codigo);
        user.put("Nombre", nombre);
        user.put("Ciudad", ciudad);
        user.put("Cantidad", cantidad);
        user.put("Activo", "Si");

// Add a new document with a generated ID
        db.collection("factura")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Datos Guardados Correctamente!", Toast.LENGTH_SHORT).show();
                        Limpiar_Campos();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "No Se Guardaron Los Datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void Modificar(View view){
        if (sw == 0){
            Toast.makeText(this, "Para modificar debe primero consultar", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else{
            codigo=jetcodigo.getText().toString();
            nombre=jetnombre.getText().toString();
            ciudad=jetciudad.getText().toString();
            cantidad=jetcantidad.getText().toString();
            if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty() || cantidad.isEmpty()){
                Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
                jetcodigo.requestFocus();
            }
            else {
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Codigo", codigo);
                user.put("Nombre", nombre);
                user.put("Ciudad", ciudad);
                user.put("Cantidad", cantidad);
                user.put("Activo", "Si");
                db.collection("factura").document(codigoId)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Estudiante actualizado correctmente...",Toast.LENGTH_SHORT).show();
                                Limpiar_Campos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Error actualizando estudiante...",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
    public void Eliminar(View view){
        if (sw == 0){
            Toast.makeText(this, "Para modificar debe primero consultar", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else{
            codigo=jetcodigo.getText().toString();
            if (codigo.isEmpty()){
                Toast.makeText(this, "El codigo es requerido", Toast.LENGTH_SHORT).show();
                jetcodigo.requestFocus();
            }
            else {
                // Create a new user with a first and last name
                db.collection("factura").document(codigoId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Documento eliminado correctmente...",Toast.LENGTH_SHORT).show();
                                Limpiar_Campos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Error elminando documento...",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
    public void Anular(View view){
        if (sw == 0){
            Toast.makeText(this, "Para modificar debe primero consultar", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else{
            codigo=jetcodigo.getText().toString();
            nombre=jetnombre.getText().toString();
            ciudad=jetciudad.getText().toString();
            cantidad=jetcantidad.getText().toString();
            if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty() || cantidad.isEmpty()){
                Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
                jetcodigo.requestFocus();
            }
            else {
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Codigo", codigo);
                user.put("Nombre", nombre);
                user.put("Ciudad", ciudad);
                user.put("Cantidad", cantidad);
                user.put("Activo", "No");
                db.collection("factura").document(codigoId)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Documento anulado correctmente...",Toast.LENGTH_SHORT).show();
                                Limpiar_Campos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Error anulando documento...",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }


    private void Limpiar_Campos(){
        jetcodigo.setText("");
        jetnombre.setText("");
        jetciudad.setText("");
        jetcantidad.setText("");
        jetcodigo.requestFocus();
        jcactivo.setChecked(false);
        sw = 0;
    }
    public void Consultar(View view){
        codigo=jetcodigo.getText().toString();
        if (codigo.isEmpty()){
            Toast.makeText(this, "Codigo es requerido", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else{
            db.collection("factura")
                    .whereEqualTo("Codigo",codigo)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                sw=1;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getString("Activo").equals("No")){
                                        Toast.makeText(MainActivity.this, "Documento existe pero esta anulado", Toast.LENGTH_SHORT).show();
                                        Limpiar_Campos();
                                    }
                                    else {
                                        codigoId = document.getId();
                                        jetnombre.setText(document.getString("Nombre"));
                                        jetciudad.setText(document.getString("Ciudad"));
                                        jetcantidad.setText(document.getString("Cantidad"));
                                        jcactivo.setChecked(true);
                                        //Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Documento no existe", Toast.LENGTH_SHORT).show();
                                //Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
    }
}