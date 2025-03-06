package com.indu.lms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	 List<TblLmsProgram> all(){
	 return programRep.findAll(); 
	 }
	 @GetMapping("/programInfos")
	 List<TblLmsProgramInfo> allInfo(){
	 return programInfoRep.findAll(); 
	 }
	 
	 @GetMapping("/programs/{id}")
	 Optional <TblLmsProgram> findProgram(@PathVariable int id){
	 return programRep.findById(id);
	 }
	@PostMapping("/program")
	 TblLmsProgram createProgram (@RequestBody TblLmsProgram newPrg) {
	 return programRep.save(newPrg);
	 }
	 // Update Customer Information
	 @PutMapping("/programs/{id}")
	 TblLmsProgram updateProgram (@RequestBody TblLmsProgram updatedProgram, @PathVariable int id) {
	 return programRep.save(updatedProgram);
	 }
	 
	 // Delete Single Customer
	 @DeleteMapping ("/programs/{id}")
	 void deleteProgram(@PathVariable int id){
		 programRep.deleteById(id);
	 }
	 
	 //BATCH CRUD OPERATIONS
	
	 @GetMapping("/batches")
	 List<TblLmsBatch> allBatch(){
	 return batchRep.findAll(); 
	 }
	 @GetMapping("/batches/{id}")
	 Optional <TblLmsBatch> findBatch(@PathVariable int id){
	 return batchRep.findById(id);
	 }
	@PostMapping("/batch")
	TblLmsBatch createBatch (@RequestBody TblLmsBatch newPrg) {
	 return batchRep.save(newPrg);
	 }
	 // Update Customer Information
	 @PutMapping("/batches/{id}")
	 TblLmsBatch updateBatch(@RequestBody TblLmsBatch updatedProgram, @PathVariable int id) {
	 return batchRep.save(updatedProgram);
	 }
	 
	 // Delete Single Customer
	 @DeleteMapping ("/batches/{id}")
	 void deleteBatch(@PathVariable int id){
		 batchRep.deleteById(id);
	 }
	 
	 @GetMapping("/batches/withProgName")
	    public List<TblLmsBatchMdl> getBatchWithProgName() {
	        return batchRep.findAllBatchWithProgramName();
	    }
	 
	 @GetMapping("/batches/withProgName/{prgId}")
	    public List<TblLmsBatchMdl> getBatchWithProgNameByProgId(@PathVariable int prgId) {
	        return batchRep.findBatchWithProgNameByProgId(prgId);
	    }
	 
	 @GetMapping("/byProgram/{prgId}")
	    public List<TblLmsBatchInfo> getBatchByPrg(@PathVariable int prgId) {
	        return batchInfoRep.findBatchByPrg(prgId);
	    }
}
