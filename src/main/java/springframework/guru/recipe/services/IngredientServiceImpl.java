package springframework.guru.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springframework.guru.recipe.commands.IngredientCommand;
import springframework.guru.recipe.commands.RecipeCommand;
import springframework.guru.recipe.converter.IngredientObjectToCommand;
import springframework.guru.recipe.domain.Ingredient;
import springframework.guru.recipe.domain.Recipe;
import springframework.guru.recipe.repositories.RecipeRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientObjectToCommand objectToCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientObjectToCommand objectToCommand, RecipeRepository recipeRepository) {
        this.objectToCommand = objectToCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findIngredientByRecipeIdAndId(Long recipeId, Long ingId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty())
            log.error("Recipe id not found Id: " + recipeId);

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingId))
                .map(ingredient ->  objectToCommand.convert(ingredient)).findFirst();

        if (ingredientCommandOptional.isEmpty())
            log.error("Ingredient id not found");

        return ingredientCommandOptional.get();
    }
}
