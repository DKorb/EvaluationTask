package com.dkorb.familymemberapp.family_member;

import com.dkorb.familymemberapp.family_member.dto.FamilyMemberDTO;
import com.dkorb.familymemberapp.family_member.exceptions.MemberIncorrectAgeException;
import com.dkorb.familymemberapp.family_member.mapper.FamilyMemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FamilyMemberService {

    private FamilyMemberRepository familyMemberRepository;


    /**
     * Creates a new family member and returns a FamilyMemberDTO object
     *
     * @param familyMemberDTO input object provided by @RequestBody
     * @return FamilyMemberDTO object which is passed to the ResponseEntity
     * with status 201 [CREATED] or status 400 [BAD_REQUEST]
     * @throws MemberIncorrectAgeException if the age of family member is below 0
     *                                     throw custom exception with status 400 [BAD_REQUEST]
     */
    public FamilyMemberDTO createFamilyMember(FamilyMemberDTO familyMemberDTO) {

        int familyMemberAge = familyMemberDTO.getAge();

        if (familyMemberAge < 0) {
            throw new MemberIncorrectAgeException(familyMemberAge);
        }

        FamilyMember newFamilyMember = familyMemberRepository.save(mapFamilyMemberDTOToFamilyMember(familyMemberDTO));

        return mapFamilyMemberToFamilyMemberDTO(newFamilyMember);
    }


    /**
     * Creates a new family member and returns a FamilyMemberDTO object
     *
     * @param familyId of family, he is supposed to be looking for
     * @return list of FamilyMember objects which is passed to the ResponseEntity
     * with status 200 [OK]
     */
    public List<FamilyMember> searchFamilyMember(int familyId) {
        return familyMemberRepository.findAllByFamilyId(familyId);
    }


    /**
     * *
     * mapping from FamilyMember class object to FamilyMemberDTO
     *
     * @param familyMember object of FamilyMember class
     * @return FamilyMemberDTO object
     */
    private FamilyMemberDTO mapFamilyMemberToFamilyMemberDTO(FamilyMember familyMember) {
        return FamilyMemberMapper.mapFamilyMemberToFamilyMemberDTO(familyMember);
    }


    /**
     * *
     * mapping from FamilyMemberDTO class object to FamilyMember
     *
     * @param familyMemberDTO object of FamilyMemberDTO class
     * @return FamilyMember object
     */
    private FamilyMember mapFamilyMemberDTOToFamilyMember(FamilyMemberDTO familyMemberDTO) {
        return FamilyMemberMapper.mapFamilyMemberDTOToFamilyMember(familyMemberDTO);
    }

}