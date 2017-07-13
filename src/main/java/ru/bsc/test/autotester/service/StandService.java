package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Stand;

/**
 * Created by sdoroshin on 12.07.2017.
 *
 */
public interface StandService {
    Stand findOne(Long id);
}
