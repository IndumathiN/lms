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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

//import com.indu.lms.validator.ProgramExists;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(name="tbl_lms_batch")
public class TblLmsBatchInfo {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="batch_id")
	private int batchId;
	
	@NotBlank (message = "Batch Name is mandatory")
	@Column(name="batch_name")
	private String batchName;
	
	@Column(name="batch_description")
	private String batchDescription;
	
	@Column(name="batch_status")
	private String batchStatus;
	
	 @ManyToOne
	 @JoinColumn(name = "batch_program_id")  // Creates Foreign Key in Student Table
	 @NotNull (message = "Program Id is mandatory") 
	// @Min(value = 1, message = "Program_id must be a positive integer")
	//@ProgramExists(message = "Program not available in DB")
	 private TblLmsProgramInfo program;
	 
	 @Column(name="batch_no_of_classes")
	private int batchNoOfClasses;
	@UpdateTimestamp
	@Column(name = "creation_time", updatable = false)
	private LocalDateTime creationTime;
	@UpdateTimestamp
	@Column(name = "last_mod_time")
	private LocalDateTime lastModTime;
	
	 @PrePersist
	    public void setDefaultValues() {
	        if (this.batchStatus == null || this.batchStatus == "") {
	            this.batchStatus = "Active";  // Default value
	        }
	        if (this.batchNoOfClasses == 0) {
	            this.batchNoOfClasses = 1;  // Default value
	        }
	    }
}
