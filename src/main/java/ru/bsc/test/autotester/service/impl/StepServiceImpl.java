package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.repository.StepRepository;
import ru.bsc.test.autotester.service.StepService;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;

    @Autowired
    public StepServiceImpl(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Override
    public Step findOne(Long stepId) {
        return stepRepository.findOne(stepId);
    }

    @Override
    public Step save(Step step) {
        return stepRepository.save(step);
    }
}
