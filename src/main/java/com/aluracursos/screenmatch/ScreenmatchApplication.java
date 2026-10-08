package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.Main.Main;
import com.aluracursos.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{

        @Autowired
        private SeriesRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

        @Override
        public void run(String... args) throws Exception {
            
            Main main = new Main(repository);
            main.showMenu();
                    
            
        }

}

           /* String url, keyApi, searchInApi, searchUpdate, jsonApi, episode, season;
            
            season = "1";
            episode = "1";
            keyApi = "85f4e24a";
            searchInApi = "Teen Wolf";
            
            searchUpdate = URLEncoder.encode(searchInApi, StandardCharsets.UTF_8);
            
            url = "https://www.omdbapi.com/?t=" + searchUpdate + "&apikey=" + keyApi;
            
            var consumoApi = new ConsumoAPI();
            
            jsonApi = consumoApi.getData(url);
            
            System.out.println(jsonApi);
            
            //convertimos los datos json a la clase
            ConvertirDatos conversor = new ConvertirDatos();
            
            var datos = conversor.obtenerDatos(jsonApi, DatosSerie.class);
            System.out.println(datos);
            
            String newUrl = "https://www.omdbapi.com/?t=" + searchUpdate + "&Season=" + season + "&episode=" + episode + "&apikey=" + keyApi;
            
            jsonApi = consumoApi.getData(newUrl);
            System.out.println(jsonApi);
            
            DatosEpisodio episodio = conversor.obtenerDatos(jsonApi, DatosEpisodio.class);
            
            System.out.println(episodio);
            
            //creamos una lista para guradar los datos de las temporadas y luego mostrarlas
            List<DatosTemporada> seasons = new ArrayList<>();
            
            //con este for recorremos las temporadas y guardamos  sus datos en una variable para proceder a guardarlo en la lista previamente creada
            for (int i = 1; i <= datos.totalTemporadas(); i++) {
                
                //modificamos nuestra URL
                String urlForSeasons = "https://www.omdbapi.com/?t=" + searchUpdate + "&Season=" + i + "&apikey=" + keyApi;
                
                //pasamos nuestra nueva url a el json para proceder a la conversion mas tarde
                jsonApi = consumoApi.getData(urlForSeasons);
                
                /*
                    guradamos los datos obtrenidos del conversor previamente pasados de formato json a formato de clase java a una variable de
                    tipo DatosTemporada
                */
               /* DatosTemporada datosTemporada = conversor.obtenerDatos(jsonApi, DatosTemporada.class);
                
                //añadimos en la lista los datos obtenidos
                seasons.add(datosTemporada);
            }*/
            
            //imprimimos los datos dentro de nuestra lista Seasons
            //seasons.forEach(System.out::println);