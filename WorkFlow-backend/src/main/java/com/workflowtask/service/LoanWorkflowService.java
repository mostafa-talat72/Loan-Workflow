package com.workflowtask.service;

import com.workflowtask.model.LoanApplication;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface LoanWorkflowService {

    String start(LoanApplication application);

    public Task getTask(String processInstanceId);

    public void completeTask(String taskId,Map<String, Object> variables);

    public Task getTaskByRole(String processInstanceId, String userTaskId, String role);

    List<Task> pendingTasks(String candidateGroup);

    boolean isFinished(String processInstanceId);

    String currentEndEvent(String processInstanceId);
}
