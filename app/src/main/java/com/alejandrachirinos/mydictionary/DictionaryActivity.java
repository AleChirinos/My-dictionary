package com.alejandrachirinos.mydictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {
    static List<modelWords> items = new ArrayList<>();
    static List<modelWords> palabras;
    static int tamanoAlfabeto = 26;
    private Context context;

    private ListView taskListView;
    private Button addButton;
    private Button deleteButton;
    private Button searchButton;
    private EditText wordEditText;

    private WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_dictionary);
        initViews();
        addEvents();
        fillWords();
    }

    private void initViews() {
        addButton = findViewById(R.id.addButton);
        deleteButton=findViewById(R.id.deleteButton);
        searchButton = findViewById(R.id.searchButton);
        wordEditText = findViewById((R.id.wordEditText));
        taskListView = findViewById(R.id.taskListView);
        adapter = new WordAdapter(context, items);
        taskListView.setAdapter(adapter);
    }

    private void addEvents() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(new modelWords(items.size(), "Nombre", "Significado"));
                adapter.notifyDataSetChanged();
                Intent addIntent = new Intent(DictionaryActivity.this, AgregarPalabraActivity.class);
                startActivity(addIntent);

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteIntent = new Intent(DictionaryActivity.this, EliminarPalabraActivity.class);
                startActivity(deleteIntent);
            }
        });
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

    static Nodo raiz;

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

    private void fillWords() {
        items.add(new modelWords(items.size(), "Abuhado", "Aquellas personas quienes tienen una apariencia que recuerda a la de un búho o ave similar."));
        items.add(new modelWords(items.size(), "Acecinar", "Acto de salar las carnes y ponerlas al aire. Acción de convertir un producto cárnico en cecina."));
        items.add(new modelWords(items.size(), "Agigolado", "Adjetivo, típico de la provincia de Segovia, que se usa para describir aquel a quien, al realizar algo con un poco de esfuerzo, siente que se ahoga y percibe una presión en el pecho."));
        items.add(new modelWords(items.size(), "Bonhomía", "Afabilidad, sencillez, bondad y honradez en el carácter."));
        items.add(new modelWords(items.size(), "Cagaprisas", "Persona que es impaciente, quien tiene prisa siempre."));
        items.add(new modelWords(items.size(), "Entronque", "Relación de parentesco entre personas quienes comparten un tronco del linaje en común."));
        items.add(new modelWords(items.size(), "Inmarcesible", "Dicho de un vegetal que no puede marchitarse."));
    }
}