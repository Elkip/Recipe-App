package springframework.guru.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springframework.guru.recipe.commands.RecipeCommand;
import springframework.guru.recipe.converter.RecipeCommandToObject;
import springframework.guru.recipe.converter.RecipeObjectoToCommand;
import springframework.guru.recipe.domain.Recipe;
import springframework.guru.recipe.exceptions.NotFoundException;
import springframework.guru.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeObjectoToCommand recipeObjectoToCommand;
    private final RecipeCommandToObject recipeCommandToObject;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeObjectoToCommand recipeObjectoToCommand,
                             RecipeCommandToObject recipeCommandToObject) {
        this.recipeRepository = recipeRepository;
        this.recipeObjectoToCommand = recipeObjectoToCommand;
        this.recipeCommandToObject = recipeCommandToObject;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Getting Recipes.,,");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isEmpty())
            throw new NotFoundException("Could not find recipe with id " + id.toString());


        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand findRecipeCommandById(Long id) {
        return recipeObjectoToCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        if (command == null)
            return null;
        Recipe detachedRecipe = recipeCommandToObject.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved Recipe ID: " + savedRecipe.getId());
        return recipeObjectoToCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
