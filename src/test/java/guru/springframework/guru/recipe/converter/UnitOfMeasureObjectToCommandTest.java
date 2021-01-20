package guru.springframework.guru.recipe.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import guru.springframework.guru.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.guru.recipe.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureObjectToCommandTest {


    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureObjectToCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureObjectToCommand();
    }


    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        UnitOfMeasureCommand command = converter.convert(uom);

        assertNotNull(command);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }

}
