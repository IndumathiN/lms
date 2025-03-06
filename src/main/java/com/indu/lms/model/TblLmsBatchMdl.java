package com.indu.lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TblLmsBatchMdl {

	private String batch_name;
	private int batch_no_of_classes;
	private String program_name;
	private int batch_id;
}
