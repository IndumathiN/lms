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
import com.indu.lms.entity.TblLmsProgram;
import com.indu.lms.entity.TblLmsProgramInfo;
import com.indu.lms.exception.ResourceNotFoundException;
import com.indu.lms.jpa.BatchInfoRepository;
import com.indu.lms.jpa.BatchRepository;
import com.indu.lms.jpa.ProgramInfoRepository;
import com.indu.lms.jpa.ProgramRepository;
import com.indu.lms.model.TblLmsBatchMdl;

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

	@GetMapping("/programs")
	public Optional<List<TblLmsProgram>> all() {
		Optional<List<TblLmsProgram>> output = Optional.ofNullable(programRep.findAll());
		if (output.isPresent()) {
			return output;
		} else {
			throw new ResourceNotFoundException("No Program!!");
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

	@PostMapping("/program")
	public ResponseEntity<String> createProgram(@Valid @RequestBody TblLmsProgram newPrg) {
		Optional<TblLmsProgram> output=programRep.findByProgramName(newPrg.getProgram_name());
		 if (!output.isEmpty()) {
			 return ResponseEntity.status(HttpStatus.CREATED).body("Program already exists!!");
         }
		programRep.save(newPrg);
		return ResponseEntity.status(HttpStatus.CREATED).body("Program created successfully");
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

	// Delete Program
	@DeleteMapping("/programs/{id}")
	public ResponseEntity<String> deleteProgram(@PathVariable int id) {
		System.out.println("****************************************");
		Optional<TblLmsProgram> program = programRep.findById(id);
		System.out.println("stmt");
		if (!program.isPresent()) {
			System.out.println("if stmt");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Program does not exist");
		} else {
			Optional<List<TblLmsBatch>> batches = batchRep.findByBatchProgramId(id);
			System.out.println("else stmt");
			System.out.println(batches);
			if (!batches.isEmpty() && batches.get().size() > 0) {
				
				System.out.println("else if stmt");
				// throw new RuntimeException("Cannot delete: Program is referenced in
				// batches.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Cannot delete: Program is referenced in batches.");
			}

			programRep.deleteById(id);
			return ResponseEntity.status(HttpStatus.CREATED).body("Program deleted successfully");
		}
	}

	// BATCH CRUD OPERATIONS

	@GetMapping("/batches")
	Optional<List<TblLmsBatch>> allBatch() {
		Optional<List<TblLmsBatch>> output = Optional.ofNullable(batchRep.findAll());
		if (output.isPresent()) {
			return output;
		} else {
			throw new ResourceNotFoundException("Batch not yet created!!");
		}
		//return batchRep.findAll();
	}

	@GetMapping("/batches/{id}")
	Optional<TblLmsBatch> findBatch(@PathVariable int id) {
		Optional<TblLmsBatch> output = batchRep.findById(id);
		if (output.isPresent()) {
			return output;
		} else {
			throw new ResourceNotFoundException("Batch Id " + id + " doesn't exists!!");
		}
		//return batchRep.findById(id);
	}

	@PostMapping("/batch")
	public ResponseEntity<String> createBatch(@Valid @RequestBody TblLmsBatch newBatch) {
		if(newBatch.getBatch_status()=="") {
			newBatch.setBatch_status("Active");
		}
		Optional<TblLmsProgram> program = programRep.findById(newBatch.getBatch_program_id());
		if (!program.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Program ID does not exist");
		}
		
		else {
			Optional<TblLmsBatch> batchnamePrg = batchRep.findByNamePrg(newBatch.getBatch_name(),newBatch.getBatch_program_id());
			if (batchnamePrg.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Batch already exists!!");
			}
			batchRep.save(newBatch);
			return ResponseEntity.status(HttpStatus.CREATED).body("Batch created successfully");
		}
		
	}

	@PostMapping("/batchInfo")
	public ResponseEntity<String> createBatchInfo(@Valid @RequestBody TblLmsBatchInfo newBatch) {

		Optional<TblLmsProgram> program = programRep.findById(newBatch.getProgram().getProgram_id());

		if (!program.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Program ID does not exist");
		}
		newBatch.setProgram(program.get());
		batchInfoRep.save(newBatch);
		return ResponseEntity.status(HttpStatus.CREATED).body("Batch created successfully");
	}

//	TblLmsBatch createBatch (@RequestBody TblLmsBatch newPrg) {
//	 return batchRep.save(newPrg);
//	 }
	// Update Customer Information
	@PutMapping("/batches/{id}")
	public ResponseEntity<String> updateBatch(@Valid @RequestBody TblLmsBatch updatedData, @PathVariable int id) {
		
		Optional<TblLmsProgram> program = programRep.findById(updatedData.getBatch_program_id());
		if (!program.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Program ID does not exist");
		}
		else {
			Optional<TblLmsBatch> batchnamePrg = batchRep.findByNamePrg(updatedData.getBatch_name(),updatedData.getBatch_program_id());
			if (batchnamePrg.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Batch already exists!!");
			}
			batchRep.save(updatedData);
			return ResponseEntity.status(HttpStatus.CREATED).body("Batch updated successfully");
		}
		
	}

	// Delete Batch
	@DeleteMapping("/batches/{id}")
	public ResponseEntity<String> deleteBatch(@PathVariable int id) {
		Optional<TblLmsBatch> batch = batchRep.findById(id);
		if (!batch.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Batch does not exist");
		}
		batchRep.deleteById(id);
		return ResponseEntity.status(HttpStatus.CREATED).body("Batch deleted successfully");
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
