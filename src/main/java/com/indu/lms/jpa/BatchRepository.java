package com.indu.lms.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indu.lms.entity.TblLmsBatch;
import com.indu.lms.model.TblLmsBatchMdl;

@Repository
public interface BatchRepository extends JpaRepository<TblLmsBatch, Integer> {
	@Query(value = "SELECT b.batch_name, b.batch_no_of_classes, p.program_name, b.batch_id FROM tbl_lms_program p inner join tbl_lms_batch b on b.batch_program_id = p.program_id where p.program_id = :programId", nativeQuery = true)
	List<TblLmsBatchMdl> findBatchByProgramId(@Param("programId") int programId);
}
