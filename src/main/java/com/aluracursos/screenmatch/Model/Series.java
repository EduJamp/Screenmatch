/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluracursos.screenmatch.Model;

import com.aluracursos.screenmatch.Enum.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;
import java.util.OptionalDouble;

/**
 *
 * @author Edu
 */

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(unique = true)
    private String title;
    
    @Enumerated(EnumType.STRING)
    private Category gender;
    
    private String poster;
    
    private String actors;
    
    private int totalSeasons;
    
    private double evaluation;
    
    private String synopsis;
    
    @Transient
    private List<Episode> episodes;
    
    public Series() {
    }
    
    public Series(DatosSerie datosSerie) {
        this.title = datosSerie.titulo();
        this.gender = Category.fromString(datosSerie.gender().split(",")[0].trim());
        this.poster = datosSerie.poster();
        this.actors = datosSerie.actors();
        this.totalSeasons = datosSerie.totalTemporadas();
        this.evaluation = OptionalDouble.of(Double.parseDouble(datosSerie.evaluacion())).orElse(0);
        this.synopsis = datosSerie.synopsis();
//        this.synopsis = QueryGemini.getTranslation(datosSerie.synopsis());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getGender() {
        return gender;
    }

    public void setGender(Category gender) {
        this.gender = gender;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(int totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "title: " + title + "\n gender: " + gender + "\n poster: " + poster + "\n actors: " + 
                actors + "\n totalSeasons: " + totalSeasons + "\n evaluation: " + evaluation + "\n synopsis: " + synopsis
                + "\n" + "-".repeat(100) + "\n";
    }
}
