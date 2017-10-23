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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 15.09.2017.
 *
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ProjectRoMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "beforeScenarioId", source = "beforeScenario.id"),
            @Mapping(target = "afterScenarioId", source = "afterScenario.id"),
            @Mapping(target = "projectCode", source = "projectCode"),
            @Mapping(target = "scenarioGroups", source = "scenarioGroups"),
            @Mapping(target = "standList", source = "standList"),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "useRandomTestId", source = "useRandomTestId"),
            @Mapping(target = "testIdHeaderName", source = "testIdHeaderName")
    })
    abstract public ProjectRo projectToProjectRo(Project project);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "projectCode", source = "projectCode"),
            @Mapping(target = "beforeScenario", ignore = true),
            @Mapping(target = "afterScenario", ignore = true),
            @Mapping(target = "scenarioGroups", ignore = true),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "standList", ignore = true),

            @Mapping(target = "scenarios", ignore = true)
    })
    abstract void updateProjectFromRo(ProjectRo projectRo, @MappingTarget Project project);

    public void updateProject(ProjectRo projectRo, @MappingTarget Project project) {
        updateProjectFromRo(projectRo, project);

        project.setBeforeScenario(
                projectRo.getBeforeScenarioId() == null ? null :
                        project.getScenarios().stream()
                                .filter(scenario -> Objects.equals(scenario.getId(), projectRo.getBeforeScenarioId()))
                                .findAny().orElseGet(null)
        );

        project.setAfterScenario(
                projectRo.getAfterScenarioId() == null ? null :
                        project.getScenarios().stream()
                                .filter(scenario -> Objects.equals(scenario.getId(), projectRo.getAfterScenarioId()))
                                .findAny().orElseGet(null)
        );

        project.setStand(
                projectRo.getStand() == null || projectRo.getStand().getId() == null ? null :
                        project.getStandList().stream()
                                .filter(stand -> Objects.equals(stand.getId(), projectRo.getStand().getId()))
                                .findAny().orElse(null)
        );

        project.setStandList(projectRo.getStandList().stream()
                .map(standRo -> project.getStandList().stream()
                        .filter(projectStand -> Objects.equals(projectStand.getId(), standRo.getId()))
                        .map(stand -> updateStandFromRo(standRo, stand))
                        .findAny()
                        .orElseGet(() -> {
                            Stand newStand = new Stand();
                            updateStandFromRo(standRo, newStand);
                            newStand.setProject(project);
                            return newStand;
                        }))
                .collect(Collectors.toList())
        );

        project.setScenarioGroups(projectRo.getScenarioGroups().stream()
                .map(scenarioGroupRo -> project.getScenarioGroups().stream()
                        .filter(projectScenarioGroup -> Objects.equals(projectScenarioGroup.getId(), scenarioGroupRo.getId()))
                        .map(scenarioGroup1 -> updateScenarioGroupFromRo(scenarioGroupRo, scenarioGroup1))
                        .findAny().orElseGet(() -> {
                            ScenarioGroup newScenarioGroup = new ScenarioGroup();
                            updateScenarioGroupFromRo(scenarioGroupRo, newScenarioGroup);
                            newScenarioGroup.setProject(project);
                            return newScenarioGroup;
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
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "dbUrl", source = "dbUrl"),
            @Mapping(target = "dbUser", source = "dbUser"),
            @Mapping(target = "dbPassword", source = "dbPassword"),
            @Mapping(target = "wireMockUrl", source = "wireMockUrl")
    })
    abstract Stand updateStandFromRo(StandRo standRo, @MappingTarget Stand stand);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "projectId", source = "project.id"),
            @Mapping(target = "projectName", source = "project.name"),
            @Mapping(target = "projectStand", source = "project.stand"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "scenarioGroup", source = "scenarioGroup"),
            @Mapping(target = "lastRunAt", source = "lastRunAt"),
            @Mapping(target = "lastRunFailures", source = "lastRunFailures"),
            @Mapping(target = "beforeScenarioId", source = "beforeScenario.id"),
            @Mapping(target = "afterScenarioId", source = "afterScenario.id"),
            @Mapping(target = "steps", ignore = true),
            @Mapping(target = "stepResults", ignore = true),
            @Mapping(target = "stand", source = "stand"),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore")
    })
    public abstract ScenarioRo scenarioToScenarioRo(Scenario scenario);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name")
    })
    abstract ScenarioGroupRo scenarioGroupToScenarioGroupRo(ScenarioGroup scenarioGroup);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "project", ignore = true)
    })
    abstract ScenarioGroup updateScenarioGroupFromRo(ScenarioGroupRo scenarioGroupRo, @MappingTarget ScenarioGroup scenarioGroup);

    public abstract List<ScenarioRo> convertScenarioListToScenarioRoList(List<Scenario> scenarioList);
}