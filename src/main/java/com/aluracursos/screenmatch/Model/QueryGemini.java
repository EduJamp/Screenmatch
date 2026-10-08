/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluracursos.screenmatch.Model;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

/**
 *
 * @author Edu
 */
public class QueryGemini {
    
//    Creamos un metodo para obtener la traduccion de nuestra sinopsis usando el api de Gemini
    public static String getTranslation(String text) {
        if (text == null || text.trim().isEmpty()) return "Sin sinopsis";
//        if (text.length() > 1000) text = text.substring(0, 1000);
        
        String modelName = "gemini-3-flash-preview"; //aqui colocamos la version que vamos a usar gemini-2.0-flash-lite
        String prompt = "Eres un traductor estricto. Tu salida debe contener ÚNICAMENTE el texto traducido. No saludes, no expliques, "
                        + "Ahora traduce el siguiente texto: " + text; //aqui colocamos la consulta que deseamos hacerle a la IA
        
        GenerateContentConfig config = GenerateContentConfig.builder()
                                    .temperature(0.1f) // Baja creatividad para mayor precisión
                                    .candidateCount(1)// Solo una traducción
                                    .maxOutputTokens(4096)
                                    .build();
        
        Client client = new Client.Builder()
                            .apiKey("AIzaSyBzi0v2-UAQTBgPKpiJO4_NmYYuXGlrNNs")
                            .build();
        
        int maxRetries = 3; // Intentaremos hasta 3 veces si el servidor falla
        long waitTime = 5000; // Iniciamos esperando 5 segundos
        
        for (int i = 0; i < maxRetries; i++) {
            try {
                GenerateContentResponse answer = client.models
                                                    .generateContent(modelName, 
                                                            prompt, 
                                                            config //Parametro para configuraciones adicionales puede ser null si no hay alguna configuracion
                                                    );

                if (answer != null && !answer.text().isEmpty()) {
                    return answer.text().trim();
                }

            }catch(Exception e) {
                String errorMsg = e.getMessage();
            
                // Si el error es por cuota (429) o sobrecarga (503), esperamos y reintentamos
                if (errorMsg.contains("429") || errorMsg.contains("503") || errorMsg.contains("overloaded")) {
                    System.err.println("Servidor ocupado o cuota excedida. Reintentando en " + (waitTime / 1000) + "s...");
                    try {
                        Thread.sleep(waitTime);
                        waitTime *= 2; // Doblamos el tiempo de espera (Retroceso Exponencial)
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    // Si es un error diferente (como API Key inválida), detenemos el proceso
                    System.err.println("Error crítico: " + errorMsg);
                    break;
                }
            }
            
        }
        
        return null;
    }
}
