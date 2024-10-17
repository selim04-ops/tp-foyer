package tn.esprit.tpfoyer.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idChambre;

    long numeroChambre;

    @Enumerated(EnumType.STRING)
    TypeChambre typeC;
//New comment added


    @OneToMany
    Set<Reservation> reservations;

    @ManyToOne(cascade = CascadeType.ALL)
    Bloc bloc;

    public Chambre(Long o, int numeroChambre, TypeChambre typeChambre, Object reservations, Object bloc) {

    }


    public long getIdChambre() {
        return idChambre;
    }
}
