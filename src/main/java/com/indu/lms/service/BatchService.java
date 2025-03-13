package com.indu.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indu.lms.entity.TblLmsBatchInfo;
import com.indu.lms.jpa.BatchInfoRepository;
import com.indu.lms.jpa.ProgramInfoRepository;

@Service
public class BatchService {
	@Autowired
	BatchInfoRepository batchInfoRep;
	@Autowired
	ProgramInfoRepository programInfoRep;
	
	 public TblLmsBatchInfo saveProduct(TblLmsBatchInfo batch) {
	        if (batch.getProgram().getProgram_id() == null || !programInfoRep.existsById(batch.getProgram().getProgram_id())) {
	            throw new IllegalArgumentException("Invalid Category ID");
	        }
	        return batchInfoRep.save(batch);
	    }
}
