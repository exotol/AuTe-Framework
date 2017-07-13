package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Stand;

import java.util.List;

/**
 * Created by sdoroshin on 12.07.2017.
 *
 */
public interface StandService {
    Stand findOne(Long id);
    List<Stand> save(List<Stand> standList);
    Stand save(Stand stand);
}
