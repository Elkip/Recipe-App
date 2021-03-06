package springframework.guru.recipe.converter;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import springframework.guru.recipe.commands.IngredientCommand;
import springframework.guru.recipe.domain.Ingredient;

@Component
public class IngredientObjectToCommand implements Converter<Ingredient, IngredientCommand> {

    UnitOfMeasureObjectToCommand converter;

    public IngredientObjectToCommand(UnitOfMeasureObjectToCommand command) {
        this.converter = command;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null)
            return null;

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        if (ingredient.getRecipe() != null)
            ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setUom(converter.convert(ingredient.getUom()));
        return ingredientCommand;
    }
}
