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

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTemporada(
        
        @JsonAlias("Season") int number,
        @JsonAlias("Episodes") List<DatosEpisodio> episodes
        
        ) {

}
