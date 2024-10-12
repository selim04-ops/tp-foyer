package tn.esprit.spring.services;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
//@TestMethodOrder(Cla)
@ExtendWith(MockitoExtension.class)
class UniversityServiceImplMock {
    @Mock
    private UniversiteRepository universiteRepository;
    @InjectMocks
    UniversiteServiceImpl universiteService;
    Universite universite3 = Universite.builder()
            .idUniversite(1)
            .foyer(null)
            .nomUniversite("ISSATM")
            .adresse("Mateur")
            .build();


    Universite u = new Universite(1, "ENSI", "Tunis", null);
    List<Universite> listUniversites = new ArrayList<Universite>() {
        {
            add(new Universite(2, "ENSI", "Tunis", null));
            add(new Universite(3, "ENSI", "Tunis", null));
            add(universite3);
        }
    };
    // Test for retrieving all universitys
    @Test
    @Order(1)
     void testRetrieveAllUniversites() {
        Mockito.when(universiteRepository.findAll()).thenReturn(listUniversites);
        List<Universite> result = universiteService.retrieveAllUniversites();
        assertEquals(3, result.size());
        Mockito.verify(universiteRepository, Mockito.times(1)).findAll();
    }
    // Test for retrieving a single university by id
    @Test
    @Order(2)
     void testRetrieveUniversite() {
        Mockito.when(universiteRepository.findById(u.getIdUniversite())).thenReturn(Optional.of(u));
        Universite result = universiteService.retrieveUniversite(u.getIdUniversite());
        assertEquals(u, result);
        Mockito.verify(universiteRepository, Mockito.times(4)).findById(u.getIdUniversite());
    }
    // Test for removing a university
    @Test
    @Order(4)
     void testRemoveUniversite() {
        Long universiteId = 1L;
        Mockito.doNothing().when(universiteRepository).deleteById(universiteId);
        universiteService.removeUniversite(universiteId);
        Mockito.verify(universiteRepository, Mockito.times(4)).deleteById(universiteId);
    }
}