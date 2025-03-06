package com.indu.lms.entity;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
public class TblLmsProgram {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	
	private int program_id;
	@NotBlank (message = "Program Name is mandatory")
	private String program_name;
	private String program_description;
	private String program_status;
	
	@UpdateTimestamp
	@Column(name = "creation_time", updatable = false)
	private LocalDateTime creation_time;
	@UpdateTimestamp
	private LocalDateTime last_mod_time;
	
	// @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
	   // private List<TblLmsBatch> batchList;
	 
//	 @PrePersist
//	    protected void onCreate() {
//		 creation_time = LocalDateTime.now();  // Set only once at creation
//		 last_mod_time = creation_time;
//	    }
//
//	    @PreUpdate
//	    protected void onUpdate() {
//	    	last_mod_time = LocalDateTime.now();  // Update only when modified
//	    }
}
