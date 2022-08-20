package com.dkorb.familyapp.family;

import com.dkorb.familyapp.family.dto.FamilyDTO;
import com.dkorb.familyapp.family.dto.FamilyMemberDTO;
import com.dkorb.familyapp.family.exceptions.FamilyIncorrectDataException;
import com.dkorb.familyapp.family.exceptions.FamilyNotExistsException;
import com.dkorb.familyapp.family.exceptions.MemberIncorrectAgeException;
import com.dkorb.familyapp.family.mapper.FamilyMapper;
import com.dkorb.familyapp.family.utils.FamilyConfig;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FamilyService {

    private FamilyRepository familyRepository;

    private FamilyConfig familyConfig;

    /**
     * create new family and returns a FamilyDTO object that contains the family and family members with their id
     *
     * @param familyDTO input object provided by @RequestBody
     * @return FamilyDTO object which is passed to the ResponseEntity
     * with status 201 [CREATED] or status 400 [BAD_REQUEST]
     * @throws FamilyIncorrectDataException if the validation method fail return
     *                                      custom exception message with status 400 [BAD_REQUEST]
     */
    public FamilyDTO createNewFamily(FamilyDTO familyDTO) {

        if (validateFamilyData(familyDTO)) {

            Family savedFamily = familyRepository.save(mapFamilyDTOToFamily(familyDTO));
            List<FamilyMemberDTO> familyMembers = postFamilyMember(getFamilyMembers(familyDTO, savedFamily.getId()));

            return mapFamilyWithFamilyMembersToOutputDTO(savedFamily, familyMembers);

        } else {
            throw new FamilyIncorrectDataException();
        }
    }

    /**
     * create new family and returns a FamilyDTO object that contains the family and family members with their id
     *
     * @param familyId of the family with members he is searching for
     * @return FamilyDTO object which is passed to the ResponseEntity
     * with status 302 [FOUND] or status 404 [NOT_FOUND]
     * @throws FamilyNotExistsException take family id after which it was searched
     *                                  if family has not been found, throw custom exception message with status 404 [NOT_FOUND]
     */
    public FamilyDTO getFamilyById(int familyId) {

        try {

            Family family = familyRepository.findById(familyId).get();
            List<FamilyMemberDTO> familyMembersById = searchForFamilyMembers(familyId);

            return mapFamilyWithFamilyMembersToOutputDTO(family, familyMembersById);

        } catch (NoSuchElementException ex) {
            throw new FamilyNotExistsException(familyId);
        }

    }


    /**
     * *
     * validate the family data provided
     *
     * @param familyDTO input object provided by @RequestBody
     * @return true if statement is correct in the opposite case false
     * @throws MemberIncorrectAgeException if the age of family member is below 0
     *                                     throw custom exception with status 400 [BAD_REQUEST]
     */
    private boolean validateFamilyData(FamilyDTO familyDTO) {

        int nrOfInfants = 0;
        int nrOfAdults = 0;
        int nrOfChildren = 0;

        for (var member : familyDTO.getFamilyMemberList()) {

            int age = member.getAge();

            if (age < 0) {
                throw new MemberIncorrectAgeException(age);
            }
            if (age <= 3) {
                nrOfInfants++;
            } else if (age <= 15) {
                nrOfChildren++;
            } else nrOfAdults++;
        }

        return nrOfInfants == familyDTO.getNrOfInfants() &&
                nrOfChildren == familyDTO.getNrOfChildren() &&
                nrOfAdults == familyDTO.getNrOfAdults();
    }


    /**
     * *
     * create list of FamilyMemberDTO objects that are converted to JSON then
     * thanks to the RestTemplate create a POST request to createFamilyMember
     * controller in FamilyMemberApp module
     *
     * @param familyMemberList received family members objects from the getFamilyMembers method
     * @return list of FamilyMemberDTO objects
     */
    private List<FamilyMemberDTO> postFamilyMember(List<FamilyMemberDTO> familyMemberList) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        Gson gson = new Gson();

        String hostName = familyConfig.getHostName();
        int port = familyConfig.getPort();

        return familyMemberList.stream()
                .map(familyMember -> {

                    var requestJSON = gson.toJson(familyMember);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> request = new HttpEntity<>(requestJSON, httpHeaders);

                    ResponseEntity<FamilyMemberDTO> response = restTemplate.exchange(
                            "http://{hostName}:{port}/family-member",
                            HttpMethod.POST,
                            request,
                            FamilyMemberDTO.class,
                            hostName,
                            port);

                    return response.getBody();

                }).collect(Collectors.toList());

    }


    /**
     * *
     * create list of FamilyMemberDTO objects thanks to the RestTemplate
     * create GET request to createFamilyMember controller in FamilyMemberApp module
     *
     * @param familyId which I will get the list of family members
     * @return list of FamilyMemberDTO objects
     */
    private List<FamilyMemberDTO> searchForFamilyMembers(int familyId) {

        RestTemplate restTemplate = new RestTemplate();

        String hostName = familyConfig.getHostName();
        int port = familyConfig.getPort();

        ResponseEntity<FamilyMemberDTO[]> response = restTemplate.exchange(
                "http://{hostName}:{port}/family-member/{id}",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                FamilyMemberDTO[].class,
                hostName,
                port,
                familyId);

        return List.of(response.getBody());
    }


    /**
     * *
     * create new FamilyMemberDTO object with the id of the family then insert them into list
     *
     * @param familyDTO object from which I collect information on family members
     * @param familyId  to which the new objects will be assigned
     * @return list of FamilyMemberDTO objects
     */
    public List<FamilyMemberDTO> getFamilyMembers(FamilyDTO familyDTO, int familyId) {
        return familyDTO.getFamilyMemberList().stream()
                .map(familyMember ->
                        FamilyMemberDTO.builder()
                                .id(familyMember.getId())
                                .familyId(familyId)
                                .givenName(familyMember.getGivenName())
                                .familyName(familyMember.getFamilyName())
                                .age(familyMember.getAge())
                                .build())
                .toList();
    }


    /**
     * *
     * mapping from Family class object to FamilyDTO
     *
     * @param family              object of Family class
     * @param familyMemberDTOList list of FamilyMemberDTO objects
     * @return FamilyDTO object containing a list of family members
     */
    private FamilyDTO mapFamilyWithFamilyMembersToOutputDTO(Family family, List<FamilyMemberDTO> familyMemberDTOList) {
        return FamilyMapper.mapFamilyWithFamilyMembersToFamilyDTO(family, familyMemberDTOList);
    }

    /**
     * *
     * mapping from FamilyDTO class object to Family
     *
     * @param familyDTO object of FamilyDTO class
     * @return Family object
     */
    private Family mapFamilyDTOToFamily(FamilyDTO familyDTO) {
        return FamilyMapper.mapFamilyDTOToFamily(familyDTO);
    }
}