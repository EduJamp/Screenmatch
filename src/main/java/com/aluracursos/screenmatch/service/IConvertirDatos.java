/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.aluracursos.screenmatch.service;

/**
 *
 * @author USER
 */
public interface IConvertirDatos {
    
    //metodo para obtener los datos, pero se usa genericos para garantizar la escalabilidada de la app
    <T> T obtenerDatos(String json, Class<T> clase);
    
}
