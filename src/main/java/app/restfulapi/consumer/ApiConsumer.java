package app.restfulapi.consumer;

import app.restfulapi.dto.PokemonDTO;
import app.restfulapi.entity.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.restfulapi.repositories.PokemonRepo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Service
public class ApiConsumer {
    private PokemonRepo pokemonRepo;
    @Autowired
    public ApiConsumer(PokemonRepo pokemonRepo) {
        this.pokemonRepo = pokemonRepo;
    }
    String apiUrl = "https://pokeapi.co/api/v2/pokemon?count=1302";

    public void consumeApi(){
        try{
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            JsonNode resultsNode = getJsonNode(connection);

            for (JsonNode pokemonNode : resultsNode) {
                String name = pokemonNode.get("name").asText();
                String urlPokemon = pokemonNode.get("url").asText();
                pokemonRepo.save(new Pokemon(0,name, urlPokemon));//todo wtf this is wrong
            }
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode getJsonNode(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.toString());
        return rootNode.get("results");
    }
}
