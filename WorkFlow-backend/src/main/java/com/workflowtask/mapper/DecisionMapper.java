package com.workflowtask.mapper;

import com.workflowtask.dto.DecisionDTO;
import com.workflowtask.model.Decision;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DecisionMapper {

    DecisionDTO convertFromDecisionToDecisionDto(Decision decision);

    List<DecisionDTO> convertFromDecisionListToDecisionDtoList(List<Decision> decisions);

    // Used when the service resolves a decision by code before completing the
    // task, because the BPMN gateway compares the code as a string.
    Decision convertFromDecisionDtoToDecision(DecisionDTO decisionDTO);
}
