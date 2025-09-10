package ru.larkin.service;

import ru.larkin.repository.OperationRepository;

public class OperationService {

    private final OperationRepository operationRepository;

    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }
}
