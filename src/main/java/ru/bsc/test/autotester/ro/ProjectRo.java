package ru.bsc.test.autotester.ro;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class ProjectRo extends AbstractRo {

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeforeScenarioPath() {
        return beforeScenarioPath;
    }

    public void setBeforeScenarioPath(String beforeScenarioPath) {
        this.beforeScenarioPath = beforeScenarioPath;
    }

    public String getAfterScenarioPath() {
        return afterScenarioPath;
    }

    public void setAfterScenarioPath(String afterScenarioPath) {
        this.afterScenarioPath = afterScenarioPath;
    }

    public StandRo getStand() {
        return stand;
    }

    public void setStand(StandRo stand) {
        this.stand = stand;
    }

    public Boolean getUseRandomTestId() {
        return useRandomTestId;
    }

    public void setUseRandomTestId(Boolean useRandomTestId) {
        this.useRandomTestId = useRandomTestId;
    }

    public String getTestIdHeaderName() {
        return testIdHeaderName;
    }

    public void setTestIdHeaderName(String testIdHeaderName) {
        this.testIdHeaderName = testIdHeaderName;
    }

    public AmqpBrokerRo getAmqpBroker() {
        return amqpBroker;
    }

    public void setAmqpBroker(AmqpBrokerRo amqpBroker) {
        this.amqpBroker = amqpBroker;
    }

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }
}
