/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluracursos.screenmatch.Main;

import com.aluracursos.screenmatch.Model.DatosEpisodio;
import com.aluracursos.screenmatch.Model.DatosSerie;
import com.aluracursos.screenmatch.Model.DatosTemporada;
import com.aluracursos.screenmatch.Model.Episode;
import com.aluracursos.screenmatch.Model.Series;
import com.aluracursos.screenmatch.repository.SeriesRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvertirDatos;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author USER
 */
public class Main {
    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvertirDatos conversor = new ConvertirDatos();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=85f4e24a";
    private List<DatosTemporada> seasons = new ArrayList<>();
    private List<DatosSerie> datosSerie = new ArrayList<>();
    private SeriesRepository repositorio;
    DatosSerie data;
    String nameSeries;

    public Main(SeriesRepository repository) {
        this.repositorio = repository;
    }
    
    public void showMenu() {
        int option = -1;
        
//            Creamos el menu para poder mostrarlo en consola
        String menu = """
                      1.- Buscar la serie
                      2.- Buscar Datos de la serie
                      3.- Buscar episodios de la serie indicada
                      4.- Ver historial de las series buscadas
                      0.- Salir
                      """;
        
        while (option != 0) {
            
            System.out.println(menu); //mostramos el menu
            
            String lectura = input.nextLine();
            
            try {
                option = Integer.parseInt(lectura);// usamos el input para poder hacer que el usuario ingrese un valor por teclado

    //          Procedemos a hacer la logica para crear el menu y quede funcional
                switch (option) {
                    case 1:
                        System.out.println("Ingresa el nombre de la serie que deseas buscar");

//              Obtenemos el nombre y lo modificamos para que se pueda buscar asi tenga espacios o tildes el nombre ingresado por el usuario
                        this.nameSeries = modifyNameSeason(input.nextLine());

                        data = getData(this.nameSeries);

                        if (data == null || data.titulo() == null) {
                            System.out.println("La serie '" + this.nameSeries + "' no existe. Inténtalo de nuevo.");
                            return;

                        } else {
                            System.out.println("Serie encontrada: " + data.titulo());
                            // Si existe, procedemos a guardar las temporadas
                            saveToListDataforSeason(data);

//                                Guardamos las series buscadas
//                            datosSerie.add(data);
                            
                            Series series = new Series(data);
                            repositorio.save(series);
                            System.out.println(data);

                        }
                    break;

                    case 2:
                        if (this.nameSeries != null && !this.nameSeries.isEmpty()) {
                            searchForDataFromTheSeries();
                        } else {
                            System.out.println("Primero debes buscar una serie en la opción 1");
                        }
                        break;    

                    case 3:
                        if (this.nameSeries != null && !this.nameSeries.isEmpty()) {
                            searchEpisodesBySeries();
                        } else {
                            System.out.println("Primero debes buscar una serie en la opción 1");
                        }
                        break;

                    case 4:
                        if (this.nameSeries == null) {
                            System.out.println("Primero tienes que buscar la serie");
                        }

                        viewHistory();
                        break;

                    case 0:
                        System.out.println("Cerrando la aplicacion.......");
                        break;

                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (NumberFormatException e) {
                // el programa saltará directamente aquí en lugar de cerrarse.
                System.out.println("ERROR: Entrada no válida. Debes ingresar ÚNICAMENTE números enteros.");
                option = -1; // Reiniciamos la opción para que el bucle continúe
            }
        }
    }
    
//    Metodo para bucasr las series pero encontrar informacion sobre ella
    public void searchForDataFromTheSeries() {
        System.out.println("*".repeat(100));
        System.out.println("Datos de la serie: " + this.data.titulo());
        System.out.println("*".repeat(100) + "\n");
        
        saveToListDataforSeason(this.data);
        
        List<Episode> list = convertDataToEpisodeList(seasons);
        
        System.out.println("Generos: " + data.gender());
        System.out.println("Poster: " + data.poster());
        System.out.println("Sinopsis: " + data.synopsis());
        System.out.println("Actores: " + data.actors());
        System.out.println("Total de Temporadas: " + data.totalTemporadas());
        System.out.println("Evaluacion: " + data.evaluacion());
        
        System.out.println("\n" + "<-->".repeat(30) + "\n");
        
        System.out.println("Mejores 5 Episodios de la serie");
        showTop5BestEpisodes(seasons);
        
        System.out.println("\n" + "<-->".repeat(30) + "\n");
        
        System.out.println("Estadisticas de la serie por temporada");
        showStatisticsFoTheSeason(list);
        
        System.out.println("\n" + "<-->".repeat(30));
    }
    
//    Metodo para buscar episodios segun la serie
    public void searchEpisodesBySeries() {
        String menuEpisodio = """
                                1.- Buscar por titulo de episodio
                                2.- Buscar por año de estreno
                                3.- Mostrar todos los episodios por temporada
                                0.- Volver al anterior menu
                              """;
        
        int opcion = -1;
        
        while (opcion != 0) {
            System.out.println(menuEpisodio);

            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1:
                    List<Episode> epi = convertDataToEpisodeList(this.seasons);
                    
                    searchForTitle(epi);
                    break;
                    
                case 2:
                    
                    List<Episode> episodes = convertDataToEpisodeList(this.seasons);
                    
                    showEpisodesFromTheYear(episodes);
                    break;
                    
                case 3:
                    showEpisodesForSeasons();
                    break;
                    
                case 0:
                    System.out.println("Regresando al menu principal");
                    return;

                default:
                    System.out.println("Opcion invalida");;
            }
        }
    }
    
//    Metodo para ver el historial de las series buscadas
    public void viewHistory() {
        List<Series> series = new ArrayList<>();
        
       /* series = datosSerie.stream()
                    .map(s -> new Series(s))
                    .collect(Collectors.toList());*/
       
       series = repositorio.findAll();
        
        series.stream()
                    .sorted(Comparator.comparing(Series::getGender))
                    .forEach(System.out::println);
    }
    
    public DatosSerie getData(String nombreSerie) {
//        Buscamos los datos mediante la url de la api y lo guardamos en una variable que es de tipo string pero se puedfe usar el tipo var
        String json = consumoApi.getData(URL_BASE + nombreSerie + API_KEY);
        
//        Convertimos los datos previamente obtenidos a datos del tippo de la clase
        DatosSerie dataSeries = conversor.obtenerDatos(json, DatosSerie.class);
        
        return dataSeries;
    }
    
//    Metodo para crear una lista que donde guardaremos todos los datos de las temporadas de la serie buscada
    public void saveToListDataforSeason(DatosSerie data) {
        for (int i = 1; i <= data.totalTemporadas(); i++) {
            var url = URL_BASE + this.nameSeries + "&Season=" + i + API_KEY;
            
            var json = consumoApi.getData(url);
            
            DatosTemporada dataForSeason = conversor.obtenerDatos(json, DatosTemporada.class);
            
            seasons.add(dataForSeason);
        }
    }
    
//    Metodo para mostrar todos los episodios por temporada seleccionada
    public void showEpisodesForSeasons() {
        System.out.println("Ingresa la temporada que deseas ver todos sus episodios");
        
        try {
            int season = input.nextInt();
            input.nextLine();
            
            var url = URL_BASE + this.nameSeries + "&Season=" + season + API_KEY;
            
            var json = consumoApi.getData(url);

            DatosTemporada data = conversor.obtenerDatos(json, DatosTemporada.class);

            
            if (data != null && data.episodes() != null && !data.episodes().isEmpty()) {
                System.out.println("\n--- Episodios de la Temporada " + season + " ---\n");
                
                data.episodes().forEach(e -> {
                    System.out.println("E" + e.numberEpisode() + " - " + 
                                       "Titulo: " + e.title() + " - " + 
                                       "Fecha de Estreno: " + e.releaseDate() + " - " + 
                                       "Evaluacion: " + e.evaluation()
                                        + "\n");
                });
                
            } else {
                System.out.println("El numero de la temporada ingresada no existe o no tiene datos.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un número entero para la temporada.");
            input.nextLine();
        }
    }
        
    
    public void example() {
        System.out.println("Ingresa el nombre de la serie que deseas buscar: ");
        
        //obtenemos el nombre modificado
        String updatedName = modifyNameSeason(input.nextLine());
        
        
        //buscamos dentro de la api mediante la url
        var json = consumoApi.getData(URL_BASE + updatedName + API_KEY);
        
        //guradamos los datos de la serie cobertidos de formato json a formato de clase java en nuestra variable datosSerie que es de tipo DatosSerie
        DatosSerie datosSerie = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datosSerie);
        
        //buscamos los datos de todas las temporadas
        //creamos una lista para guradar los datos de las temporadas y luego mostrarlas
//        List<DatosTemporada> seasons = new ArrayList<>();

        //con este for recorremos las temporadas y guardamos  sus datos en una variable para proceder a guardarlo en la lista previamente creada
        for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {

            //modificamos nuestra URL
            String urlForSeasons = URL_BASE + updatedName + "&Season=" + i + API_KEY;

            //pasamos nuestra nueva url a el json para proceder a la conversion mas tarde
            String jsonApi = consumoApi.getData(urlForSeasons);

            /*
                guradamos los datos obtrenidos del conversor previamente pasados de formato json a formato de clase java a una variable de
                tipo DatosTemporada
            */
            DatosTemporada datosTemporada = conversor.obtenerDatos(jsonApi, DatosTemporada.class);

            //añadimos en la lista los datos obtenidos
            seasons.add(datosTemporada);
        }

        //imprimimos los datos dentro de nuestra lista Seasons
        //seasons.forEach(System.out::println);
        
        //mostramos solo los titulos de los episodios por temporada
        //este for recorre toda la lista y pasa los episodios a una nueva lista
        /*for (int i = 0; i < datosSerie.totalTemporadas(); i++) {
            List<DatosEpisodio> episodesForSeasons = seasons.get(i).episodes();
            
            //usamos este for para poder mostrar la lista de episodios, itera la lista donde esta los episodios
            for (int j = 0; j < episodesForSeasons.size(); j++) {
                System.out.println(episodesForSeasons.get(j).title());
            }
        }*/
        
        //hacemos la logica anterior usando lambdas
        /*seasons.forEach(t -> t.episodes()
                                        .forEach(e -> System.out.println(e.title())
                                                                                   )
                                                                                    );*/
        
        //creamos una lista del tipo DatoEpisodio
//        showTop5BestEpisodes(seasons);
        /*List<DatosEpisodio> dataEpisode = seasons.stream()
                                                 .flatMap(t -> t.episodes().stream())
                                                 .collect(Collectors.toList());
        
        //hacemos el top 5 mejores episodios, se calculara dependiendo la evaluacion que recibio cada uno
        dataEpisode.stream()
                .filter(p -> !p.evaluation().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluation).reversed())
                .limit(5)
                .forEach(System.out::println);*/
        
//        System.out.println("-".repeat(100));
        
        //convirtiendo los datos a una lista del tipo Episode
//        List<Episode> episode = convertDataToEpisodeList(seasons);
        
//        imprimimos la lista
//        episode.forEach(System.out::println);
        /*List<Episode> episodeList = seasons.stream()
                .flatMap(t -> t.episodes().stream().map(d -> new Episode(t.number(), d)))
                .collect(Collectors.toList());
        
        episodeList.forEach(System.out::println);*/
        
//      Funcion para mostrar los episodios por el año ingresado por el usuario
//        showEpisodesFromTheYear(episode);

//      Funcion que permite bucar un episodio a partir de un titulo o parte de un titulo de espisodio ingresado
//        searchForTitle(episode);
        

//      Funcion para mostrar las evaluaciones de las temporadas
//        showStatisticsFoTheSeason(episode);
    }
    
    //este metodo hace que cuando el ususario ingrese un espacio entre las palabras se concatene con un "+" para evitar errores en la busqueda
    public String modifyNameSeason(String data) {
        var modifiedName = URLEncoder.encode(data, StandardCharsets.UTF_8);
        
        return modifiedName;
    }
    
//    metodo para mostrar los 5 episodios mejores calificados
    public void showTop5BestEpisodes(List<DatosTemporada> list) {
//        Creamos una lista del tipo DatosEpisodio
        List<DatosEpisodio> dataEpisode = list.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());
        
//        Logica para mostrar los 5 mejores episodios
        dataEpisode.stream()
                .filter(p -> !p.evaluation().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluation).reversed())
                .limit(5)
                .forEach(System.out::println);
    }
    
//    Metodo para convertir los datos de la lista de temporadas a una lista del tipo Episode
    public List<Episode> convertDataToEpisodeList(List<DatosTemporada> list) {
        List<Episode> episodeList = list.stream()
                .flatMap(t -> t.episodes().stream().map(d -> new Episode(t.number(), d)))
                .collect(Collectors.toList());
        
        return episodeList;
    }
    
//    Busqueda de episodios a partir de x año
    public void showEpisodesFromTheYear(List<Episode> list) {
        
        System.out.println("Ingresa el año desde el que deseas ver los episodios");
        int year = input.nextInt();
        input.nextLine();
        
//        creamos una variable para guardar los datos de la fercha
        LocalDate searchDate = LocalDate.of(year, 1, 1);
        
//        damos formato a la fecha para que se pueda visualisar mejor al momento de imprimir los datos
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        list.stream().filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getSeason() +"\n"
                        + "Episodio: " + e.getTitle() + "\n"
                        + "Fecha de lanzamiento: " + e.getReleaseDate() + "\n"
                        + "<->".repeat(40)
                    )
                );
    }
    
//    Busqueda de episodios dependiendo el nombre del titulo ingresado, puede ser una parte o puede ser el titulo completo,
//    Esta funcion garantiza devolver similitudes dependiendo lo ingresado por el usuario
    public void searchForTitle(List<Episode> list) {
        System.out.println("porfavor ingresa el nombre del episodio que deseas ver");
        String title = input.nextLine();
        
        Optional<Episode> searchEpisode =list.stream()
                                                .filter(e -> e.getTitle().toUpperCase().contains(title.toUpperCase()))
                                                .findFirst();
        
        if (searchEpisode.isPresent()) {
            System.out.println("Episodio Encontrado");
            System.out.println("Los datos del Episodio son: " + searchEpisode.get());
        } else {
            System.out.println("No se entro el episodio buscado");
        }
    }
    
//    Metodo que permite mostrar las estadisticas de las evaluaciones por temporada
    public void showStatisticsFoTheSeason(List<Episode> list) {
        Map<Integer, Double> statistic /*estasdistica*/ = list.stream()
                .filter(e -> e.getEvaluation() > 0.0) //filtramos las evaluaciones para que no se cuenten los valores menores a 0.0
                .collect(Collectors.groupingBy(Episode::getSeason, //para cada episodio se devuelve su temporada
                        Collectors.averagingDouble(Episode::getEvaluation))); //promediamos las evaluaciones por temporada
        
//        Mostramos el mapa
        System.out.println(statistic);
        
        DoubleSummaryStatistics std = list.stream()
                .filter(e -> e.getEvaluation()> 0.0)
                .collect(Collectors.summarizingDouble(Episode::getEvaluation));
        
        System.out.println("Media de las Evaluciones de la Serie: " + std.getAverage() + "\n"
                            + "Episodio Mejor Evaluado: " + std.getMax() + "\n"
                            + "Episodio Peor Evaluado: " + std. getMin());
    }
    
}
