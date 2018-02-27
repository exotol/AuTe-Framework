package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
@Getter
@Setter
public class ProjectRo implements AbstractRo {
    private static final long serialVersionUID = 3953325934454830833L;

    private String code;
    private String name;
    private String beforeScenarioPath;
    private String afterScenarioPath;
    private StandRo stand;
    private Boolean useRandomTestId;
    private String testIdHeaderName;
    private AmqpBrokerRo amqpBroker;
    private List<String> groupList;
}
