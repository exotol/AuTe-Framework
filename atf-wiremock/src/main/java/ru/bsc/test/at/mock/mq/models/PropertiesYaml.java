package ru.bsc.test.at.mock.mq.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PropertiesYaml {
    private List<MockMessage> mockMessageList;
}
