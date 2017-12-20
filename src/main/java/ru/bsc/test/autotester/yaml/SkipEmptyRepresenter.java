package ru.bsc.test.autotester.yaml;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by sdoroshin on 03.11.2017.
 *
 */

class SkipEmptyRepresenter extends Representer {
    @Override
    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
        // if value of property is null, ignore it.
        if ((propertyValue == null)
                || (propertyValue instanceof List && ((List) propertyValue).isEmpty())
                || (propertyValue instanceof Boolean && !((Boolean) propertyValue) && !Objects.equals(property.getName(), "failed"))
                || (propertyValue instanceof Map  && ((Map)  propertyValue).isEmpty())) {
            return null;
        } else {
            return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
        }
    }
}
