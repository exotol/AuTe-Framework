package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;
import ru.bsc.test.at.executor.model.NameValueProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by smakarov
 * 20.04.2018 12:22
 */
@Getter
@Setter
public class MqMessageRo implements AbstractRo {
    private static final long serialVersionUID = -1549749532101759753L;

    private String queueName;
    private String message;
    private String messageFile;
    private List<NameValueProperty> properties = new LinkedList<>();
}
