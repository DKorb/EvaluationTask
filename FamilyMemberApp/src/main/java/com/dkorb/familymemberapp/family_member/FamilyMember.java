package com.dkorb.familymemberapp.family_member;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "familymember")
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String givenName;

    @NotNull
    private String familyName;

    @NotNull
    private int age;

    @NotNull
    private int familyId;

}