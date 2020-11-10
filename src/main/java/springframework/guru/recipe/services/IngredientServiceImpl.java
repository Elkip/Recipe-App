package springframework.guru.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springframework.guru.recipe.commands.IngredientCommand;
import springframework.guru.recipe.converter.IngredientCommandToObject;
import springframework.guru.recipe.converter.IngredientObjectToCommand;
import springframework.guru.recipe.domain.Ingredient;
import springframework.guru.recipe.domain.Recipe;
import springframework.guru.recipe.repositories.RecipeRepository;
import springframework.guru.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientObjectToCommand objectToCommand;
    private final IngredientCommandToObject commandToObject;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientObjectToCommand objectToCommand,
                                 IngredientCommandToObject commandToObject, RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.objectToCommand = objectToCommand;
        this.commandToObject = commandToObject;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (recipeOptional.isEmpty()) {
            log.error("Recipe not found for ID: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
            } else {
                Ingredient temp = commandToObject.convert(command);
                recipe.addIngredient(temp);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return objectToCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst().get());
        }
    }
}
