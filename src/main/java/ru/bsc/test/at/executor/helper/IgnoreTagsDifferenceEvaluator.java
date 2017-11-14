package ru.bsc.test.at.executor.helper;

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
        final int beginIndex = node.getNodeValue().indexOf(":");
        if (beginIndex == -1) {
            return "";
        }
        return node.lookupNamespaceURI(node.getNodeValue().substring(0, beginIndex));
    }

    private String getNameWithoutPrefix(Node controlNode) {
        final int beginIndex = controlNode.getNodeValue().indexOf(":");
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
            String parentNodeName = comparison.getControlDetails().getTarget().getOwnerDocument().getDocumentElement().getLocalName();
            if (ignoredTags.contains(parentNodeName)) {
                outcome = ComparisonResult.EQUAL;
                return outcome;
            }
            switch (comparison.getType()) {
                case NAMESPACE_PREFIX:
                case NAMESPACE_URI:
                case NO_NAMESPACE_SCHEMA_LOCATION:
                case SCHEMA_LOCATION:
                    outcome = ComparisonResult.EQUAL;
                    return outcome;
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

        if (controlNode != null)
            if (ignoredTags != null) {
                for (String ignoredTag : this.ignoredTags) {
                    if (ignoredTag.matches("\\s*\\w+\\s*\\(\\s*\\w+\\s*=\\s*[\\w#]+\\s*\\)")) {
                        Pattern p = Pattern.compile("\\s*(\\w+)\\s*\\(\\s*(\\w+)\\s*=\\s*([\\w#]+)\\s*\\)");
                        Matcher m = p.matcher(ignoredTag);
                        if (m.find()) {
                            String parent = m.group(1);
                            String childKey = m.group(2);
                            String childValue = m.group(3);

                            if (controlNode.getParentNode() instanceof Element) {
                                Element controlElement = (Element) controlNode.getParentNode();
                                if (controlElement.getParentNode() instanceof Element) {
                                    Element parentElement = (Element) controlElement.getParentNode();

                                    if (parentElement.getTagName().equals(parent)) {
                                        if (parentElement.hasChildNodes()) {
                                            NodeList childNodes = parentElement.getElementsByTagName(childKey);
                                            if (childNodes.getLength() > 0) {
                                                for (int i = 0; i < childNodes.getLength(); i++) {
                                                    if (childNodes.item(i) instanceof Element && ((Element) childNodes.item(i)).getTagName().equals(childKey)) {
                                                        Element childElement = (Element) childNodes.item(i);

                                                        if (childElement.getTagName().equals(childKey) && childElement.getTextContent().equals(childValue))
                                                            return ComparisonResult.EQUAL;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (controlNode.getParentNode() instanceof Element) {
                            Element element = (Element) controlNode.getParentNode();
                            if (ignoredTag.equals(element.getTagName()))
                                return ComparisonResult.EQUAL;
                        }
                    }
                }
            }
        return outcome;
    }
}