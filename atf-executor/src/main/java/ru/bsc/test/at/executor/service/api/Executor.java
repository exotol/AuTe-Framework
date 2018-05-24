package ru.bsc.test.at.executor.service.api;

/**
 * @author Pavel Golovkin
 */
public interface Executor<T extends ExecutorRequest> {

    void execute(T executorRequest);
}
