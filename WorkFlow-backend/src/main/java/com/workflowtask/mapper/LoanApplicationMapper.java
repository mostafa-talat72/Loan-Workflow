package com.workflowtask.mapper;

import com.workflowtask.dto.LoanApplicationDTO;
import com.workflowtask.dto.LoanApplicationRequestDTO;
import com.workflowtask.model.LoanApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// The nested applicant and lookup rows are produced by their own mappers.
@Mapper(componentModel = "spring",
        uses = {UserAccountMapper.class, LoanStatusMapper.class, DecisionMapper.class})
public interface LoanApplicationMapper {

    LoanApplicationDTO convertFromLoanApplicationToLoanApplicationDto(LoanApplication loanApplication);

    List<LoanApplicationDTO> convertFromLoanApplicationListToLoanApplicationDtoList(
            List<LoanApplication> loanApplications);

    // The id is database generated, the applicant comes from the authenticated
    // user, the status is resolved by the service, and the remaining fields are
    // written by the workflow engine and the delegates.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicant", ignore = true)
    @Mapping(target = "creditScore", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "processInstanceId", ignore = true)
    @Mapping(target = "officerDecision", ignore = true)
    @Mapping(target = "officerNotes", ignore = true)
    @Mapping(target = "managerDecision", ignore = true)
    @Mapping(target = "managerNotes", ignore = true)
    @Mapping(target = "disbursementRef", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    LoanApplication convertFromLoanApplicationRequestDtoToLoanApplication(
            LoanApplicationRequestDTO requestDTO);
}
