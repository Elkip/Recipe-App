package guru.springframework.guru.recipe.services;

import guru.springframework.guru.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findIngredientByRecipeIdAndId(Long recipeId, Long ingId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(Long recipeId, Long ingredId);
}
