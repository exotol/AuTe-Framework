package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioGroup;
import ru.bsc.test.autotester.ro.ScenarioGroupRo;
import ru.bsc.test.autotester.ro.ScenarioRo;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class RoMapper {


}
