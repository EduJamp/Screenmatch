/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.aluracursos.screenmatch.Enum;

/**
 *
 * @author Edu
 */
public enum Category {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIMEN("Crimen");
    
    private String categoryOmdb;
    
    Category(String categoryOmdb) {
        this.categoryOmdb = categoryOmdb;
    }
    
    public static Category fromString(String text) {
        for (Category categoria : Category.values()) {
            if (categoria.categoryOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        
        throw new IllegalArgumentException("Ninguna categoria encontrada " + text);
    }
}
