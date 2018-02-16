package ru.bsc.test.at.executor.helper;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DifferenceEvaluator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rmalyshev date: 30.11.12
 *
 */
class IgnoreTagsDifferenceEvaluator implements DifferenceEvaluator {

    private Set<String> ignoredTags = new HashSet<>();

    IgnoreTagsDifferenceEvaluator(Set<String> ignoredTags) {
        if (ignoredTags != null)
            this.ignoredTags = ignoredTags;
    }

    private boolean isXSIType(Node node) {
        return node.getNodeType() == Node.ATTRIBUTE_NODE &&
                node.getLocalName().compareTo("type") == 0 &&
                Objects.equals(node.getNamespaceURI(), "http://www.w3.org/2001/XMLSchema-instance");
    }

    private String getNameSpaceFromPrefix(Node node) {
        final int beginIndex = node.getNodeValue().indexOf(':');
        if (beginIndex == -1) {
            return "";
        }
        return node.lookupNamespaceURI(node.getNodeValue().substring(0, beginIndex));
    }

    private String getNameWithoutPrefix(Node controlNode) {
        final int beginIndex = controlNode.getNodeValue().indexOf(':');
        if (beginIndex == -1) {
            return controlNode.getNodeValue();
        }
        return controlNode.getNodeValue().substring(beginIndex);
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) {
            return outcome;
        }
        if (outcome == ComparisonResult.DIFFERENT) {
            if (ComparisonType.ATTR_VALUE.equals(comparison.getType()) ||
                ComparisonType.ATTR_NAME_LOOKUP.equals(comparison.getType())) {
                String parentNodeName =
                    ((Attr) comparison.getControlDetails().getTarget()).getOwnerElement().getLocalName();
                if (ignoredTags.contains(parentNodeName)) {
                    return ComparisonResult.EQUAL;
                }
            }

            switch (comparison.getType()) {
                case NAMESPACE_PREFIX:
                case NAMESPACE_URI:
                case NO_NAMESPACE_SCHEMA_LOCATION:
                case SCHEMA_LOCATION:
                    return ComparisonResult.EQUAL;
                default:
                    break;
            }
        }

        final Node controlNode = comparison.getControlDetails().getTarget();
        final Node testNode = comparison.getTestDetails().getTarget();

        if (comparison.getType() == ComparisonType.ATTR_VALUE && isXSIType(controlNode) && isXSIType(testNode)) {
            if (getNameSpaceFromPrefix(controlNode).compareTo(getNameSpaceFromPrefix(testNode)) != 0) {
                return ComparisonResult.DIFFERENT;
            }

            String withoutPrefixControl = getNameWithoutPrefix(controlNode);
            String withoutPrefixTest = getNameWithoutPrefix(testNode);

            if (withoutPrefixControl.compareTo(withoutPrefixTest) == 0) {
                return ComparisonResult.EQUAL;
            }
        }

        if(controlNode == null || ignoredTags == null) {
            return outcome;
        }

        Pattern pattern = Pattern.compile("\\s*(\\w+)\\s*\\(\\s*(\\w+)\\s*=\\s*([\\w#]+)\\s*\\)");
        for (String ignoredTag : this.ignoredTags) {
            if(!ignoredTag.matches("\\s*\\w+\\s*\\(\\s*\\w+\\s*=\\s*[\\w#]+\\s*\\)")) {
                if(!(controlNode.getParentNode() instanceof Element)) {
                    continue;
                }

                Element element = (Element) controlNode.getParentNode();
                if (ignoredTag.equals(element.getTagName())) {
                    return ComparisonResult.EQUAL;
                }
                continue;
            }

            Matcher matcher = pattern.matcher(ignoredTag);
            if(!matcher.find()) {
                continue;
            }

            String parent = matcher.group(1);
            String childKey = matcher.group(2);
            String childValue = matcher.group(3);
            if(!(controlNode.getParentNode() instanceof Element)) {
                continue;
            }

            Element controlElement = (Element) controlNode.getParentNode();
            if(!(controlElement.getParentNode() instanceof Element)) {
                continue;
            }

            Element parentElement = (Element) controlElement.getParentNode();
            if(!parentElement.getTagName().equals(parent) || !parentElement.hasChildNodes()) {
                continue;
            }

            NodeList childNodes = parentElement.getElementsByTagName(childKey);
            if(childNodes.getLength() <= 0) {
                continue;
            }
            for (int i = 0; i < childNodes.getLength(); i++) {
                if(!(childNodes.item(i) instanceof Element) ||
                    !((Element) childNodes.item(i)).getTagName().equals(childKey)) {
                    continue;
                }

                Element childElement = (Element) childNodes.item(i);
                if (childElement.getTagName().equals(childKey) &&
                    childElement.getTextContent().equals(childValue)) {
                    return ComparisonResult.EQUAL;
                }
            }

        }
        return outcome;
    }
}
