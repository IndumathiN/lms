package com.indu.lms.entity;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name="tbl_lms_program")
public class TblLmsProgramInfo {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name="program_id")
	private int programId;
	
	@NotBlank (message = "Program Name is mandatory")
	@Column(name="program_name")
	private String programName;
	
	@Column(name="program_description")
	private String programDescription;
	
	@Column(name="program_status")
	private String programStatus;
	
	@UpdateTimestamp
	@Column(name = "creation_time", updatable = false)
	private LocalDateTime creationTime;
	
	@UpdateTimestamp
	@Column(name = "last_mod_time")
	private LocalDateTime lastModTime;
	
//	 @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	 private List<TblLmsBatchInfo> batchList;
	 
}
