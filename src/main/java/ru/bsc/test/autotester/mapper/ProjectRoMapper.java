package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.bsc.test.at.executor.model.AmqpBroker;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.autotester.ro.AmqpBrokerRo;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StandRo;

import java.util.List;

/**
 * Created by sdoroshin on 15.09.2017.
 *
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ProjectRoMapper {

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "beforeScenarioPath", source = "beforeScenarioPath"),
            @Mapping(target = "afterScenarioPath", source = "afterScenarioPath"),
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "useRandomTestId", source = "useRandomTestId"),
            @Mapping(target = "testIdHeaderName", source = "testIdHeaderName")
    })
    abstract public ProjectRo projectToProjectRo(Project project);

    @Mappings({
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "beforeScenarioPath", source = "beforeScenarioPath"),
            @Mapping(target = "afterScenarioPath", source = "afterScenarioPath"),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "amqpBroker", source = "amqpBroker"),
            @Mapping(target = "useRandomTestId", source = "useRandomTestId"),
            @Mapping(target = "testIdHeaderName", source = "testIdHeaderName"),
            @Mapping(target = "scenarioList", ignore = true)
    })
    public abstract Project updateProjectFromRo(ProjectRo projectRo);

    abstract public List<ProjectRo> convertProjectListToProjectRoList(List<Project> list);

    @Mappings({
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "dbUrl", source = "dbUrl"),
            @Mapping(target = "dbUser", source = "dbUser"),
            @Mapping(target = "dbPassword", source = "dbPassword"),
            @Mapping(target = "wireMockUrl", source = "wireMockUrl")
    })
    abstract StandRo standToStandRo(Stand stand);

    @Mappings({
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "dbUrl", source = "dbUrl"),
            @Mapping(target = "dbUser", source = "dbUser"),
            @Mapping(target = "dbPassword", source = "dbPassword"),
            @Mapping(target = "wireMockUrl", source = "wireMockUrl")
    })
    abstract Stand updateStandFromRo(StandRo standRo);

    @Mappings({
            @Mapping(target = "projectCode", ignore = true),
            @Mapping(target = "projectName", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "scenarioGroup", source = "scenarioGroup"),
            @Mapping(target = "stepList", ignore = true),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore")
    })
    abstract ScenarioRo scenarioToScenarioRoInner(Scenario scenario);

    public ScenarioRo scenarioToScenarioRo(String projectCode, String projectName, Scenario scenario) {
        ScenarioRo scenarioRo = scenarioToScenarioRoInner(scenario);
        scenarioRo.setProjectCode(projectCode);
        scenarioRo.setProjectName(projectName);
        return scenarioRo;
    }

    public abstract List<ScenarioRo> convertScenarioListToScenarioRoList(List<Scenario> scenarioList);

    @Mappings({
            @Mapping(target = "mqService", source = "mqService"),
            @Mapping(target = "host", source = "host"),
            @Mapping(target = "port", source = "port"),
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "password", source = "password"),

    })
    public abstract AmqpBroker updateAmqpBrokerFromRo(AmqpBrokerRo amqpBrokerRo);

    @Mappings({
            @Mapping(target = "mqService", source = "mqService"),
            @Mapping(target = "host", source = "host"),
            @Mapping(target = "port", source = "port"),
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "password", source = "password")
    })
    public abstract AmqpBrokerRo convertAmqpBrokerToRo(AmqpBroker amqpBroker);
}
