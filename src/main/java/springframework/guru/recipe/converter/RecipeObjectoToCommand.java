package springframework.guru.recipe.converter;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import springframework.guru.recipe.commands.RecipeCommand;
import springframework.guru.recipe.domain.Recipe;

@Component
public class RecipeObjectoToCommand implements Converter<Recipe, RecipeCommand> {
    private final NotesObjectToCommand notesConverter;
    private final CategoryObjectToCommand categoryConverter;
    private final IngredientObjectToCommand ingredientConverter;


    public RecipeObjectoToCommand(NotesObjectToCommand notesConverter, CategoryObjectToCommand categoryConverter, IngredientObjectToCommand ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null)
            return null;

        final RecipeCommand command = new RecipeCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        command.setPrepTime(source.getPrepTime());
        command.setCookTime(source.getCookTime());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setDifficulty(source.getDifficultly());
        command.setDirections(source.getDirections());
        command.setNotes(notesConverter.convert(source.getNotes()));
        command.setImage(source.getImage());

        if (source.getCategories() != null && source.getCategories().size() >= 0)
            source.getCategories()
                    .forEach(category -> command.getCategories().add(categoryConverter.convert(category)));

        if (source.getIngredients() != null && source.getIngredients().size() >= 0)
            source.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));

        return command;
    }
}
