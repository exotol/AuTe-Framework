package ru.bsc.test.at.executor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by smakarov
 * 20.04.2018 12:10
 */
@Getter
@Setter
public class MqMessage implements Serializable, AbstractModel {
    private static final long serialVersionUID = -2284237307005166339L;

    private String queueName;
    private String message;
    private String messageFile;
    private List<NameValueProperty> properties = new LinkedList<>();

    public MqMessage copy() {
        MqMessage copy = new MqMessage();
        copy.setQueueName(getQueueName());
        copy.setMessage(getMessage());
        if (getProperties() != null) {
            copy.setProperties(new LinkedList<>());
            properties.forEach(p -> copy.getProperties().add(p.copy()));
        }
        return copy;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return StringUtils.isEmpty(this.queueName) || StringUtils.isEmpty(this.message);
    }
}
