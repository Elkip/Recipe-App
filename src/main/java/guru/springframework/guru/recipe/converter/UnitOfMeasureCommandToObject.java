package guru.springframework.guru.recipe.converter;

import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import guru.springframework.guru.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.guru.recipe.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureCommandToObject implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null)
            return null;

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
    }

}
