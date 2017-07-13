package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.autotester.repository.StandRepository;
import ru.bsc.test.autotester.service.StandService;

import java.util.List;

/**
 * Created by sdoroshin on 12.07.2017.
 *
 */
@Service
public class StandServiceImpl implements StandService {

    private final StandRepository standRepository;

    @Autowired
    public StandServiceImpl(StandRepository standRepository) {
        this.standRepository = standRepository;
    }

    @Override
    public Stand findOne(Long id) {
        return standRepository.findOne(id);
    }

    @Override
    public List<Stand> save(List<Stand> standList) {
        return standRepository.save(standList);
    }

    @Override
    public Stand save(Stand stand) {
        return standRepository.save(stand);
    }
}
