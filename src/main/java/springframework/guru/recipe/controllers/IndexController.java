package springframework.guru.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springframework.guru.recipe.services.RecipeService;

@Slf4j
@Controller
public class IndexController {

    public final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("GET page request");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

}
