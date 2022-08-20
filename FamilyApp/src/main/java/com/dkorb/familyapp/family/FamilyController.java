package com.dkorb.familyapp.family;

import com.dkorb.familyapp.family.dto.FamilyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/family")
@Api(value = "REST-API for family")
public class FamilyController {

    private FamilyService familyService;

    @ApiOperation(value = "Create new family")
    @PostMapping
    public ResponseEntity<FamilyDTO> createFamily(@Valid @RequestBody FamilyDTO familyDTO){
        return new ResponseEntity<>(familyService.createNewFamily(familyDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get family")
    @GetMapping("{familyId}")
    public ResponseEntity<FamilyDTO> getFamily(@PathVariable("familyId") int familyId) {
        return new ResponseEntity<>(familyService.getFamilyById(familyId), HttpStatus.FOUND);
    }

}