package com.dkorb.familymemberapp;

import com.dkorb.familymemberapp.family_member.FamilyMember;
import com.dkorb.familymemberapp.family_member.FamilyMemberRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FamilyMemberRepositoryTests {


    private static final String TEST_FAMILY_NAME = "test_familyName";


    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    private FamilyMember familyMember;

    @BeforeEach
    public void setup() {

        familyMember = FamilyMember.builder()
                .id(1)
                .givenName("test-givenName")
                .familyName(TEST_FAMILY_NAME)
                .age(22)
                .familyId(1)
                .build();

    }

    @Test
    public void givenFamilyId_whenFindAllByFamilyId_thenReturnFamilyMemberList() {

        //given
        int familyMemberId = 1;

        //when
        List<FamilyMember> allByFamilyId = familyMemberRepository.findAllByFamilyId(familyMemberId);

        //then
        assertThat(allByFamilyId).isNotNull();
        assertThat(allByFamilyId).size().isEqualTo(7);
    }


    @Test
    public void givenFamilyMemberObject_whenSave_thenReturnSavedFamilyMember() {

        //given
        FamilyMember savedFamily = familyMemberRepository.save(familyMember);

        //then
        assertThat(savedFamily).isNotNull();
        assertThat(savedFamily.getId()).isGreaterThan(0);
    }

    @Test
    public void givenFamilyMemberList_whenFindAll_thenReturnFamilyMemberList() {

        //given
        familyMemberRepository.save(familyMember);

        //when
        List<FamilyMember> familyList = familyMemberRepository.findAll();

        //then
        assertThat(familyList).isNotNull();
        assertThat(familyList.size()).isEqualTo(7);
    }

    @Test
    public void givenFamilyMemberObject_whenFindById_thenReturnFamilyMemberObject() {

        //given
        familyMemberRepository.save(familyMember);

        //when
        FamilyMember familyById = familyMemberRepository.findById(familyMember.getId()).get();

        //then
        assertThat(familyById).isNotNull();
    }

    @Test
    public void givenFamilyMemberObject_whenDeleteFamilyMember_thenRemoveFamilyMember() {

        //given
        familyMemberRepository.save(familyMember);

        //when
        familyMemberRepository.delete(familyMember);
        Optional<FamilyMember> familyById = familyMemberRepository.findById(familyMember.getId());

        //then
        assertThat(familyById).isEmpty();
    }

}