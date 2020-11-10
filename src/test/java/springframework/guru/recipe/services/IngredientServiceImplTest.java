package springframework.guru.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springframework.guru.recipe.commands.IngredientCommand;
import springframework.guru.recipe.converter.IngredientCommandToObject;
import springframework.guru.recipe.converter.IngredientObjectToCommand;
import springframework.guru.recipe.converter.UnitOfMeasureCommandToObject;
import springframework.guru.recipe.converter.UnitOfMeasureObjectToCommand;
import springframework.guru.recipe.domain.Ingredient;
import springframework.guru.recipe.domain.Recipe;
import springframework.guru.recipe.repositories.RecipeRepository;
import springframework.guru.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    IngredientObjectToCommand ingredientObjectToCommand;
    IngredientCommandToObject ingredientCommandToObject;
    IngredientService ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientObjectToCommand = new IngredientObjectToCommand(new UnitOfMeasureObjectToCommand());
        ingredientCommandToObject = new IngredientCommandToObject(new UnitOfMeasureCommandToObject());
        ingredientService = new IngredientServiceImpl(ingredientObjectToCommand, ingredientCommandToObject, recipeRepository, unitOfMeasureRepository);
    }

    @Test
    void testFindIngredientRecipeIdAndId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        recipe.addIngredient(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        recipe.addIngredient(ingredient2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findIngredientByRecipeIdAndId(1L, 3L);

        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveRecipeCommand() {
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();

        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}