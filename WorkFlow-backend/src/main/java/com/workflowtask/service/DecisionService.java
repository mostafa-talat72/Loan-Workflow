package com.workflowtask.service;

import com.workflowtask.model.Decision;

public interface DecisionService {

    Decision getByCode(String code);
}
