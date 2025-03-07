package com.indu.lms.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.indu.lms.entity.TblLmsBatchInfo;

@Repository
public interface BatchInfoRepository extends JpaRepository<TblLmsBatchInfo, Integer> {
	@Query("SELECT b FROM TblLmsBatchInfo b WHERE b.program.id = :programId")
	Optional<List<TblLmsBatchInfo>> findBatchByPrg(@Param("programId") int programId);
}
