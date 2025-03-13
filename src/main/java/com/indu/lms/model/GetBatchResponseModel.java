package com.indu.lms.model;

import com.indu.lms.exception.ErrorResponse;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class GetBatchResponseModel {
	private BatchModel batch;

	private ResponseModel resModel;

}
