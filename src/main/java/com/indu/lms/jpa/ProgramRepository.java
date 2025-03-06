package com.indu.lms.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indu.lms.entity.TblLmsProgram;

@Repository

public interface ProgramRepository extends JpaRepository<TblLmsProgram, Integer> {

}
