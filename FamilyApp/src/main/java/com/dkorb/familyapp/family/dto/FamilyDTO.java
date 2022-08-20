package com.dkorb.familyapp.family.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Family model information")
public class FamilyDTO {

    private int id;

    @ApiModelProperty(value = "family name")
    @Size(min = 3, message = "Family name should have at least 3 characters.")
    private String familyName;

    @ApiModelProperty(value = "number of adults in the family")
    private int nrOfAdults;

    @ApiModelProperty(value = "number of children in the family")
    private int nrOfChildren;

    @ApiModelProperty(value = "number of infants in the family")
    private int nrOfInfants;

    @ApiModelProperty(value = "list of family members")
    @NotEmpty(message = "List of family members should include at least 1 member.")
    private List<FamilyMemberDTO> familyMemberList;

}