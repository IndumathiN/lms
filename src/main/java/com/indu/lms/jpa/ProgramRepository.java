package com.indu.lms.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indu.lms.entity.TblLmsBatch;
import com.indu.lms.entity.TblLmsProgram;

@Repository

public interface ProgramRepository extends JpaRepository<TblLmsProgram, Integer> {

	//boolean existsByProgramName(String program_name);
	@Query(value = "SELECT * FROM tbl_lms_program  where program_name = :programName", nativeQuery = true)
	Optional<TblLmsProgram> findByProgramName(@Param("programName") String name);
	//boolean findByProgramName(String program_name);

}
