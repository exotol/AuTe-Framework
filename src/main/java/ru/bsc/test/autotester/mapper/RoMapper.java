package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioGroup;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.ro.ScenarioGroupRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StandRo;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "beforeScenario", source = "beforeScenario"),
            @Mapping(target = "afterScenario", source = "afterScenario"),
            @Mapping(target = "projectCode", source = "projectCode"),
            @Mapping(target = "scenarioGroups", source = "scenarioGroups"),
            @Mapping(target = "standList", source = "standList"),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "useRandomTestId", source = "useRandomTestId"),
            @Mapping(target = "testIdHeaderName", source = "testIdHeaderName")
    })
    ProjectRo projectToProjectRo(Project project);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "projectCode", source = "projectCode"),
            @Mapping(target = "beforeScenario", source = "beforeScenario"),
            @Mapping(target = "afterScenario", source = "afterScenario"),
            @Mapping(target = "scenarioGroups", source = "scenarioGroups"),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "standList", source = "standList"),

            @Mapping(target = "scenarios", ignore = true)
    })
    void updateProjectFromRo(ProjectRo projectRo, @MappingTarget Project project);

    List<ProjectRo> convertProjectListToProjectRoList(List<Project> list);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "scenarioGroup", source = "scenarioGroup"),
            @Mapping(target = "lastRunAt", source = "lastRunAt"),
            @Mapping(target = "lastRunFailures", source = "lastRunFailures"),
            @Mapping(target = "beforeScenario", source = "beforeScenario"),
            @Mapping(target = "afterScenario", source = "afterScenario"),
            @Mapping(target = "steps", ignore = true),
            @Mapping(target = "stepResults", ignore = true),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore")
    })
    ScenarioRo scenarioToScenarioRo(Scenario scenario);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "scenarioGroup", source = "scenarioGroup"),
            @Mapping(target = "lastRunAt", source = "lastRunAt"),
            @Mapping(target = "lastRunFailures", source = "lastRunFailures"),
            @Mapping(target = "beforeScenario", source = "beforeScenario"),
            @Mapping(target = "afterScenario", source = "afterScenario"),
            @Mapping(target = "steps", ignore = true),
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "stepResults", ignore = true),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore")
    })
    Scenario scenarioRoToScenario(ScenarioRo scenarioRo);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name")
    })
    ScenarioGroupRo scenarioGroupToScenarioGroupRo(ScenarioGroup scenarioGroup);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "project", ignore = true)
    })
    ScenarioGroup scenarioGroupRoToScenarioGroup(ScenarioGroupRo scenarioGroupRo);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "serviceUrl", source = "serviceUrl")
    })
    StandRo standToStandRo(Stand stand);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "dbUrl", source = "dbUrl"),
            @Mapping(target = "dbUser", source = "dbUser"),
            @Mapping(target = "dbPassword", source = "dbPassword"),
            @Mapping(target = "wireMockUrl", source = "wireMockUrl")
    })
    Stand standRoToStand(StandRo standRo);
}
