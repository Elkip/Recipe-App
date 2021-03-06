package springframework.guru.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springframework.guru.recipe.commands.IngredientCommand;
import springframework.guru.recipe.commands.RecipeCommand;
import springframework.guru.recipe.commands.UnitOfMeasureCommand;
import springframework.guru.recipe.services.IngredientService;
import springframework.guru.recipe.services.RecipeService;
import springframework.guru.recipe.services.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredient")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingId, Model model) {
        log.debug("Getting ingredient");

        model.addAttribute("ingredient", ingredientService.findIngredientByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(ingId)));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingId, Model model) {

        model.addAttribute("ingredient", ingredientService.findIngredientByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(ingId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("Saved Recipe ID: " + savedCommand.getRecipeId());
        log.debug("Saved Ingredient ID: " + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        model.addAttribute("ingredient", ingredientCommand);

        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingId) {
        log.debug("Deleting ingredient id: " + ingId);
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingId));
        return "redirect:/recipe/" + recipeId + "/ingredient";
    }

}
