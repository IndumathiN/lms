package com.indu.lms.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indu.lms.entity.TblLmsBatch;
import com.indu.lms.model.TblLmsBatchMdl;

@Repository
public interface BatchRepository extends JpaRepository<TblLmsBatch, Integer> {
	@Query(value = "SELECT b.batch_name as batchname, b.batch_no_of_classes as batchnoofclasses, p.program_name as programname, b.batch_id as batchid FROM tbl_lms_program p inner join tbl_lms_batch b on b.batch_program_id = p.program_id", nativeQuery = true)
	Optional<List<TblLmsBatchMdl>> findAllBatchWithProgramName();
	
	@Query(value = "SELECT b.batch_name as batchname, b.batch_no_of_classes as batchnoofclasses, p.program_name as programname, b.batch_id as batchid FROM tbl_lms_program p inner join tbl_lms_batch b on b.batch_program_id = p.program_id where p.program_id = :programId", nativeQuery = true)
	 Optional<List<TblLmsBatchMdl>> findBatchWithProgNameByProgId(@Param("programId") int programId);
	
	@Query(value = "SELECT * FROM tbl_lms_batch  where batch_program_id = :programId", nativeQuery = true)
	Optional<List<TblLmsBatch>> findByBatchProgramId(@Param("programId") int id);
	
	@Query(value = "SELECT * FROM tbl_lms_batch  where batch_name=:batchName and batch_program_id = :programId", nativeQuery = true)
	Optional<TblLmsBatch> findByNamePrg(@Param("batchName") String prgName,@Param("programId")int id);
	
	

	//Optional<List<TblLmsBatch>> findByBatchProgramId(int id);
}
