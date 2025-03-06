package com.indu.lms.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indu.lms.entity.TblLmsBatchInfo;
import com.indu.lms.entity.TblLmsProgram;
import com.indu.lms.entity.TblLmsProgramInfo;

@Repository
public interface ProgramInfoRepository extends JpaRepository<TblLmsProgramInfo, Integer>  {
//
//	@Query("SELECT p FROM TblLmsProgramInfo p WHERE b.program.id = :programId")
//	List<TblLmsBatchInfo> findBatchByPrg(@Param("programId") int programId);
}
