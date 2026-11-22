package com.workflowtask.service.impl;

import com.workflowtask.model.Decision;
import com.workflowtask.repo.DecisionRepo;
import com.workflowtask.service.DecisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DecisionServiceImpl implements DecisionService {

    private final DecisionRepo decisionRepo;

    @Override
    public Decision getByCode(String code) {
        return decisionRepo.findByCode(code).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown decision code: " + code)
        );
    }
}
