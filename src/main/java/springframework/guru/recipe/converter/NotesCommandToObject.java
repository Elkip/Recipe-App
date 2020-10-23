package springframework.guru.recipe.converter;
import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import springframework.guru.recipe.commands.NotesCommand;
import springframework.guru.recipe.domain.Notes;

@Component
public class NotesCommandToObject implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if (notesCommand == null)
            return null;

        Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setNotes(notesCommand.getNotes());
        return notes;
    }
}
