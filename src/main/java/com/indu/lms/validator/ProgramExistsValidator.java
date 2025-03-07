package com.indu.lms.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.indu.lms.entity.TblLmsProgramInfo;

import javax.persistence.EntityManager;

public class ProgramExistsValidator implements ConstraintValidator<ProgramExists, TblLmsProgramInfo> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean isValid(TblLmsProgramInfo parentEntity, ConstraintValidatorContext context) {
        if (parentEntity == null) {
            return false;
        }
        
        // Check if the parent entity exists in the database
        String query = "SELECT COUNT(p) FROM TblLmsProgramInfo p WHERE p.program_id = :id";
        System.out.println("Query "+ query);
        Long count = entityManager.createQuery(query, Long.class)
            .setParameter("id", parentEntity.getProgram_id())
            .getSingleResult();
        
        return count > 0; // Return true if parent exists, false otherwise
    }
}
