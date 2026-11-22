package com.workflowtask.mapper;

import com.workflowtask.dto.LoanStatusDTO;
import com.workflowtask.model.LoanStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanStatusMapper {

    LoanStatusDTO convertFromLoanStatusToLoanStatusDto(LoanStatus loanStatus);

    List<LoanStatusDTO> convertFromLoanStatusListToLoanStatusDtoList(List<LoanStatus> loanStatuses);

    // Used when the service resolves a status by code and needs the entity.
    LoanStatus convertFromLoanStatusDtoToLoanStatus(LoanStatusDTO loanStatusDTO);
}
