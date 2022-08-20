package com.dkorb.familymemberapp.family_member.mapper;

import com.dkorb.familymemberapp.family_member.dto.FamilyMemberDTO;
import com.dkorb.familymemberapp.family_member.FamilyMember;

public class FamilyMemberMapper {

    public static FamilyMemberDTO mapFamilyMemberToFamilyMemberDTO(FamilyMember familyMember) {

        return FamilyMemberDTO.builder()
                .id(familyMember.getId())
                .givenName(familyMember.getGivenName())
                .familyName(familyMember.getFamilyName())
                .age(familyMember.getAge())
                .familyId(familyMember.getFamilyId())
                .build();
    }

    public static FamilyMember mapFamilyMemberDTOToFamilyMember(FamilyMemberDTO familyMemberDTO) {

        return FamilyMember.builder()
                .id(familyMemberDTO.getId())
                .givenName(familyMemberDTO.getGivenName())
                .familyName(familyMemberDTO.getFamilyName())
                .age(familyMemberDTO.getAge())
                .familyId(familyMemberDTO.getFamilyId())
                .build();
    }

}