package com.alejandrachirinos.mydictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EliminarPalabraActivity extends AppCompatActivity {

    private Context context;

    private ListView taskListView;
    private Button done;
    private EditText deleteword;

    private WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_eliminarpalabra);
        initViews();
        addEvents();
    }

    private void initViews() {
        done = findViewById(R.id.doneDelete);
        deleteword = findViewById((R.id.editTextPalabraEliminar));
    }

    private void addEvents() {
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String borrar = deleteword.getText().toString();
                ArrayList<modelWords> borrarPalabras= new ArrayList<>();
                borrarPalabras.add(new modelWords(7,borrar,"xd"));
                Intent intent = new Intent(EliminarPalabraActivity.this, DictionaryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("delete", borrarPalabras);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}