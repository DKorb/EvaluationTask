package com.dkorb.familyapp;


import com.dkorb.familyapp.family.FamilyService;
import com.dkorb.familyapp.family.dto.FamilyDTO;
import com.dkorb.familyapp.family.dto.FamilyMemberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
public class FamilyControllerTests {

    private static final String TEST_FAMILY_NAME = "test_familyName";

    private static final String TEST_GIVEN_NAME = "test_givenName";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FamilyService familyService;

    @Autowired
    private ObjectMapper objectMapper;

    private FamilyDTO testFamily;

    private FamilyMemberDTO testFamilyMember;


    @BeforeEach
    public void setup() {

        testFamilyMember = FamilyMemberDTO.builder()
                .id(1)
                .familyId(1)
                .familyName(TEST_FAMILY_NAME)
                .givenName(TEST_GIVEN_NAME)
                .age(23)
                .build();

        testFamily = FamilyDTO.builder()
                .id(1)
                .familyName(TEST_FAMILY_NAME)
                .nrOfAdults(1)
                .nrOfChildren(0)
                .nrOfInfants(0)
                .familyMemberList(List.of(testFamilyMember))
                .build();

    }


    @Test
    public void givenFamilyDTOObject_whenCreateFamily_thenReturnFamilyDTOObject() throws Exception {

        //given
        given(familyService.createNewFamily(ArgumentMatchers.any(FamilyDTO.class)))
                .willAnswer((invocationOnMock) -> invocationOnMock.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/family")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testFamily)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.familyName", is(testFamily.getFamilyName())))
                .andExpect(jsonPath("$.nrOfAdults", is(testFamily.getNrOfAdults())))
                .andExpect(jsonPath("$.nrOfChildren", is(testFamily.getNrOfChildren())))
                .andExpect(jsonPath("$.nrOfInfants", is(testFamily.getNrOfInfants())))
                .andExpect(jsonPath("$.familyMemberList", notNullValue()));

    }

    @Test
    public void givenFamilyId_whenGetFamilyById_thenReturnFamilyDTOObject() throws Exception {

        //given
        int familyId = testFamilyMember.getFamilyId();

        given(familyService.getFamilyById(familyId))
                .willReturn(testFamily);

        //when
        ResultActions response = mockMvc.perform(get("/family/{id}", familyId));

        //then
        response.andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.familyName", is(testFamily.getFamilyName())))
                .andExpect(jsonPath("$.nrOfAdults", is(testFamily.getNrOfAdults())))
                .andExpect(jsonPath("$.nrOfChildren", is(testFamily.getNrOfChildren())))
                .andExpect(jsonPath("$.nrOfInfants", is(testFamily.getNrOfInfants())))
                .andExpect(jsonPath("$.familyMemberList", notNullValue()));

    }

}