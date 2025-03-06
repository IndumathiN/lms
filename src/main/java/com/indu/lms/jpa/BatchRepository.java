package com.indu.lms.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indu.lms.entity.TblLmsBatch;

@Repository
public interface BatchRepository extends JpaRepository<TblLmsBatch, Integer> {
	@Query("SELECT b FROM TblLmsBatch b WHERE b.program.id = :programId")
	List<TblLmsBatch> findBatchByPrg(@Param("programId") int programId);
}
