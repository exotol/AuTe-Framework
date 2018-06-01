package ru.bsc.test.at.util;

import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.serializer.AnchorGenerator;

/**
 * Created by sdoroshin on 02.11.2017.
 *
 */
public class AutotesterAnchorGenerator implements AnchorGenerator {

    private long lastAnchorId;

    @Override
    public String nextAnchor(Node node) {
        if (node instanceof MappingNode) {
            NodeTuple idNode = ((MappingNode) node).getValue()
                    .stream()
                    .filter(nodeTuple -> nodeTuple.getKeyNode() instanceof ScalarNode)
                    .filter(nodeTuple -> "id".equals(((ScalarNode) nodeTuple.getKeyNode()).getValue()))
                    .findAny()
                    .orElse(null);
            if (idNode != null && idNode.getValueNode() instanceof ScalarNode) {
                String idValue = ((ScalarNode) idNode.getValueNode()).getValue();
                if (idValue != null) {
                    return "objId" + idValue;
                }
            }
        }
        return "id" + (lastAnchorId++);
    }
}
