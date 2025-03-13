package com.indu.lms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.indu.lms.entity.TblLmsBatchInfo;
import com.indu.lms.entity.TblLmsProgramInfo;
import com.indu.lms.exception.ErrorResponse;
import com.indu.lms.jpa.BatchInfoRepository;
import com.indu.lms.jpa.ProgramInfoRepository;
import com.indu.lms.model.BatchModel;
import com.indu.lms.model.ResponseModel;

@Service
public class BatchService {
	@Autowired
	BatchInfoRepository batchInfoRep;
	@Autowired
	ProgramInfoRepository programInfoRep;
	
	 public ResponseModel saveBatch(BatchModel newBatch) {
		 int programId=0;
			int batchNoOfClasses = 1;
			try {
				
				programId=Integer.valueOf(newBatch.getProgramId()) ;
			}
			catch(NumberFormatException e) {
				
				return ResponseModel.builder()
						.isSuccess(false)
						.error(ErrorResponse.builder()
								.status(HttpStatus.BAD_REQUEST)
								.message("Error: Program ID should be numeric")
								.build())
						.build();
						
			}
			
			try {
				batchNoOfClasses=Integer.valueOf(newBatch.getBatchNoOfClasses()) ;
			}
			catch(NumberFormatException e) {
				
			}
			
			String batchName=newBatch.getBatchName();
			
			
			
			if(!programInfoRep.existsById(programId)) {
				return ResponseModel.builder()
						.isSuccess(false)
						.error(ErrorResponse.builder()
								.status(HttpStatus.BAD_REQUEST)
								.message("Error: Program ID does not exist!!")
								.build())
						.build();
				
			}
			//boolean batchExists=batchInfoRep.existsByBatch_nameAndBatchProgramId(batchName,programId);
			Optional<TblLmsProgramInfo> program = programInfoRep.findById(programId);
			boolean batchExists=batchInfoRep.existsByBatchNameAndProgram(batchName,program.get());
			System.out.println("**************");
			System.out.println(batchExists);
			if (batchExists) {
				return ResponseModel.builder()
				.isSuccess(false)
				.error(ErrorResponse.builder()
						.status(HttpStatus.BAD_REQUEST)
						.message("Error: Batch already exists!!")
						.build())
				.build();
				
			}

			
			TblLmsProgramInfo newPrgEntity = TblLmsProgramInfo.builder()
					.programId(programId)
					.build()	;
//			TblLmsBatchInfoBuilder builder = TblLmsBatchInfo.builder();
			TblLmsBatchInfo newBatchEntity=TblLmsBatchInfo.builder()
					.batchName(batchName)
					.batchDescription(newBatch.getBatchDescription())
					.batchStatus(newBatch.getBatchStatus())
					.batchNoOfClasses(batchNoOfClasses)
					.program(newPrgEntity)
					.build();
	        batchInfoRep.save(newBatchEntity);
	      
	        return ResponseModel.builder()
					.isSuccess(true)
					.message("Batch created successfully")
					.build();
	    }
}
