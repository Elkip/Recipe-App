package springframework.guru.recipe.converter;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import springframework.guru.recipe.commands.IngredientCommand;
import springframework.guru.recipe.domain.Ingredient;

@Component
public class IngredientCommandToObject implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToObject converter;

    public IngredientCommandToObject(UnitOfMeasureCommandToObject converter) {
        this.converter = converter;
    }


    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null)
            return null;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setUom(converter.convert(ingredientCommand.getUom()));
        return ingredient;
    }
}
