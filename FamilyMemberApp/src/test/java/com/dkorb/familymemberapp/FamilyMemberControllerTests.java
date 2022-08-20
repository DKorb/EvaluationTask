package com.dkorb.familymemberapp;


import com.dkorb.familymemberapp.family_member.FamilyMember;
import com.dkorb.familymemberapp.family_member.FamilyMemberService;
import com.dkorb.familymemberapp.family_member.dto.FamilyMemberDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
public class FamilyMemberControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FamilyMemberService familyMemberService;

    @Autowired
    private ObjectMapper objectMapper;

    private FamilyMemberDTO testFamilyMember;

    private FamilyMember familyMember;


    @BeforeEach
    public void setup() {

        testFamilyMember = FamilyMemberDTO.builder()
                .id(1)
                .givenName("test-givenName")
                .familyName("test-familyName")
                .age(22)
                .familyId(1)
                .build();

        familyMember = FamilyMember.builder()
                .id(1)
                .givenName("test-givenName")
                .familyName("test-familyName")
                .age(22)
                .familyId(1)
                .build();

    }

    @Test
    public void givenFamilyMemberDTOObject_whenCreateFamilyMember_thenReturnFamilyDTOObject() throws Exception {

        //given
        given(familyMemberService.createFamilyMember(ArgumentMatchers.any(FamilyMemberDTO.class)))
                .willAnswer((invocationOnMock) -> invocationOnMock.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/family-member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testFamilyMember)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.givenName", is(testFamilyMember.getGivenName())))
                .andExpect(jsonPath("$.familyName", is(testFamilyMember.getFamilyName())))
                .andExpect(jsonPath("$.age", is(testFamilyMember.getAge())))
                .andExpect(jsonPath("$.familyId", is(testFamilyMember.getFamilyId())));

    }

    @Test
    public void givenFamilyMemberId_whenSearchFamilyMember_thenReturnFamilyMemberObject() throws Exception {

        //given
        int familyMemberId = 1;
        List<FamilyMember> familyMembersList = new ArrayList<>();
        familyMembersList.add(familyMember);

        given(familyMemberService.searchFamilyMember(familyMemberId))
                .willReturn(familyMembersList);

        //when
        ResultActions response = mockMvc.perform(get("/family-member/{familyId}", familyMemberId));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(familyMembersList.size())));

    }

}