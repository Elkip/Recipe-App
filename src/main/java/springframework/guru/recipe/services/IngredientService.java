package springframework.guru.recipe.services;

import springframework.guru.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findIngredientByRecipeIdAndId(Long recipeId, Long ingId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);
}
