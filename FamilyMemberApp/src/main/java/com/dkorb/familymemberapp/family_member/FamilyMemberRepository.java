package com.dkorb.familymemberapp.family_member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Integer> {

    List<FamilyMember> findAllByFamilyId(int familyId);

}