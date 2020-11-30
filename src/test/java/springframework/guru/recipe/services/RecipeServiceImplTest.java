package springframework.guru.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springframework.guru.recipe.commands.RecipeCommand;
import springframework.guru.recipe.converter.RecipeCommandToObject;
import springframework.guru.recipe.converter.RecipeObjectoToCommand;
import springframework.guru.recipe.domain.Recipe;
import springframework.guru.recipe.exceptions.NotFoundException;
import springframework.guru.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeObjectoToCommand recipeObjectoToCommand;

    @Mock
    RecipeCommandToObject recipeCommandToObject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeObjectoToCommand, recipeCommandToObject);
    }

    @Test
    void getRecipesTest() {

        Recipe recipe = new Recipe();
        HashSet recipiesData = new HashSet();
        recipiesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipiesData);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeCommandByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeObjectoToCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findRecipeCommandById(1L);

        assertNotNull(commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }


    @Test
    void getRecipesByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipeByIdNotFoundTest() {
        Optional<Recipe> optionalRecipe = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        assertThrows(NotFoundException.class, () -> {
            Recipe recipe = recipeService.findById(1L);
        });
    }


    @Test
    void deleteByIdTest() {
        Long idToDelete = Long.valueOf(2L);
        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}