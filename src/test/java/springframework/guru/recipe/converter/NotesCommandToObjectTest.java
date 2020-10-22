package springframework.guru.recipe.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springframework.guru.recipe.commands.NotesCommand;
import springframework.guru.recipe.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToObjectTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";
    NotesCommandToObject notesConverter;

    @BeforeEach
    void setUp() {
        notesConverter = new NotesCommandToObject();
    }

    @Test
    void convert() {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setNotes(RECIPE_NOTES);

        Notes notes = notesConverter.convert(notesCommand);

        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getNotes());
    }

    @Test
    void testNull() {
        assertNull(notesConverter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(notesConverter.convert(new NotesCommand()));
    }
}