package springframework.guru.recipe.converter;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import springframework.guru.recipe.commands.UnitOfMeasureCommand;
import springframework.guru.recipe.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureObjectToCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null)
            return null;

        final UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setDescription(unitOfMeasure.getDescription());
        uomCommand.setId(unitOfMeasure.getId());
        return uomCommand;
    }
}
