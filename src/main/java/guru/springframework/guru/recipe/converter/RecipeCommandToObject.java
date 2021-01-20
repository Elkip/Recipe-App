package guru.springframework.guru.recipe.converter;

import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import guru.springframework.guru.recipe.commands.RecipeCommand;
import guru.springframework.guru.recipe.domain.Recipe;

@Component
public class RecipeCommandToObject implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToObject notesConverter;
    private final CategoryCommandToObject categoryConverter;
    private final IngredientCommandToObject ingredientConverter;

    public RecipeCommandToObject(NotesCommandToObject notesConverter, CategoryCommandToObject categoryConverter, IngredientCommandToObject ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setDifficultly(source.getDifficulty());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() >= 0)
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));

        if (source.getIngredients() != null && source.getIngredients().size() >= 0)
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));

        return recipe;
    }
}
