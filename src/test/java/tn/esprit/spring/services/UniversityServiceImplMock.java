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
class TestUniversityServiceImplMock {
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
       Mockito.when(universiteRepository.findById(universite3.getIdUniversite())).thenReturn(Optional.of(universite3));
       Universite result = universiteService.retrieveUniversite(universite3.getIdUniversite());
       assertEquals(universite3, result);
       Mockito.verify(universiteRepository, Mockito.times(1)).findById(universite3.getIdUniversite());
   }

    @Test
    @Order(3)
    void testmodifyUniversity() {
        Universite universityToUpdate = listUniversites.get(0); // Assume we modify the first student
        universityToUpdate.setAdresse("UpdatedAddress");

        Mockito.when(universiteRepository.save(universityToUpdate)).thenReturn(universityToUpdate);

        Universite updatedUniversity = universiteService.modifyUniversite(universityToUpdate);

        assertNotNull(updatedUniversity);
        assertEquals("UpdatedAddress", updatedUniversity.getAdresse());
        Mockito.verify(universiteRepository, Mockito.times(1)).save(universityToUpdate);
    }
    // Test for removing a university
    @Test
    @Order(4)
    void testRemoveNonExistentUniversite() {
        Long universityIdToRemove = listUniversites.get(0).getIdUniversite();
        universiteService.removeUniversite(universityIdToRemove);
        Mockito.verify(universiteRepository, Mockito.times(1)).deleteById(universityIdToRemove);
    }
    @Test
    @Order(5)
    void testAddUniveristy() {
        Universite newUniveristy = Universite.builder()
                .idUniversite(4L)
                .foyer(null)
                .nomUniversite("ESB")
                .adresse("ARiana")
                .build();
        Mockito.when(universiteRepository.save(newUniveristy)).thenReturn(newUniveristy);

        Universite addedUniveristy = universiteService.addUniversite(newUniveristy);

        assertNotNull(addedUniveristy);
        assertEquals(newUniveristy.getNomUniversite(), addedUniveristy.getNomUniversite());
        Mockito.verify(universiteRepository, Mockito.times(1)).save(newUniveristy);


    }
}