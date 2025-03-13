package com.indu.lms.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.indu.lms.exception.ErrorResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ResponseModel {
 private boolean isSuccess;
 private String message;
 private ErrorResponse error;
 private List<BatchModel> batchList;
 
 
}
