package com.indu.lms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.indu.lms.entity.TblLmsBatchInfo;
import com.indu.lms.entity.TblLmsBatchInfo.TblLmsBatchInfoBuilder;
import com.indu.lms.entity.TblLmsProgramInfo;
import com.indu.lms.exception.ErrorResponse;
import com.indu.lms.jpa.BatchInfoRepository;
import com.indu.lms.jpa.ProgramInfoRepository;
import com.indu.lms.model.BatchModel;
import com.indu.lms.model.ResponseModel;

@Service
@Validated
public class BatchService {
	@Autowired
	BatchInfoRepository batchInfoRep;
	@Autowired
	ProgramInfoRepository programInfoRep;
	
	public List<BatchModel> getBatch(){
		List<TblLmsBatchInfo> allBatchData= batchInfoRep.findAll();
		
		 return allBatchData.stream()
                .map(batch -> BatchModel.builder()
                        .batchId(String.valueOf(batch.getBatchId()))
                        .batchName(batch.getBatchName())
                        .batchDescription(batch.getBatchDescription())
                        .batchStatus(batch.getBatchStatus())
                        .programId(String.valueOf(batch.getProgram().getProgramId()))
                        .batchNoOfClasses(String.valueOf(batch.getBatchNoOfClasses()))
                        .programName(String.valueOf(batch.getProgram().getProgramName()))
                        .build()
                        )
                		.collect(Collectors.toList());
	}
	public Optional<BatchModel> getBatchById(int id) {
		Optional<TblLmsBatchInfo> batchData= batchInfoRep.findById(id);
		return batchData.map(batch -> BatchModel.builder()
                .batchId(String.valueOf(batch.getBatchId()))
                .batchName(batch.getBatchName())
                .batchDescription(batch.getBatchDescription())
                .batchStatus(batch.getBatchStatus())
                .programId(String.valueOf(batch.getProgram().getProgramId()))
                .batchNoOfClasses(String.valueOf(batch.getBatchNoOfClasses()))
                .programName(String.valueOf(batch.getProgram().getProgramName()))
                .build()
        );
		
	}
	public ResponseModel deleteBatch(int id) {
		
		boolean isExists = batchInfoRep.existsById(id);
		System.out.println("batch id "+id+" ** "+isExists);
		HttpStatus reqStatus=HttpStatus.OK;
		String reqMessage="Batch deleted!!!";
		if(!isExists) {
			reqStatus=HttpStatus.BAD_REQUEST;
			reqMessage="Error: Batch ID does not exist!!";
		} else {
			batchInfoRep.deleteById(id);
		}
		return ResponseModel.builder()
				.isSuccess(isExists)
				.error(ErrorResponse.builder()
						.status(reqStatus)
						.message(reqMessage)
						.build())
				.build();
	}
	 public ResponseModel saveBatch(BatchModel newBatch) {
		 System.out.println(newBatch);
		
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
				return ResponseModel.builder()
						.isSuccess(false)
						.error(ErrorResponse.builder()
								.status(HttpStatus.BAD_REQUEST)
								.message("Error: No. of class should be numeric")
								.build())
						.build();
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
			
			Optional<TblLmsProgramInfo> program = programInfoRep.findById(programId);
			boolean batchExists=batchInfoRep.existsByBatchNameAndProgram(batchName,program.get());
			
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
					.build();

			TblLmsBatchInfoBuilder batchEntity=TblLmsBatchInfo.builder()
					.batchName(batchName)
					.batchDescription(newBatch.getBatchDescription())
					.batchStatus(newBatch.getBatchStatus())
					.batchNoOfClasses(batchNoOfClasses)
					.program(newPrgEntity);
			String msg="Created";
			 if (newBatch.getBatchId() != null) {
				 int batchId=Integer.valueOf(newBatch.getBatchId());
				 if(!batchInfoRep.existsById(batchId)) {
						return ResponseModel.builder()
								.isSuccess(false)
								.error(ErrorResponse.builder()
										.status(HttpStatus.BAD_REQUEST)
										.message("Error: Batch ID does not exist!!")
										.build())
								.build();
						
					} else {
						batchEntity.batchId(Integer.valueOf(newBatch.getBatchId()));
						msg="Updated";
					}
			 } 
			TblLmsBatchInfo batchDetails=batchEntity.build();
			
	        batchInfoRep.save(batchDetails);
	      
	        return ResponseModel.builder()
					.isSuccess(true)
					.message("Batch "+msg+" successfully")
					.build();
	    }
	
}
