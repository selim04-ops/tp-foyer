package tn.esprit.spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceImplTest {

    @Mock
    EtudiantRepository etudiantRepository ;

    @InjectMocks
    EtudiantServiceImpl etudiantService ;
    Etudiant etuduiant = new Etudiant(1,"roua","ounissi",1349,new Date(), new HashSet<>()) ;

    List<Etudiant> listEtufiants = new ArrayList<Etudiant>() {
        {
            add(new Etudiant(2,"rihem","saadouli",1234,new Date(), new HashSet<>()));
            add(new Etudiant(3,"amin","hamed",1234,new Date(), new HashSet<>()));
        }
    };

    @Test
    public void testRetrieveStudent() {
        Mockito.when(etudiantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(etuduiant));
        Etudiant etudiant1 = etudiantService.retrieveEtudiant(2L);
        assertNotNull(etudiant1);
    }

    @Test
    @Order(1)
    void testRetrieveAllStudents() {
        Mockito.when(etudiantRepository.findAll()).thenReturn(listEtufiants);
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();
        assertEquals(2, result.size());

    }

    @Test
    @Order(2)
    public void testAddStudent() {
        Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(etuduiant);
        etudiantService.addEtudiant(etuduiant);

        // Vérifiez que la méthode save a été appelée avec l'objet attendu
        Mockito.verify(etudiantRepository).save(etuduiant);

        // Simulez le comportement du repository après l'ajout d'un étudiant
        listEtufiants.add(etuduiant);
        Mockito.when(etudiantRepository.findAll()).thenReturn(listEtufiants);

        // Assurez-vous que l'étudiant ajouté existe bien dans le résultat final
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();
        Assertions.assertEquals(3, result.size());
    }


    @Test
    @Order(3)
    void testModifyStudent() {
        // Créer un étudiant avec des informations mises à jour
        Etudiant modifiedStudent = new Etudiant(1, "updatedPrenom", "ounissi", 1349, new Date(), new HashSet<>());

        // Mock la méthode save pour retourner l'étudiant modifié
        Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(modifiedStudent);

        // Appel du service pour modifier l’étudiant
        Etudiant updatedStudent = etudiantService.modifyEtudiant(modifiedStudent);

        // Vérification que l'étudiant n'est pas null
        assertNotNull(updatedStudent);

        // Vérification que les informations ont été mises à jour correctement
        assertEquals("updatedPrenom", updatedStudent.getNomEtudiant());

        // Vérifie que la méthode save a été appelée une fois avec l'étudiant modifié
        Mockito.verify(etudiantRepository, Mockito.times(1)).save(modifiedStudent);
    }











}
