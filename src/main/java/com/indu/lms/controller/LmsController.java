package com.indu.lms.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indu.lms.entity.TblLmsBatch;
import com.indu.lms.entity.TblLmsBatchInfo;
import com.indu.lms.entity.TblLmsBatchInfo.TblLmsBatchInfoBuilder;
import com.indu.lms.entity.TblLmsProgram;
import com.indu.lms.entity.TblLmsProgramInfo;
import com.indu.lms.exception.ErrorResponse;
import com.indu.lms.exception.ResourceNotFoundException;
import com.indu.lms.jpa.BatchInfoRepository;
import com.indu.lms.jpa.BatchRepository;
import com.indu.lms.jpa.ProgramInfoRepository;
import com.indu.lms.jpa.ProgramRepository;
import com.indu.lms.model.BatchModel;
import com.indu.lms.model.ProgramModel;
import com.indu.lms.model.ResponseModel;
import com.indu.lms.model.TblLmsBatchMdl;
import com.indu.lms.service.BatchService;
import com.indu.lms.service.ProgramService;

@RestController
@RequestMapping("/")
public class LmsController {

	@Autowired
	BatchRepository batchRep;

	@Autowired
	BatchInfoRepository batchInfoRep;

	@Autowired
	ProgramRepository programRep;

	@Autowired
	ProgramInfoRepository programInfoRep;

	@Autowired
	BatchService batchService;

	@Autowired
	ProgramService programService;
	
	
	
	// *****************************************************************************
	@PostMapping("/programInfo")
	public ResponseModel createProgram(@Valid @RequestBody ProgramModel newPrg) {
		return programService.saveProgram(newPrg);
	}

	@PutMapping("/programInfo/{id}")
	public ResponseModel updateProgram(@Valid @RequestBody ProgramModel program, @PathVariable String id) {

		program.setProgramId(id);
		if (program.getProgramStatus() == "" || program.getProgramStatus() == null) {
			program.setProgramStatus("Active");
		}
		return programService.saveProgram(program);

	}

	// Delete Program
	@DeleteMapping("/delProgram/{id}")
	public ResponseModel deleteProgram(@PathVariable int id) {
		
		return programService.deleteBatch(id);

	}

	@PostMapping("/batchInfo")
	public ResponseEntity<ResponseModel> createBatchInfo(@Valid @RequestBody BatchModel newBatch) {
		HttpStatus status = HttpStatus.OK;
		ResponseModel response = batchService.saveBatch(newBatch);

		if (response != null) {
			if (!response.isSuccess() && response.getError() != null) {
				status = response.getError().getStatus();
			}
			return ResponseEntity.status(status).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseModel.builder().isSuccess(false).error(ErrorResponse.builder()
							.status(HttpStatus.INTERNAL_SERVER_ERROR).message("Error:Something went wrong").build())
							.build());
		}

	}

	@PutMapping("/batchInfo/{id}")
	public ResponseEntity<ResponseModel> updateBatchInfo(@Valid @RequestBody BatchModel updatedData,
			@PathVariable String id) {

		updatedData.setBatchId(id);
		ResponseModel response = batchService.saveBatch(updatedData);
		HttpStatus status = HttpStatus.OK;
		if (response != null) {
			if (!response.isSuccess() && response.getError() != null) {
				status = response.getError().getStatus();
			}
			return ResponseEntity.status(status).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseModel.builder().isSuccess(false).error(ErrorResponse.builder()
							.status(HttpStatus.INTERNAL_SERVER_ERROR).message("Error:Something went wrong").build())
							.build());
		}
	}

	// Delete Batch
	@DeleteMapping("/batchInfo/{id}")
	public ResponseEntity<ResponseModel> deleteBatch(@PathVariable int id) {
		// Optional<TblLmsBatch> batch = batchRep.findById(id);
		ResponseModel response = batchService.deleteBatch(id);
		HttpStatus status = HttpStatus.OK;
		if (response != null) {
			if (!response.isSuccess() && response.getError() != null) {
				status = response.getError().getStatus();
			}
			return ResponseEntity.status(status).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseModel.builder().isSuccess(false).error(ErrorResponse.builder()
							.status(HttpStatus.INTERNAL_SERVER_ERROR).message("Error:Something went wrong").build())
							.build());
		}
	}

	@GetMapping("/batchInfo")
	List<BatchModel> allBatch() {
		List<BatchModel> output = batchService.getBatch();

		if (!output.isEmpty()) {
			return output;
		} else {
			throw new ResourceNotFoundException("Batch not yet created!!");
		}

	}

	@GetMapping("/batchInfoById/{id}")
	Optional<BatchModel> batchById(@PathVariable int id) {
		Optional<BatchModel> output = batchService.getBatchById(id);

		if (output.isPresent()) {
			return output;
		} else {
			throw new ResourceNotFoundException("Batch ID doesn't exists");
		}

	}

//		********************************************************************
	
	@GetMapping("/programs")
	public List<ProgramModel> allProgram() {
		List<ProgramModel> output = programService.getAllProgram();

		if (!output.isEmpty()) {
			return output;
		} else {
			throw new ResourceNotFoundException("Program not yet created!!");
		}
	}
	@GetMapping("/programInfos")
	Optional<List<TblLmsProgramInfo>> allInfo() {
		Optional<List<TblLmsProgramInfo>> output = Optional.ofNullable(programInfoRep.findAll());
		if (output.isPresent()) {
			return output;
		} else {
			throw new ResourceNotFoundException("No Program!!");
		}
		// return programInfoRep.findAll();
	}

	@GetMapping("/programs/{id}")
	Optional<TblLmsProgram> findProgram(@PathVariable int id) {
		Optional<TblLmsProgram> output = programRep.findById(id);
		if (output.isPresent()) {
			return output;
		} else {
			throw new ResourceNotFoundException("Program Id " + id + " doesn't exists!!");
		}

	}

	// Update prg Information
	@PutMapping("/programs/{id}")
	public ResponseEntity<String> updateProgram(@Valid @RequestBody TblLmsProgram updatedProgram,
			@PathVariable int id) {
		Optional<TblLmsProgram> program = programRep.findById(id);
		if (!program.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Program ID does not exist");
		}
		programRep.save(updatedProgram);
		return ResponseEntity.status(HttpStatus.CREATED).body("Program ID " + id + " updated successfully");
	}

	// BATCH CRUD OPERATIONS

	@GetMapping("/batches/{id}")
	Optional<TblLmsBatch> findBatch(@PathVariable int id) {
		Optional<TblLmsBatch> output = batchRep.findById(id);
		if (output.isPresent()) {
			return output;
		} else {
			throw new ResourceNotFoundException("Batch Id " + id + " doesn't exists!!");
		}
		// return batchRep.findById(id);
	}

	@PostMapping("/batch")
	public ResponseEntity<String> createBatch(@Valid @RequestBody TblLmsBatch newBatch) {
		if (newBatch.getBatch_status() == "") {
			newBatch.setBatch_status("Active");
		}
		Optional<TblLmsProgram> program = programRep.findById(newBatch.getBatch_program_id());
		if (!program.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Program ID does not exist");
		}

		else {
			Optional<TblLmsBatch> batchnamePrg = batchRep.findByNamePrg(newBatch.getBatch_name(),
					newBatch.getBatch_program_id());
			if (batchnamePrg.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Batch already exists!!");
			}
			batchRep.save(newBatch);
			return ResponseEntity.status(HttpStatus.CREATED).body("Batch created successfully");
		}

	}

	@PutMapping("/batches/{id}")
	public ResponseEntity<String> updateBatch(@Valid @RequestBody TblLmsBatch updatedData, @PathVariable int id) {

		Optional<TblLmsProgram> program = programRep.findById(updatedData.getBatch_program_id());
		if (!program.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Program ID does not exist");
		} else {
			Optional<TblLmsBatch> batchnamePrg = batchRep.findByNamePrg(updatedData.getBatch_name(),
					updatedData.getBatch_program_id());
			if (batchnamePrg.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Batch already exists!!");
			}
			batchRep.save(updatedData);
			return ResponseEntity.status(HttpStatus.CREATED).body("Batch updated successfully");
		}

	}

	@GetMapping("/batches/withProgName")
	public Optional<List<TblLmsBatchMdl>> getBatchWithProgName() {
		// return batchRep.findAllBatchWithProgramName();
		Optional<List<TblLmsBatchMdl>> output = batchRep.findAllBatchWithProgramName();
		if (output.isPresent() && !output.get().isEmpty()) {
			return output;
		} else {
			throw new ResourceNotFoundException("No Batch found");
		}
	}

	@GetMapping("/batches/withProgName/{prgId}")
	public Optional<List<TblLmsBatchMdl>> getBatchWithProgNameByProgId(@PathVariable int prgId) {
		// return batchRep.findBatchWithProgNameByProgId(prgId).orElseThrow(() -> new
		// RuntimeException("Student with ID " + prgId + " not found"));
		Optional<List<TblLmsBatchMdl>> output = batchRep.findBatchWithProgNameByProgId(prgId);
		if (output.isPresent() && !output.get().isEmpty()) {
			return output;
		} else {
			throw new ResourceNotFoundException("No Batch found for Program ID: " + prgId);
		}

	}

	@GetMapping("/byProgram/{prgId}")
	public Optional<List<TblLmsBatchInfo>> getBatchByPrg(@PathVariable int prgId) {
		Optional<List<TblLmsBatchInfo>> output = batchInfoRep.findBatchByPrg(prgId);
		if (output.isPresent() && !output.get().isEmpty()) {
			return output;
		} else {
			throw new ResourceNotFoundException("No Batch found for Program ID: " + prgId);
		}
	}
}
