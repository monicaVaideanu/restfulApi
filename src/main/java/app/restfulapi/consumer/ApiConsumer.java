package consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import repositories.PokemonRepo;
import java.io.BufferedReader;
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
    List<Pokemon> pokemonList = new ArrayList<>();

    public void consumeApi(){
        try{
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());
            JsonNode resultsNode = rootNode.get("results");

            for (JsonNode pokemonNode : resultsNode) {
                String name = pokemonNode.get("name").asText();
                String urlPokemon = pokemonNode.get("url").asText();
                pokemonList.add(new Pokemon(name, urlPokemon));
                pokemonRepo.save(new Pokemon(name, urlPokemon));
            }

            for (Pokemon pokemon : pokemonList) {
                System.out.println("Name: " + pokemon.getName() + ", URL: " + pokemon.getUrlPokemon());
            }

            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
