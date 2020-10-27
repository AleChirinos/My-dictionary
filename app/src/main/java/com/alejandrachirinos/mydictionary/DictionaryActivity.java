package com.alejandrachirinos.mydictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {
    private List<modelWords> items = new ArrayList<>();
    private Context context;

    private ListView taskListView;
    private Button addButton;
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
            }
        });
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