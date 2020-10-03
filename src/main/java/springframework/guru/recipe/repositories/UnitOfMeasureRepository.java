package springframework.guru.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.guru.recipe.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
