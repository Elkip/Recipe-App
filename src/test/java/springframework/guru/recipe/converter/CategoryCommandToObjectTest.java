package springframework.guru.recipe.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springframework.guru.recipe.commands.CategoryCommand;
import springframework.guru.recipe.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToObjectTest {

    CategoryCommandToObject converter;
    private static final Long ID_VALUE = 1L;
    private static final String RECIPE_CATEGORY = "Category";

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToObject();
    }

    @Test
    void convert() {
        CategoryCommand command = new CategoryCommand();
        command.setId(ID_VALUE);
        command.setDescription(RECIPE_CATEGORY);

        Category category = converter.convert(command);

        assertEquals(ID_VALUE, category.getId());
        assertEquals(RECIPE_CATEGORY, category.getDescription());
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }
}