package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 15.09.2017.
 *
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ProjectRoMapper {

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "beforeScenarioId", source = "beforeScenario.id"),
            @Mapping(target = "afterScenarioId", source = "afterScenario.id"),
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "standList", source = "standList"),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "useRandomTestId", source = "useRandomTestId"),
            @Mapping(target = "testIdHeaderName", source = "testIdHeaderName")
    })
    abstract public ProjectRo projectToProjectRo(Project project);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "code", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "sort", ignore = true),
            @Mapping(target = "beforeScenario", ignore = true),
            @Mapping(target = "afterScenario", ignore = true),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "standList", ignore = true),

            @Mapping(target = "scenarioList", ignore = true)
    })
    abstract void updateProjectFromRo(ProjectRo projectRo, @MappingTarget Project project);

    public void updateProject(ProjectRo projectRo, @MappingTarget Project project) {
        updateProjectFromRo(projectRo, project);

        project.setBeforeScenario(
                projectRo.getBeforeScenarioId() == null ? null :
                        project.getScenarioList().stream()
                                .filter(scenario -> Objects.equals(scenario.getId(), projectRo.getBeforeScenarioId()))
                                .findAny().orElse(null)
        );

        project.setAfterScenario(
                projectRo.getAfterScenarioId() == null ? null :
                        project.getScenarioList().stream()
                                .filter(scenario -> Objects.equals(scenario.getId(), projectRo.getAfterScenarioId()))
                                .findAny().orElse(null)
        );

        project.setStand(
                projectRo.getStand() == null || projectRo.getStand().getId() == null ? null :
                        project.getStandList().stream()
                                .filter(stand -> Objects.equals(stand.getId(), projectRo.getStand().getId()))
                                .findAny().orElse(null)
        );

        List<Stand> projectStandList = new LinkedList<>(project.getStandList());
        project.getStandList().clear();
        project.getStandList().addAll(projectRo.getStandList().stream()
                .map(standRo -> projectStandList.stream()
                        .filter(projectStand -> Objects.equals(projectStand.getId(), standRo.getId()))
                        .map(stand -> updateStandFromRo(standRo, stand))
                        .findAny()
                        .orElseGet(() -> {
                            Stand newStand = new Stand();
                            updateStandFromRo(standRo, newStand);
                            return newStand;
                        }))
                .collect(Collectors.toList())
        );
    }

    abstract public List<ProjectRo> convertProjectListToProjectRoList(List<Project> list);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "serviceUrl", source = "serviceUrl")
    })
    abstract StandRo standToStandRo(Stand stand);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "sort", ignore = true),
            @Mapping(target = "code", ignore = true),
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "dbUrl", source = "dbUrl"),
            @Mapping(target = "dbUser", source = "dbUser"),
            @Mapping(target = "dbPassword", source = "dbPassword"),
            @Mapping(target = "wireMockUrl", source = "wireMockUrl")
    })
    abstract Stand updateStandFromRo(StandRo standRo, @MappingTarget Stand stand);

    @Mappings({
            @Mapping(target = "id", source = "id"),
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
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "sort", ignore = true),
            @Mapping(target = "code", ignore = true),
            @Mapping(target = "mqService", source = "mqService"),
            @Mapping(target = "host", source = "host"),
            @Mapping(target = "port", source = "port"),
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "password", source = "password"),

    })
    public abstract AmqpBroker updateAmqpBrokerFromRo(AmqpBrokerRo amqpBrokerRo, @MappingTarget AmqpBroker amqpBroker);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "mqService", source = "mqService"),
            @Mapping(target = "host", source = "host"),
            @Mapping(target = "port", source = "port"),
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "password", source = "password")
    })
    public abstract AmqpBrokerRo convertAmqpBrokerToRo(AmqpBroker amqpBroker);
}
