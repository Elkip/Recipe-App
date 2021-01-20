package guru.springframework.guru.recipe.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import guru.springframework.guru.recipe.commands.RecipeCommand;
import guru.springframework.guru.recipe.converter.RecipeCommandToObject;
import guru.springframework.guru.recipe.converter.RecipeObjectoToCommand;
import guru.springframework.guru.recipe.domain.Recipe;
import guru.springframework.guru.recipe.repositories.RecipeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeServiceIT {

    static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToObject recipeCommandToObject;

    @Autowired
    RecipeObjectoToCommand recipeObjectoToCommand;

    @Transactional
    @Test
    void testSaveOfDescription() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeObjectoToCommand.convert(testRecipe);

        assert testRecipeCommand != null;
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
