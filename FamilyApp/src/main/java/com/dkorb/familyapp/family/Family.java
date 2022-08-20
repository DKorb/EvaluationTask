package com.dkorb.familyapp.family;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String familyName;

    @NotNull
    private int nrOfAdults;

    @NotNull
    private int nrOfChildren;

    @NotNull
    private int nrOfInfants;

}