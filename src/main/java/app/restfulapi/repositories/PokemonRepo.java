package repositories;

import consumer.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepo extends JpaRepository<Pokemon, Integer> {
    Optional<Pokemon> findByName(String name);
}
