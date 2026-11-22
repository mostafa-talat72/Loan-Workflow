package com.workflowtask.service.impl;

import com.workflowtask.model.LoanApplication;
import com.workflowtask.service.LoanWorkflowService;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoanWorkflowServiceImpl implements LoanWorkflowService {

    private static final String PROCESS_KEY = "loanRequest";

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final HistoryService historyService;

    @Override
    public String start(LoanApplication application) {
        Map<String, Object> variables = new HashMap<>();

        variables.put("applicationId", application.getId());
        variables.put("amount", application.getAmount().doubleValue());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                PROCESS_KEY,
                String.valueOf(application.getId()),
                variables
        );


        return processInstance.getId();
    }

    @Override
    public Task getTask(String processInstanceId) {
        return taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
    }

    @Override
    public void completeTask(String taskId,Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    @Override
    public Task getTaskByRole(String processInstanceId, String userTaskId, String role) {
        return taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey(userTaskId)
                .taskCandidateGroup(role)
                .singleResult();
    }

    @Override
    public List<Task> pendingTasks(String candidateGroup) {
        return taskService.createTaskQuery()
                .taskCandidateGroup(candidateGroup)
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    @Override
    public boolean isFinished(String processInstanceId){
        HistoricProcessInstance historic = findHistoric(processInstanceId);
        return Objects.nonNull(historic) && Objects.nonNull(historic.getEndTime());
    }

    @Override
    public String currentEndEvent(String processInstanceId){
        HistoricProcessInstance historic = findHistoric(processInstanceId);
        return Objects.isNull(historic)? null : historic.getEndActivityId();
    }

    private HistoricProcessInstance findHistoric(String processInstanceId){
        return historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
    }

}
