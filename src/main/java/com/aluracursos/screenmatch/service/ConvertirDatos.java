/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluracursos.screenmatch.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author USER
 */

public class ConvertirDatos implements IConvertirDatos{
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        
        try {
            
            return objectMapper.readValue(json, clase);
            
        } catch (JsonProcessingException ex) {
            
            throw new RuntimeException(ex);
            
        }
        
    }
    
}
