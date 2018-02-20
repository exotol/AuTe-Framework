package ru.bsc.wiremock.velocity.directive;

import java.io.Writer;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

/**
 * Created by sdoroshin on 14.08.2017.
 *
 */
public class GroovyDirective extends Directive {
    @Override
    public String getName() {
        return "groovy";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String groovyScript = node.jjtGetChild(0).getFirstToken().image;

        Binding binding = new Binding();
        binding.setVariable("context", context);
        new GroovyShell(binding).evaluate(groovyScript);

        return true;
    }
}
