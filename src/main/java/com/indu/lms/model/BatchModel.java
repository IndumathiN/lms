package com.indu.lms.model;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BatchModel {
	 
	     private String batchId;
	     @NotBlank(message = "Batch Name is mandatory")
	     private String batchName;
	     private String batchDescription;
	     private String batchStatus;
	     @NotBlank(message = "Program ID is mandatory")
	     private String programId;
	     private String batchNoOfClasses;
	     private String programName;
	     
}
