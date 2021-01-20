package guru.springframework.guru.recipe.converter;

import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import guru.springframework.guru.recipe.commands.NotesCommand;
import guru.springframework.guru.recipe.domain.Notes;

@Component
public class NotesObjectToCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null)
            return  null;

        NotesCommand command = new NotesCommand();
        command.setId(source.getId());
        command.setNotes(source.getNotes());
        return command;
    }
}
