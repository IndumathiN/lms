package com.indu.lms.entity;


import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

import com.indu.lms.validator.ProgramExists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Immutable
@Table(name="tbl_lms_batch")
public class TblLmsBatchInfo {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private int batch_id;
	@NotBlank (message = "Batch Name is mandatory")
	private String batch_name;
	private String batch_description;
	@NotBlank (message = "Batch Status is mandatory")
	private String batch_status;
	
	 @ManyToOne
	 @JoinColumn(name = "batch_program_id")  // Creates Foreign Key in Student Table
	 @NotNull (message = "Program Id is mandatory") 
	//@ProgramExists(message = "Program not available in DB")
	 private TblLmsProgram program;
	 
	
	private int batch_no_of_classes;
	@UpdateTimestamp
	@Column(name = "creation_time", updatable = false)
	private LocalDateTime creation_time;
	@UpdateTimestamp
	private LocalDateTime last_mod_time;
}
