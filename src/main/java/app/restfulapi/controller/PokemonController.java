package controller;

import consumer.ApiConsumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.PokemonRepo;

@RestController
public class PokemonController {
    PokemonRepo pokemonRepo;
    @GetMapping("pokemon")
    public String getPokemon() {
        System.out.println(pokemonRepo.findByName("pikachu").get().getUrlPokemon());
        return pokemonRepo.findByName("pikachu").get().getUrlPokemon();
    }
}
