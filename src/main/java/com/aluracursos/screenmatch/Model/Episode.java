/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluracursos.screenmatch.Model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Edu
 */
public class Episode {
    private int season;
    private String title;
    private int numberEpisode;
    private double evaluation;
    private LocalDate releaseDate;

    public Episode(int number, DatosEpisodio data) {
        this.season = number;
        this.title = data.title();
        this.numberEpisode = data.numberEpisode();
        try {
            this.evaluation = Double.valueOf(data.evaluation());
            
        } catch (NumberFormatException e) {
            this.evaluation = 0;
            
        }
        
        try {
            this.releaseDate = LocalDate.parse(data.releaseDate());
            
        } catch (DateTimeParseException e) {
            this.releaseDate = null;
            
        }
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberEpisode() {
        return numberEpisode;
    }

    public void setNumberEpisode(int numberEpisode) {
        this.numberEpisode = numberEpisode;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "season=" + season + 
                ", title=" + title + 
                ", numberEpisode=" + numberEpisode + 
                ", evaluation=" + evaluation + 
                ", releaseDate=" + releaseDate;
    }
}
