package com.dkorb.familyapp;

import com.dkorb.familyapp.family.Family;
import com.dkorb.familyapp.family.FamilyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class FamilyRepositoryTests {


    private static final String TEST_FAMILY_NAME = "test_familyName";

    @Autowired
    private FamilyRepository familyRepository;

    private Family family;

    @BeforeEach
    public void setup() {

        family = Family.builder()
                .id(1)
                .familyName(TEST_FAMILY_NAME)
                .nrOfAdults(1)
                .nrOfChildren(0)
                .nrOfInfants(0)
                .build();
    }


    @Test
    public void givenFamilyObject_whenSave_thenReturnSavedFamily() {

        //given
        Family savedFamily = familyRepository.save(family);

        //then
        assertThat(savedFamily).isNotNull();
        assertThat(savedFamily.getId()).isGreaterThan(0);
    }

    @Test
    public void givenFamilyList_whenFindAll_thenReturnFamilyList() {

        //given
        familyRepository.save(family);

        //when
        List<Family> familyList = familyRepository.findAll();

        //then
        assertThat(familyList).isNotNull();
        assertThat(familyList.size()).isEqualTo(1);
    }

    @Test
    public void givenFamilyObject_whenFindById_thenReturnFamilyObject() {

        //given
        familyRepository.save(family);

        //when
        Family familyById = familyRepository.findById(family.getId()).get();

        //then
        assertThat(familyById).isNotNull();
    }

    @Test
    public void givenFamilyObject_whenDeleteFamily_thenRemoveFamily() {

        //given
        familyRepository.save(family);

        //when
        familyRepository.delete(family);
        Optional<Family> familyById = familyRepository.findById(family.getId());

        //then
        assertThat(familyById).isEmpty();
    }

}