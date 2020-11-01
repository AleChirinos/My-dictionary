package com.alejandrachirinos.mydictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AgregarPalabraActivity extends AppCompatActivity {
    private Button done;
    private EditText name;
    private EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarpalabra);
        initViews();
        addEvents();
    }
    private void initViews() {
        done = findViewById(R.id.done);
        name = findViewById((R.id.editTextPalabra));
        description = findViewById((R.id.editTextSignificado));
    }

    private void addEvents() {
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = name.getText().toString();
                String descripcion = description.getText().toString();
                if(nombre.isEmpty() || descripcion.isEmpty()){
                    Toast.makeText(AgregarPalabraActivity.this, "Campos incompletos",Toast.LENGTH_SHORT);
                }else{
                    modelWords add = new modelWords(5,nombre, descripcion);
                    ArrayList<modelWords> adds = new ArrayList<>();
                    adds.add(add);
                    Intent intent = new Intent(AgregarPalabraActivity.this, DictionaryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("palabras", adds);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }
}