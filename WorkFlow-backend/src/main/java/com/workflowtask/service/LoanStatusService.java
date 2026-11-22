package com.workflowtask.service;

import com.workflowtask.model.LoanStatus;

public interface LoanStatusService {

    LoanStatus getByCode(String code);
}
