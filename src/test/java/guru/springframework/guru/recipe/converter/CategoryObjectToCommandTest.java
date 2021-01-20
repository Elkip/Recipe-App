package guru.springframework.guru.recipe.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import guru.springframework.guru.recipe.commands.CategoryCommand;
import guru.springframework.guru.recipe.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryObjectToCommandTest {

    CategoryObjectToCommand converter;
    private static final Long ID_VALUE = 1L;
    private static final String RECIPE_CATEGORY = "Category";

    @BeforeEach
    void setUp() {
        converter = new CategoryObjectToCommand();
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(RECIPE_CATEGORY);

        CategoryCommand categoryCommand = converter.convert(category);

        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(RECIPE_CATEGORY, categoryCommand.getDescription());
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }
}
