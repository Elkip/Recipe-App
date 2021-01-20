package guru.springframework.guru.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import guru.springframework.guru.recipe.commands.IngredientCommand;
import guru.springframework.guru.recipe.converter.IngredientCommandToObject;
import guru.springframework.guru.recipe.converter.IngredientObjectToCommand;
import guru.springframework.guru.recipe.domain.Ingredient;
import guru.springframework.guru.recipe.domain.Recipe;
import guru.springframework.guru.recipe.repositories.RecipeRepository;
import guru.springframework.guru.recipe.repositories.UnitOfMeasureRepository;

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
                Ingredient ingredient = commandToObject.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            // not a great solution
            if (savedIngredientOptional.isEmpty()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //todo check for fail
            return objectToCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteById(Long recipeId, Long ingredId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            log.debug("Found recipe");
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredId))
                    .findFirst();
            if (ingredientOptional.isPresent()) {
                log.debug("Found ingredient");
                Ingredient deleteIngredient = ingredientOptional.get();
                recipe.getIngredients().remove(deleteIngredient);
                deleteIngredient.setRecipe(null);
                recipeRepository.save(recipe);
            }
        } else {
            log.error("Ingredient ID Not Found");
        }
    }
}
