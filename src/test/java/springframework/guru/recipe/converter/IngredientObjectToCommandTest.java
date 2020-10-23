package springframework.guru.recipe.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import springframework.guru.recipe.commands.IngredientCommand;
import springframework.guru.recipe.domain.Ingredient;
import springframework.guru.recipe.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientObjectToCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "Description";
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(10000.0000);
    public static final Long UOM_ID = 1L;
    IngredientObjectToCommand converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientObjectToCommand();
    }

    @Test
    void convert() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        ingredient.setUom(uom);

        IngredientCommand command = converter.convert(ingredient);

        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(AMOUNT, command.getAmount());
        assertNotNull(command.getUnitOfMeasure());
        assertEquals(UOM_ID, command.getUnitOfMeasure().getId());
    }

    @Test
    void convertNoUOM() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setUom(null);

        IngredientCommand command = converter.convert(ingredient);

        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(AMOUNT, command.getAmount());
        assertNull(command.getUnitOfMeasure());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty() {
        assertNotNull(converter.convert(new Ingredient()));
    }
}