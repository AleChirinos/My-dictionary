package com.alejandrachirinos.mydictionary;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DictionaryActivity extends AppCompatActivity {
        public static String LOG = com.alejandrachirinos.mydictionary.MainActivity.class.getName();
        private Button buttonStart;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dictionary);}
}
