package ru.bsc.test.autotester.report;

import org.apache.commons.io.FileUtils;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SimpleReportGenerator extends AbstractReportGenerator {

    private final static String HTML_HEAD = "<html><head></head><body style='font-size: 12px; font-family: Helvetica,Arial,sans-serif;'>";
    private final static String HTML_FOOTER = "</body></html>";

    @Override
    public void generate(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                throw new FileNotFoundException(directory.getAbsolutePath() + " is not a directory.");
            }
            FileUtils.deleteDirectory(directory);
        }
        if (!directory.mkdir()) {
            throw new IOException(directory.getAbsolutePath() + " not created.");
        }

        StringBuilder scenarioListHtml = new StringBuilder();
        scenarioStepResultMap.forEach((scenario, stepResultList) -> {
            scenarioListHtml.append(generateScenarioHtml(scenario, stepResultList));
        });

        try(PrintWriter out = new PrintWriter(new File(directory, "index.html"))) {
            out.print(htmlTemplate(scenarioListHtml));
        }
    }

    private String htmlTemplate(StringBuilder scenarioHtml) {
        return HTML_HEAD + scenarioListWrapper(scenarioHtml) + HTML_FOOTER;
    }

    private StringBuilder scenarioListWrapper(StringBuilder scenarioHtml) {
        scenarioHtml.insert(0, "<table style='border: 1px solid gray; font-size: 14px;'>");
        scenarioHtml.append("</table>");
        return scenarioHtml;
    }

    private String generateScenarioHtml(Scenario scenario, List<StepResult> stepResultList) {

        StringBuilder stepResultHtml = new StringBuilder();
        stepResultList.forEach(stepResult -> stepResultHtml.append(generateStepResultHtml(scenario, stepResult)));
        stepResultListWrapper(scenario, stepResultHtml);

        long failCount = stepResultList.stream().filter(stepResult -> !StepResult.RESULT_OK.equals(stepResult.getResult())).count();

        String script = "var el = document.getElementById('scenario" + scenario.hashCode() + "'); el.style.display = (el.style.display == 'none') ? 'block' : 'none'; return false;";
        StringBuilder result = new StringBuilder();
        result.append("<tr><td style='background-color: ").append(failCount > 0 ? "#FBDCD1" : "#DAF8DA")
                .append("'><a onclick=\"").append(script).append("\" href='#'>").append(scenario.getName()).append("</a>");

        result.append("<div id='scenario").append(scenario.hashCode()).append("' style='display: none; margin-left: 15px;'>").append(stepResultHtml).append("</div>");
        result.append("</td></tr>");
        return result.toString();
    }

    private void stepResultListWrapper(Scenario scenario, StringBuilder stepResultHtml) {
        stepResultHtml.insert(0, "<a name='" + scenario.hashCode() + "'></a>");
    }

    private String generateStepResultHtml(Scenario scenario, StepResult stepResult) {
        return "<hr/><b>" + stepResult.getRequestUrl() + "</b><pre>" + (stepResult.getDetails() == null ? "" : stepResult.getDetails()) + "</pre>";
    }

}
