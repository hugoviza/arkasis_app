package com.example.arkasis.utilerias;

import java.text.Normalizer;

public class Utileria {
    public static String cleanString(String texto) {
        if(texto == null) return "";

        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }
}
