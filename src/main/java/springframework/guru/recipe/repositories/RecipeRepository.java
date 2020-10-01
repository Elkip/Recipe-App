package springframework.guru.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.guru.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
