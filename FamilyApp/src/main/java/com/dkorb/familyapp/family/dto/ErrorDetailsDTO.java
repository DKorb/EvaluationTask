package com.dkorb.familyapp.family.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Model of the error details")
public class ErrorDetailsDTO {

    @ApiModelProperty(value = "Date the error occurred")
    private Date timestamp;

    @ApiModelProperty(value = "Error message")
    private String message;

    @ApiModelProperty(value = "Details of the error")
    private String details;

    @ApiModelProperty(value = "Error code")
    private Integer errorCode;

}