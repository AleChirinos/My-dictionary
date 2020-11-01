package com.alejandrachirinos.mydictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ListaDePalabras extends AppCompatActivity {

        static List<modelWords> items = new ArrayList<>();
        static List<modelWords> palabras;
        static int tamanoAlfabeto = 26;

        private Context context;

        private ListView taskListView;
        private Button searchButton;
        private EditText wordEditText;

        private WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_dictionary2);
        Bundle bundle = getIntent().getExtras();
        palabras = bundle.getParcelableArrayList("mylist");
        //receiveValues();
        initViews();
       // addEvents();
    }

        private void initViews() {

           // searchButton = findViewById(R.id.searchButton);
            //wordEditText = findViewById((R.id.wordEditText));
            taskListView = findViewById(R.id.taskListView);
            adapter = new WordAdapter(context, palabras);
            taskListView.setAdapter(adapter);
        }

        private void addEvents() {

            searchButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String guardarPalabra = wordEditText.getText().toString();
                    busqueda(guardarPalabra);
                    adapter = new WordAdapter(context, palabras);
                }
            });
        }

    private void receiveValues() {
        //Intent intent = getIntent();
        //if (intent.hasExtra(Constants.INTENT_KEY_USER)) {
           // String userObj = intent.getStringExtra(Constants.INTENT_KEY_USER);
             //palabras  = new Gson().fromJson(userObj, ArrayList.class);
        //}
        Intent data = null;
        Bundle objetoRecibido = data.getExtras();
        palabras = (List<modelWords>) objetoRecibido.getSerializable("proyecto");
    }

        static class Nodo {
            Map<Character, Nodo> hijos;
            boolean FinDePalabra;
            modelWords modelo;
            Nodo(String name, String descripcion) {
                FinDePalabra = false;
                hijos = new HashMap<>();
                if (FinDePalabra) {
                    modelo = new modelWords(items.size(), name, descripcion);
                }
            }
        }

    static Nodo raiz=new Nodo(null, null);

    static void agregarPalabra(modelWords palabra) {
        Nodo nodoActual = raiz;
        int letras = palabra.getName().length();
        for (int i = 0; i < letras; i++) {
            if (nodoActual.hijos.containsKey(palabra.getName().charAt(i))) {
                nodoActual = nodoActual.hijos.get(palabra.getName().charAt(i));
            } else {
                nodoActual.hijos.put(palabra.getName().charAt(i),
                        new Nodo(palabra.getName(), palabra.getDescription()));
                nodoActual = nodoActual.hijos.get(palabra.getName().charAt(i));
            }
        }
        nodoActual.FinDePalabra = true;
        nodoActual.modelo = new modelWords(palabra.getId(), palabra.getName(), palabra.getDescription());
        items.add(nodoActual.modelo);
    }

    static void busqueda(String palabra) {
        int letras = palabra.length();
        Nodo nodoActual = raiz;
        palabras = new ArrayList<>();
        for (int i = 0; i < letras; i++) {
            if (nodoActual.hijos.containsKey(palabra.charAt(i))) {
                nodoActual = nodoActual.hijos.get(palabra.charAt(i));
            } else {
                return;
            }
        }
        dfs(nodoActual);
    }

    static void dfs(Nodo nodoActual) {
        if (nodoActual.modelo != null && !palabras.contains(nodoActual.modelo)) {
            palabras.add(nodoActual.modelo);
        }
        if (nodoActual.hijos.isEmpty()) {
            return;
        } else {
            Object[] nodosHijos = nodoActual.hijos.values().toArray();
            for (int i = 0; i < nodoActual.hijos.size(); i++) {
                dfs((Nodo) nodosHijos[i]);
            }
        }
    }

    static void borrarPalabra(String palabra) {
        int letras = palabra.length();
        Nodo nodoActual = raiz;
        for (int i = 0; i < letras; i++) {
            char caracter = palabra.charAt(i);
            if (!nodoActual.hijos.containsKey(caracter)) {
                break;
            } else {
                nodoActual = nodoActual.hijos.get(caracter);
            }
        }
        if (nodoActual.FinDePalabra) {
            items.remove(items.indexOf(nodoActual.modelo));
            nodoActual.FinDePalabra = false;
            nodoActual.modelo = null;
        }
    }

}

