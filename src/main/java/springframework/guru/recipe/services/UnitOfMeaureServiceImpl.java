package springframework.guru.recipe.services;

import org.springframework.stereotype.Service;
import springframework.guru.recipe.commands.UnitOfMeasureCommand;
import springframework.guru.recipe.converter.UnitOfMeasureObjectToCommand;
import springframework.guru.recipe.repositories.UnitOfMeasureRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeaureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureObjectToCommand unitOfMeasureObjectToCommand;

    public UnitOfMeaureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureObjectToCommand unitOfMeasureObjectToCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureObjectToCommand = unitOfMeasureObjectToCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureObjectToCommand::convert)
                .collect(Collectors.toSet());
    }
}
