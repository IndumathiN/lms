//package com.indu.lms.validator;
//
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = ProgramExistsValidator.class)
//public @interface ProgramExists {
//	
//    String message() default "Parent entity does not exist";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//}
