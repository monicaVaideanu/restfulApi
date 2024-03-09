package app.restfulapi.controller;
import app.restfulapi.consumer.PokemonC;
import app.restfulapi.entity.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.restfulapi.repositories.PokemonRepo;

@RestController
@RequestMapping(path = "/pokemon")
public class PokemonController {
    @Autowired
    PokemonRepo pokemonRepo;
    @GetMapping("id/{id}")
    public String getPokemonById(@PathVariable int id) {
        System.out.println("here");
        return pokemonRepo.findById(id).get().toString();
    }
    @GetMapping("/name/{name}")
    public String getPokemonByName(@PathVariable String name) {
        return pokemonRepo.findByName(name).get().toString();
    }
    @PostMapping("/add")
    public String addPokemon(@RequestBody PokemonC pokemonC) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonC.getName());
        pokemon.setUrl(pokemonC.getUrlPokemon());
        pokemonRepo.save(pokemon);
        System.out.println(pokemonC.getUrlPokemon());
        System.out.println(pokemon.getUrl());
        return pokemon.toString();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePokemon(@PathVariable int id, @RequestBody PokemonC pokemonC) {
        if (pokemonRepo.existsById(id)) {
            Pokemon pokemon = pokemonRepo.findById(id).get();
            pokemon.setName(pokemonC.getName());
            pokemon.setUrl(pokemonC.getUrlPokemon());
            pokemonRepo.save(pokemon);
            return ResponseEntity.ok().body(pokemon.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable int id) {
        if (pokemonRepo.existsById(id)) {
            pokemonRepo.deleteById(id);
            return ResponseEntity.ok().body("Pokemon with id " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
