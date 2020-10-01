package springframework.guru.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.guru.recipe.domain.Category;

public interface CategoryRepository extends CrudRepository <Category, Long> {


}
