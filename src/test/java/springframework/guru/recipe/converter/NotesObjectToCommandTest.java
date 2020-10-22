package springframework.guru.recipe.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springframework.guru.recipe.commands.NotesCommand;
import springframework.guru.recipe.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesObjectToCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";
    NotesObjectToCommand notesConverter;

    @BeforeEach
    void setUp() {
        notesConverter = new NotesObjectToCommand();
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setNotes(RECIPE_NOTES);

        NotesCommand notesCommand = notesConverter.convert(notes);

        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getNotes());
    }

    @Test
    void testNull() {
        assertNull(notesConverter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(notesConverter.convert(new Notes()));
    }
}