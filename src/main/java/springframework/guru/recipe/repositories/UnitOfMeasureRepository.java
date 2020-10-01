package springframework.guru.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.guru.recipe.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}
