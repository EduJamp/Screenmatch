/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.aluracursos.screenmatch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author USER
 */

@JsonIgnoreProperties(ignoreUnknown = true) /* con esta linea le decimos que ignore los otros campos y que solo traiga los datos que pedimos para
                                                evitar errores al momentode que el ide quiere leer los datos*/
public record DatosSerie(
        @JsonAlias("Title") String titulo,
        
        @JsonAlias("Genre") String gender,
        
        @JsonAlias("Poster") String poster,
        
        @JsonAlias("Plot") String synopsis,
        
        @JsonAlias("Actors") String actors,
        
        @JsonAlias("totalSeasons") int totalTemporadas,
        
        @JsonAlias("imdbRating") String evaluacion
        
        ) {

}

//@JsonAlias --> permite solo leer los datos de nuestro servidor(api)
//@JsonProperty --> permite leer y escribir los datos, osea tambien podems mandar datos con el jsonproperti
//ambos nos permiten que el ide sepa cuales son los datos que se van a traer y los juarga en las respectivas variables