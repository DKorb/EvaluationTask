package com.dkorb.familyapp.family.mapper;

import com.dkorb.familyapp.family.Family;
import com.dkorb.familyapp.family.dto.FamilyDTO;
import com.dkorb.familyapp.family.dto.FamilyMemberDTO;

import java.util.List;

public class FamilyMapper {

    public static Family mapFamilyDTOToFamily(FamilyDTO familyDTO) {

        return Family.builder()
                .id(familyDTO.getId())
                .familyName(familyDTO.getFamilyName())
                .nrOfAdults(familyDTO.getNrOfAdults())
                .nrOfChildren(familyDTO.getNrOfChildren())
                .nrOfInfants(familyDTO.getNrOfInfants())
                .build();
    }

    public static FamilyDTO mapFamilyWithFamilyMembersToFamilyDTO(Family family, List<FamilyMemberDTO> familyMemberDTOList) {

        return FamilyDTO.builder()
                .id(family.getId())
                .familyName(family.getFamilyName())
                .nrOfAdults(family.getNrOfAdults())
                .nrOfChildren(family.getNrOfChildren())
                .nrOfInfants(family.getNrOfInfants())
                .familyMemberList(familyMemberDTOList)
                .build();

    }

}