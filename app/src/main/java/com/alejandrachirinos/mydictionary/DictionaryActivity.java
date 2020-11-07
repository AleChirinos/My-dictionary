package com.alejandrachirinos.mydictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity{
    static List<modelWords> items = new ArrayList<>();
    static ArrayList<modelWords> palabras = new ArrayList<>();
    static ArrayList<modelWords> agregar;

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
        ArrayList<modelWords> agregar = new ArrayList<>();
        ArrayList<modelWords> eliminar = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            agregar = bundle.getParcelableArrayList("palabras");
            eliminar = bundle.getParcelableArrayList("delete");
            if(agregar != null){
                agregarPalabra(agregar.get(0));
            }
            if(eliminar!= null){
                borrarPalabra(eliminar.get(0).getName());
            }
            initViews();
            addEvents();
        }else {
            initViews();
            addEvents();
            fillWords();
        }
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

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String guardarPalabra = wordEditText.getText().toString();
                busqueda(guardarPalabra);
                Intent intent = new Intent(DictionaryActivity.this, ListaDePalabras.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("mylist", palabras);
                intent.putExtras(bundle);
                startActivity(intent);


                //Intent searchIntent = new Intent(DictionaryActivity.this, ListaDePalabras.class);
                //String userString = new Gson().toJson(palabras);
                //searchIntent.putExtra(Constants.INTENT_KEY_USER, userString);
                //startActivity(searchIntent);
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

    private void fillWords() {
        agregarPalabra(new modelWords(items.size(), "Abuhado", "Aquellas personas quienes tienen una apariencia que recuerda a la de un búho o ave similar."));
        agregarPalabra(new modelWords(items.size(), "Acecinar", "Acto de salar las carnes y ponerlas al aire. Acción de convertir un producto cárnico en cecina."));
        agregarPalabra(new modelWords(items.size(), "Agigolado", "Adjetivo, típico de la provincia de Segovia, que se usa para describir aquel a quien, al realizar algo con un poco de esfuerzo, siente que se ahoga y percibe una presión en el pecho."));
        agregarPalabra(new modelWords(items.size(), "Arrebol", "Es el efecto de la luz del Sol al proyectarse sobre las nubes matutinas y de la tarde, que les otorga tonalidades rojizas"));
        agregarPalabra(new modelWords(items.size(), "Bahorrina", "Conjunto de muchas cosas asquerosas que se han echado en agua, la cual se ha tornado sucia. También significa conjunto de gente soez y ruin."));
        agregarPalabra(new modelWords(items.size(), "Bonhomía", "Afabilidad, sencillez, bondad y honradez en el carácter."));
        agregarPalabra(new modelWords(items.size(), "Burdégano", "Híbrido entre un caballo y una asna."));
        agregarPalabra(new modelWords(items.size(), "Cagaprisas", "Persona que es impaciente, quien tiene prisa siempre."));
        agregarPalabra(new modelWords(items.size(), "Celaje", "Cuando en el cielo se pueden observar nubes de distintas texturas, formando un horizonte colorido en la puesta o salida del sol."));
        agregarPalabra(new modelWords(items.size(), "Conflictuar", "Acto de provocar un conflicto en alguien o en algo. También significa el sufrir un conflicto interno o preocupación que puede hacer que se cambie el comportamiento."));
        agregarPalabra(new modelWords(items.size(), "Depauperar", "Debilitar, extenuar física o moralmente, ya sea uno mismo o hacia otra persona."));
        agregarPalabra(new modelWords(items.size(), "Desleír", "Disolver algo, de contextura sólida o pastosa, en un líquido."));
        agregarPalabra(new modelWords(items.size(), "Deyección", "Defecación de los excrementos."));
        agregarPalabra(new modelWords(items.size(), "Ebúrneo", "Hecho de marfil o de un material que se le parece."));
        agregarPalabra(new modelWords(items.size(), "Entronque", "Relación de parentesco entre personas quienes comparten un tronco del linaje en común."));
        agregarPalabra(new modelWords(items.size(), "Esmegma", "Secreción de las glándulas prepuciales. Parte del semen más espesa."));
        agregarPalabra(new modelWords(items.size(), "Falcado", "Que tiene una curvatura similar a la de una hoz."));
        agregarPalabra(new modelWords(items.size(), "Farmacopea", "Repertorio o libro de recetas medicinales, pudiendo ser éstas tanto fármacos como fitoterapéuticas."));
        agregarPalabra(new modelWords(items.size(), "Ful", "Faso, fallido, que posee poco valor."));
        agregarPalabra(new modelWords(items.size(), "Garambaina", "Adorno de mal gusto u objetos que son pamplinas. También significa gestos de mal gusto"));
        agregarPalabra(new modelWords(items.size(), "Garlito", "Herramienta de pesca la cual consiste en una nasa en cuya parte más estrecha tiene una red con la que atrapar al pez."));
        agregarPalabra(new modelWords(items.size(), "Gaznápiro", "Tonto, palurdo, persona quien se emboba con cualquier cosa."));
        agregarPalabra(new modelWords(items.size(), "Haiga", "Coche de gran tamaño y ostentoso, como lo es una limusina, un todoterreno de lujo o un bus privado."));
        agregarPalabra(new modelWords(items.size(), "Heresiarca", "Quien promueve una herejía, quien enciende la chispa en un acto contra una religión."));
        agregarPalabra(new modelWords(items.size(), "Hermeneuta", "Persona que interpreta textos, normalmente de carácter religioso o ético, para fijar su verdadero sentido."));
        agregarPalabra(new modelWords(items.size(), "Histrión", "Actor teatral. También referido para aquellas personas quienes se expresan con la forma característica de una persona quien está sobreactuando."));
        agregarPalabra(new modelWords(items.size(), "Idiotismo", "Giro o expresión lingüística que no se ajusta a las reglas gramaticales."));
        agregarPalabra(new modelWords(items.size(), "Inmarcesible", "Dicho de un vegetal que no puede marchitarse."));
        agregarPalabra(new modelWords(items.size(), "Isagoge", "Introducción, preámbulo."));
        agregarPalabra(new modelWords(items.size(), "Jerapellina", "Vestido viejo y andrajoso, pieza de tela que no puede dar más de sí."));
        agregarPalabra(new modelWords(items.size(), "Jerigonza", "Lenguaje propio de algunos gremios, es decir, vocabulario especializado en un determinado ámbito profesional."));
        agregarPalabra(new modelWords(items.size(), "Jipiar", "Gemir, hipar, gimotear. También significa cantar con voz semejante a la de un gemido."));
        agregarPalabra(new modelWords(items.size(), "Joyel", "Joya pequeña."));
        agregarPalabra(new modelWords(items.size(), "Lábaro", "Estandarte que era usado por los antiguos romanos. También es el nombre del monograma formado por la cruz y las dos primeras letras del nombre griego de Cristo."));
        agregarPalabra(new modelWords(items.size(), "Lobanillo", "Bulto leñoso que se forma en la corteza de los árboles. También tiene su versión humana, que consiste en un bulto superficial, generalmente no doloroso, que se forma en la cabeza y otras partes del cuerpo."));
        agregarPalabra(new modelWords(items.size(), "Limerencia", "Locura de amor. Estado mental involuntario en el que la atracción de un persona hacia la otra le impide pensar de forma racional."));
        agregarPalabra(new modelWords(items.size(), "Melifluo", "Sonido excesivamente dulce, suave o delicado."));
        agregarPalabra(new modelWords(items.size(), "Mondo", "Dicho de algo que está limpio y libre de cosas extra, añadidas o superfluas."));
        agregarPalabra(new modelWords(items.size(), "Nadir", "Punto de la esfera celeste diametralmente opuesto al cenit."));
        agregarPalabra(new modelWords(items.size(), "Nefando", "Algo que causa repugnancia u horror cuando se habla de ella. Algo que es abominable y asqueroso a partes iguales."));
        agregarPalabra(new modelWords(items.size(), "Nefelibata", "Persona soñadora, quien se encuentra en la inopia y se mantiene aislada de lo duro y cruel que es este mundo."));
        agregarPalabra(new modelWords(items.size(), "Núbil", "Dicho de una persona, especialmente de una mujer, quien se encuentra en edad de casarse."));
        agregarPalabra(new modelWords(items.size(), "Ñengo", "Persona enclenque, flaca, desmedrada."));
        agregarPalabra(new modelWords(items.size(), "Ñomblón", "Dicho de una persona muy gorda, con buenas nalgas."));
        agregarPalabra(new modelWords(items.size(), "Ñuzco", "Uno de los nombres con el que se hace referencia al Diablo o príncipe de los ángeles del mal."));
        agregarPalabra(new modelWords(items.size(), "Ochavo", "Sinónimo de octavo, utilizado para hacer referencia a una octava parte de algo. También se usa para indicar que algo tiene poco valor. En la antigüedad era una moneda española de cobre con peso de un octavo de onza."));
        agregarPalabra(new modelWords(items.size(), "Oleaginoso", "Sinónimo de aceitoso, con textura de aceite."));
        agregarPalabra(new modelWords(items.size(), "Orate", "Persona quien no está en plenas facultades psíquicas, quien ha perdido el juicio."));
        agregarPalabra(new modelWords(items.size(), "Petricor", "Olor que desprende la tierra cuando ésta ha sido mojada por las gotas de la lluvia."));
        agregarPalabra(new modelWords(items.size(), "Plañir", "Gemir y llorar de tal forma que los demás lo escuchan. Sollozar y clamar."));
        agregarPalabra(new modelWords(items.size(), "Patibulario", "Dicho de alguien o algo que, a causa de su repugnante aspecto, produce un gran espanto y horror."));
        agregarPalabra(new modelWords(items.size(), "Patochada", "Disparate, dicho de algo necio, un despropósito."));
        agregarPalabra(new modelWords(items.size(), "Picio", "Dicho de alguien quien ha tenido la mala fortuna de ser excesivamente feo."));
        agregarPalabra(new modelWords(items.size(), "Quincalla", "Conjunto de objetos metálicos, con poco valor. Pueden ser tijeras, imitaciones de joyas, piezas de coche estropeadas..."));
        agregarPalabra(new modelWords(items.size(), "Recipiendario", "Persona quien es recibida solemnemente por la institución quien la recibe."));
        agregarPalabra(new modelWords(items.size(), "Regodeo", "Deleite ante el percance ajeno, acto de alegrarse por la desgracia ajena."));
        agregarPalabra(new modelWords(items.size(), "Regnícola", "Habitante natural de un reino. También dicho de quien escribe de las cosas especiales de su patria, como códigos penales, hábitos nacionales, cultura en general."));
        agregarPalabra(new modelWords(items.size(), "Réprobo", "Condenado a las penas eternas. También dicho de aquel quien es condenado por su heterodoxia religioso."));
        agregarPalabra(new modelWords(items.size(), "Sabrimiento", "Palabra antaño utilizada como sinónima de sabor. También se utilizaba para hacer referencia a un chiste o chascarrillo."));
        agregarPalabra(new modelWords(items.size(), "Sapenco", "Caracol terrestre con rayas pardas transversales común de la Europa más sureña."));
        agregarPalabra(new modelWords(items.size(), "Sempiterno", "Dicho de algo que durará para siempre. Algo que tiene inicio, pero no final."));
        agregarPalabra(new modelWords(items.size(), "Serendipia", "Hallazgo, que no estaba planificado, que ha resultado ser algo afortunado pese a que se estaba buscando otra cosa."));
        agregarPalabra(new modelWords(items.size(), "Trapisonda", "Riña con voces o acciones. También significa agitación del mar, formada por olas pequeñitas que se cruzan en diversos sentidos."));
        agregarPalabra(new modelWords(items.size(), "Testaferro", "Persona quien presta su nombre en un contrato que, en realidad, le correspondría firmar a otra persona."));
        agregarPalabra(new modelWords(items.size(), "Trémolo", "Concepto musical que describe una rápida sucesión de repeticiones de una misma nota."));
        agregarPalabra(new modelWords(items.size(), "Ubérrimo", "Dicho de algo que es muy fértil y abundante."));
        agregarPalabra(new modelWords(items.size(), "Ucronía", "Utopía aplicada a la historia. Reconstrucción contrafáctica de un evento histórico, de una forma en la que al final no fue."));
        agregarPalabra(new modelWords(items.size(), "Uebos", "Palabra, ya en desuso, que significa una necesidad o quehacer."));
        agregarPalabra(new modelWords(items.size(), "Unigénito", "Persona quien es hijo o hija única."));
        agregarPalabra(new modelWords(items.size(), "Vagido", "Gemido o llanto de un recién nacido."));
        agregarPalabra(new modelWords(items.size(), "Verbigracia", "Sinónimo de por ejemplo."));
        agregarPalabra(new modelWords(items.size(), "Vituperio", "Insulto, calumnia o infamia que provoca la acción de haber ofendido a alguien."));
        agregarPalabra(new modelWords(items.size(), "Vulpino", "Palabra usada para designar a todo aquello que esté relacionado con los zorros."));
        agregarPalabra(new modelWords(items.size(), "Xerofítico", "Dicho sobre aquellos vegetales que están adaptados por su estructura a los medios secos."));
        agregarPalabra(new modelWords(items.size(), "Xeroftalmia", "Enfermedad de los ojos en la que hay sequedad el el globo ocular y retracción de la conjuntiva, además de opacidad de la córnea."));
        agregarPalabra(new modelWords(items.size(), "Xeromicteria", "Sequedad de la mucosa nasal."));
        agregarPalabra(new modelWords(items.size(), "Zaino", "Traidor, falso, poco seguro en el trato."));
        agregarPalabra(new modelWords(items.size(), "Zangolotear", "Estar constantemente moviéndose de un lugar a otro sin propósito alguno."));
        agregarPalabra(new modelWords(items.size(), "Zonzo", "Soso, insulso e insípido. También dicho para referirse a alguien o algo que resulta ser bastante tonto."));

    }
}