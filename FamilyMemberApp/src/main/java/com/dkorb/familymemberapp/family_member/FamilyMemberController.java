package com.dkorb.familymemberapp.family_member;

import com.dkorb.familymemberapp.family_member.dto.FamilyMemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/family-member")
@Api(value = "REST-API for family member")
public class FamilyMemberController {

    private FamilyMemberService familyMemberService;

    @ApiOperation(value = "Create new family member")
    @PostMapping
    public ResponseEntity<FamilyMemberDTO> createFamilyMember(@RequestBody FamilyMemberDTO familyMemberDTO) {
        return new ResponseEntity<>(familyMemberService.createFamilyMember(familyMemberDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get family members")
    @GetMapping("{familyId}")
    public ResponseEntity<List<FamilyMember>> searchFamilyMember(@PathVariable("familyId") Integer familyId){
        return new ResponseEntity<>(familyMemberService.searchFamilyMember(familyId), HttpStatus.OK);
    }

}
