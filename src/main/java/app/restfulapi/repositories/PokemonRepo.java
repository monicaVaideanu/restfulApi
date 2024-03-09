package app.restfulapi.repositories;

import app.restfulapi.consumer.PokemonC;
import app.restfulapi.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PokemonRepo extends JpaRepository<Pokemon, Integer> {

    Optional<Pokemon> findByName(String name);
}
