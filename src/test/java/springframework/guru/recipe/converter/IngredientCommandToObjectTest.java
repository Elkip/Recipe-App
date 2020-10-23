package springframework.guru.recipe.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springframework.guru.recipe.commands.IngredientCommand;
import springframework.guru.recipe.commands.UnitOfMeasureCommand;
import springframework.guru.recipe.domain.Ingredient;
import springframework.guru.recipe.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToObjectTest {


    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "Description";
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(10000.0000);
    public static final Long UOM_ID = 1L;
    IngredientCommandToObject converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToObject();
    }

    @Test
    void convert() {
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);
        command.setAmount(AMOUNT);

        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(UOM_ID);
        command.setUnitOfMeasure(uomCommand);

        Ingredient ingredient = converter.convert(command);

        assertNotNull(ingredient);
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertNotNull(ingredient.getUom());
        assertEquals(UOM_ID, ingredient.getUom().getId());
    }

    @Test
    void convertNoUOM() {
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);
        command.setAmount(AMOUNT);
        command.setUnitOfMeasure(null);

        Ingredient ingredient = converter.convert(command);

        assertNotNull(ingredient);
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertNull(ingredient.getUom());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }
}