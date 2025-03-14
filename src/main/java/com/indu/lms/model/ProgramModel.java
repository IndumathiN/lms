package com.indu.lms.model;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramModel {
	 private String programId;
     @NotBlank(message = "Program Name is mandatory")
     private String programName;
     private String programDescription;
     private String programStatus;
}
