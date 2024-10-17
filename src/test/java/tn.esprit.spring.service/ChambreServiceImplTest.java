package tn.esprit.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllChambres() {
        // Arrange
        List<Chambre> mockChambres = Arrays.asList(
                new Chambre(1L, 101, TypeChambre.SIMPLE, null, null),
                new Chambre(2L, 102, TypeChambre.DOUBLE, null, null)
        );
        when(chambreRepository.findAll()).thenReturn(mockChambres);

        // Act
        List<Chambre> result = chambreService.retrieveAllChambres();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository).findAll();
    }

    @Test
    void testAddChambre() {
        // Arrange
        Chambre newChambre = new Chambre(null, 103, TypeChambre.SIMPLE, null, null);
        Chambre savedChambre = new Chambre(3L, 103, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.save(newChambre)).thenReturn(savedChambre);

        // Act
        Chambre result = chambreService.addChambre(newChambre);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getIdChambre());  // Assuming idChambre is the getter for id
        verify(chambreRepository).save(newChambre);
    }

    @Test
    void testRetrieveChambre() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Act
        Chambre result = chambreService.retrieveChambre(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository).findById(1L);
    }

    @Test
    void testRemoveChambre() {
        // Arrange
        Long chambreId = 1L;

        // Act
        chambreService.removeChambre(chambreId);

        // Assert
        verify(chambreRepository).deleteById(chambreId);
    }

    @Test
    void testRecupererChambresSelonTyp() {
        // Arrange
        List<Chambre> mockChambres = Arrays.asList(
                new Chambre(1L, 101, TypeChambre.SIMPLE, null, null),
                new Chambre(2L, 102, TypeChambre.SIMPLE, null, null)
        );
        when(chambreRepository.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(mockChambres);

        // Act
        List<Chambre> result = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository).findAllByTypeC(TypeChambre.SIMPLE);
    }
}
