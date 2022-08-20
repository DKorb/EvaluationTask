package com.dkorb.familymemberapp.family_member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "Family member model information")
public class FamilyMemberDTO {

    private int id;

    @ApiModelProperty(value = "first name")
    @Size(min = 3, message = "Given name should have at least 3 characters.")
    private String givenName;

    @ApiModelProperty(value = "family name")
    @Size(min = 3, message = "Family name should have at least 3 characters.")
    private String familyName;

    @NotEmpty(message = "Age of the family member must be entered.")
    private int age;

    private int familyId;

}