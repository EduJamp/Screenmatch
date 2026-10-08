/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.aluracursos.screenmatch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author USER
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisodio(
        
        @JsonAlias("Title") String title,
        
        @JsonAlias("Episode") int numberEpisode,
        
        @JsonAlias("imdbRating") String evaluation,
        
        @JsonAlias("Released") String releaseDate
        
        ) {

}
