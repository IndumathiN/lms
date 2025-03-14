package com.indu.lms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.indu.lms.entity.TblLmsBatch;
import com.indu.lms.entity.TblLmsBatchInfo;
import com.indu.lms.entity.TblLmsProgramInfo;
import com.indu.lms.entity.TblLmsProgramInfo.TblLmsProgramInfoBuilder;
import com.indu.lms.exception.ErrorResponse;
import com.indu.lms.jpa.BatchInfoRepository;
import com.indu.lms.jpa.ProgramInfoRepository;
import com.indu.lms.model.BatchModel;
import com.indu.lms.model.ProgramModel;
import com.indu.lms.model.ResponseModel;

@Service
public class ProgramService {
	@Autowired
	ProgramInfoRepository programInfoRep;
	
	@Autowired
	BatchInfoRepository batchInfoRep;
	public ResponseModel saveProgram(ProgramModel program) {
		System.out.println(program);
		boolean isSuccess = false;
		HttpStatus reqStatus = HttpStatus.BAD_REQUEST;
		String reqMsg = "";
		// *******
		TblLmsProgramInfo newPrgEntity = TblLmsProgramInfo.builder().programName(program.getProgramName())
				.programDescription(program.getProgramDescription()).programStatus(program.getProgramStatus()).build();

		if (program.getProgramId() != null) {
			System.out.println("update ID NOT NULL*****");
			int prgId = Integer.valueOf(program.getProgramId());
			if (!programInfoRep.existsById(prgId)) {
				return ResponseModel.builder().isSuccess(false).error(ErrorResponse.builder()
						.status(HttpStatus.BAD_REQUEST).message("Error: Program ID does not exist!!").build()).build();

			} else {
				TblLmsProgramInfo checkPrgNameInOtherIds = programInfoRep
						.findByProgramNameAndProgramIdNot(program.getProgramName(), prgId);
				System.out.println(checkPrgNameInOtherIds);
				if (checkPrgNameInOtherIds != null) {
					reqMsg = "Error: Program Name already exists in other Id!!";
				} else {

					newPrgEntity.setProgramId(prgId);
					isSuccess = true;
					reqStatus = HttpStatus.OK;
					reqMsg = "Program Updated !!!";
					programInfoRep.save(newPrgEntity);
				}

			}
		}

		// *********
		else {
			System.out.println("create ID NULL **********");
			if (programInfoRep.existsByProgramName(program.getProgramName())) {

				reqMsg = "Error: Program already exist!!";

			} else {
//			TblLmsProgramInfo newPrgEntity = TblLmsProgramInfo.builder()
//					.programName(program.getProgramName())
//					.programDescription(program.getProgramDescription())
//					.programStatus(program.getProgramStatus())
//					.build();
				programInfoRep.save(newPrgEntity);
				isSuccess = true;
				reqStatus = HttpStatus.OK;
				reqMsg = "Program created !!!";

			}
		}
		return ResponseModel.builder().isSuccess(isSuccess)
				.error(ErrorResponse.builder().status(reqStatus).message(reqMsg).build()).build();
	}

	public ResponseModel deleteBatch(int id) {
		boolean isSuccess = false;
		HttpStatus reqStatus = HttpStatus.BAD_REQUEST;
		String reqMsg = "";
		
		boolean programExists = programInfoRep.existsById(id);
		if(!programExists) {
			reqMsg="Id doesn't exists!!";
		} else {
			Optional<TblLmsProgramInfo> program = programInfoRep.findById(id);
			Optional<List<TblLmsBatchInfo>> batches = batchInfoRep.findByProgram(program);
			System.out.println("size is ********** "+batches.get().size()+" ************");
			if (!batches.isEmpty() && batches.get().size() > 0) {
				reqMsg="Batches available for this program!!!";
			} else {
				programInfoRep.deleteById(id);
				isSuccess = true;
				reqStatus = HttpStatus.OK;
				reqMsg = "Program deleted !!!";

			}
		}
		return ResponseModel.builder().isSuccess(isSuccess)
				.error(ErrorResponse.builder().status(reqStatus).message(reqMsg).build()).build();
	}

	public List<ProgramModel> getAllProgram() {
		List<TblLmsProgramInfo> data= programInfoRep.findAll();
		
		 return data.stream()
               .map(prg -> ProgramModel.builder()
                       .programId(String.valueOf(prg.getProgramId()))
                       .programName(prg.getProgramName())
                       .programDescription(prg.getProgramDescription())
                       .programStatus(prg.getProgramStatus())
                       
                       .build()
                       )
               		.collect(Collectors.toList());
	}
}
