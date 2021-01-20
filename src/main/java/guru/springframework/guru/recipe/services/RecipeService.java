package guru.springframework.guru.recipe.services;

import guru.springframework.guru.recipe.commands.RecipeCommand;
import guru.springframework.guru.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand findRecipeCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(Long id);
}
