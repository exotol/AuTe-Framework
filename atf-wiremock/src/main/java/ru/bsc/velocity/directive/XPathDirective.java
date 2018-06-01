package ru.bsc.velocity.directive;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

/**
 * Created by sdoroshin on 26.07.2017.
 *
 */
public class XPathDirective extends Directive {

    @Override
    public String getName() {
        return "xpath";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node ) throws IOException {
        String variableName = node.jjtGetChild(0).getFirstToken().image.substring(1);
        String xmlDocument = String.valueOf(node.jjtGetChild(1).value(context));
        String xpathString = String.valueOf(node.jjtGetChild(2).value(context));

        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            String evaluatedValue = xpath.evaluate(xpathString, new InputSource(new StringReader(xmlDocument)));
            context.put(variableName, evaluatedValue);
        } catch (XPathExpressionException e) {
            throw new IOException("cannot evaluate xpath: " + e.getMessage(), e);
        }
        return true;
    }
}
