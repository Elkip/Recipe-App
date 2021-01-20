package guru.springframework.guru.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import guru.springframework.guru.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.guru.recipe.converter.UnitOfMeasureObjectToCommand;
import guru.springframework.guru.recipe.domain.UnitOfMeasure;
import guru.springframework.guru.recipe.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeaureServiceImplTest {

    UnitOfMeasureService service;
    UnitOfMeasureObjectToCommand objectToCommand;

    @Mock
    UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        objectToCommand = new UnitOfMeasureObjectToCommand();
        service = new UnitOfMeaureServiceImpl(repository, objectToCommand);

    }

    @Test
    void testListAllUoms() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        when(repository.findAll()).thenReturn(unitOfMeasures);

        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        assertEquals(2, commands.size());
        verify(repository, times(1)).findAll();
    }
}
