package com.dkorb.familymemberapp;

import com.dkorb.familymemberapp.family_member.FamilyMember;
import com.dkorb.familymemberapp.family_member.FamilyMemberRepository;
import com.dkorb.familymemberapp.family_member.FamilyMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FamilyMemberServiceTests {

    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @InjectMocks
    private FamilyMemberService familyMemberService;

    private FamilyMember familyMember;


    @BeforeEach
    public void setup() {

        familyMember = FamilyMember.builder()
                .id(1)
                .givenName("test-givenName")
                .familyName("test-familyName")
                .age(22)
                .familyId(1)
                .build();

    }

    @Test
    public void givenFamilyMemberId_whenGetAllByFamilyId_thenReturnFamilyMemberList() {

        //given
        given(familyMemberRepository.findAllByFamilyId(familyMember.getId()))
                .willReturn(List.of(familyMember));

        //when
        List<FamilyMember> familyMembers = familyMemberService.searchFamilyMember(familyMember.getId());

        //then
        assertThat(familyMembers).isNotNull();
        assertThat(familyMembers.size()).isEqualTo(1);
        assertThat(familyMembers).contains(familyMember);

    }


}