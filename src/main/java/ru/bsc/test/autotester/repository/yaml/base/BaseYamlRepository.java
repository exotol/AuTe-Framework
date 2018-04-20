package ru.bsc.test.autotester.repository.yaml.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import ru.bsc.test.at.executor.model.*;
import ru.bsc.test.autotester.component.Translator;
import ru.bsc.test.autotester.utils.FileExtensionsUtils;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by smakarov
 * 05.03.2018 13:56
 */
@Slf4j
public abstract class BaseYamlRepository {
    protected static final String MAIN_YML_FILENAME = "main.yml";
    protected static final String SCENARIO_YML_FILENAME = "scenario.yml";
    private static final String FILE_ENCODING = "UTF-8";
    private static final String REQUEST_JSON = "request.json";

    protected final Translator translator;

    public BaseYamlRepository(Translator translator) {
        this.translator = translator;
    }

    protected void saveScenarioToFiles(Scenario scenario, File scenarioFile) throws IOException {
        File scenarioRootDirectory = scenarioFile.getParentFile();
        for (int i = 0; i < scenario.getStepList().size(); i++) {
            int order = i + 1;
            saveStepToFiles(order, scenario.getStepList().get(i), scenarioRootDirectory);
        }
        String scenarioGroup = scenario.getScenarioGroup();
        scenario.setScenarioGroup(null);
        YamlUtils.dumpToFile(scenario, scenarioFile.getAbsolutePath());
        scenario.setScenarioGroup(scenarioGroup);
    }

    protected void loadStepFromFiles(Step step, File scenarioRootDirectory) {
        if (step.getRequestFile() != null && step.getRequest() == null) {
            step.setRequest(readFile(scenarioRootDirectory + "/" + step.getRequestFile()));
        }
        if (step.getExpectedResponseFile() != null && step.getExpectedResponse() == null) {
            step.setExpectedResponse(readFile(scenarioRootDirectory + "/" + step.getExpectedResponseFile()));
        }
        if (step.getMqMessageFile() != null && step.getMqMessage() == null) {
            step.setMqMessage(readFile(scenarioRootDirectory + "/" + step.getMqMessageFile()));
        }

        step.getMockServiceResponseList().forEach(mockServiceResponse -> {
            if (mockServiceResponse.getResponseBodyFile() != null && mockServiceResponse.getResponseBody() == null) {
                mockServiceResponse.setResponseBody(readFile(scenarioRootDirectory + "/" + mockServiceResponse.getResponseBodyFile()));
            }
        });

        step.getExpectedServiceRequests().forEach(expectedServiceRequest -> {
            if (expectedServiceRequest.getExpectedServiceRequestFile() != null && expectedServiceRequest.getExpectedServiceRequest() == null) {
                expectedServiceRequest.setExpectedServiceRequest(readFile(scenarioRootDirectory + "/" + expectedServiceRequest.getExpectedServiceRequestFile()));
            }
        });

        Path mqResponsesPath = getMqResponsesPath(scenarioRootDirectory, step);
        if (Files.exists(mqResponsesPath) && step.getMqMockResponseList() != null) {
            File[] files = mqResponsesPath.toFile().listFiles(File::isFile);
            if (files != null) {
                for (File file : files) {
                    String code = file.getName().split("\\.")[0];
                    step.getMqMockResponseList().stream()
                            .filter(item -> code.equals(item.getCode()))
                            .findAny()
                            .ifPresent(item -> item.setResponseBody(readFile(file.toString())));
                }
            }
        }

        Path mqRequestsPath = getMqRequestsPath(scenarioRootDirectory, step);
        if (Files.exists(mqRequestsPath) && step.getExpectedMqRequestList() != null) {
            File[] files = mqRequestsPath.toFile().listFiles(File::isFile);
            if (files != null) {
                for (File file : files) {
                    String code = file.getName().split("\\.")[0];
                    step.getExpectedMqRequestList().stream()
                            .filter(item -> code.equals(item.getCode()))
                            .findAny()
                            .ifPresent(item -> item.setRequestBody(readFile(file.toString())));
                }
            }
        }

        /*
            Данный блок необходим для сохранения информации о sql из старой версии модели
            TODO: удалить этот блок после окончательного прекращения поддержки старого формата
         */
        if (StringUtils.isNotEmpty(step.getSql())) {
            SqlData sqlData = new SqlData();
            sqlData.setSql(step.getSql());
            step.setSql(null);
            if (StringUtils.isNotEmpty(step.getSqlSavedParameter())) {
                sqlData.setSqlSavedParameter(step.getSqlSavedParameter());
                step.setSqlSavedParameter(null);
            }
            step.getSqlDataList().add(sqlData);
        }
    }

    protected String scenarioPath(Scenario scenario) {
        String result = "";
        if (scenario != null) {
            if (scenario.getScenarioGroup() != null) {
                result += scenario.getScenarioGroup() + "/";
            }
            if (scenario.getCode() == null) {
                // TODO Проверять, существует ли такая директория
                scenario.setCode(translator.translate(scenario.getName()));
            }
            result += scenario.getCode() + "/";
        } else {
            result = "0/";
        }
        return result;
    }

    private void saveStepToFiles(int order, Step step, File scenarioRootDirectory) throws IOException {
        step.setCode(generateCodeForStep(order, step));
        if (step.getRequest() != null) {
            try {
                if (step.getRequestFile() == null) {
                    step.setRequestFile(stepPath(step) + REQUEST_JSON);
                }
                File file = new File(scenarioRootDirectory + "/" + step.getRequestFile());
                FileUtils.writeStringToFile(file, step.getRequest(), FILE_ENCODING);
                step.setRequest(null);
            } catch (IOException e) {
                log.error("Save file " + scenarioRootDirectory + "/" + step.getRequestFile(), e);
            }
        } else {
            step.setRequestFile(null);
        }

        if (step.getExpectedResponse() != null) {
            try {
                if (step.getExpectedResponseFile() == null) {
                    step.setExpectedResponseFile(stepPath(step) + stepExpectedResponseFile(step));
                }
                File file = new File(scenarioRootDirectory + "/" + step.getExpectedResponseFile());
                FileUtils.writeStringToFile(file, step.getExpectedResponse(), FILE_ENCODING);
                step.setExpectedResponse(null);
            } catch (IOException e) {
                log.error("Save file " + scenarioRootDirectory + "/" + step.getExpectedResponseFile(), e);
            }
        } else {
            step.setExpectedResponseFile(null);
        }

        if (step.getMqMessage() != null) {
            try {
                if (step.getMqMessageFile() == null) {
                    step.setMqMessageFile(stepPath(step) + stepMqMessageFile(step));
                }
                File file = new File(scenarioRootDirectory + "/" + step.getMqMessageFile());
                FileUtils.writeStringToFile(file, step.getMqMessage(), FILE_ENCODING);
                step.setMqMessage(null);
            } catch (IOException e) {
                log.error("Save file " + scenarioRootDirectory + "/" + step.getMqMessageFile(), e);
            }
        } else {
            step.setMqMessageFile(null);
        }

        step.getMockServiceResponseList().forEach(mockServiceResponse -> {
            if (mockServiceResponse.getResponseBody() != null) {
                try {
                    if (mockServiceResponse.getResponseBodyFile() == null) {
                        mockServiceResponse.setResponseBodyFile(stepPath(step) + mockResponseBodyFile(mockServiceResponse));
                    }
                    File file = new File(scenarioRootDirectory + "/" + mockServiceResponse.getResponseBodyFile());
                    FileUtils.writeStringToFile(file, mockServiceResponse.getResponseBody(), FILE_ENCODING);
                    mockServiceResponse.setResponseBody(null);
                } catch (IOException e) {
                    log.error("Save file " + scenarioRootDirectory + "/" + mockServiceResponse.getResponseBodyFile(), e);
                }
            } else {
                mockServiceResponse.setResponseBodyFile(null);
            }
        });

        step.getExpectedServiceRequests().forEach(expectedServiceRequest -> {
            if (expectedServiceRequest.getExpectedServiceRequest() != null) {
                try {
                    if (expectedServiceRequest.getExpectedServiceRequestFile() == null) {
                        expectedServiceRequest.setExpectedServiceRequestFile(stepPath(step) + expectedServiceRequestFile(expectedServiceRequest));
                    }
                    File file = new File(scenarioRootDirectory + "/" + expectedServiceRequest.getExpectedServiceRequestFile());
                    FileUtils.writeStringToFile(
                            file,
                            expectedServiceRequest.getExpectedServiceRequest(),
                            FILE_ENCODING
                    );
                    expectedServiceRequest.setExpectedServiceRequest(null);
                } catch (IOException e) {
                    log.error("Save file " + scenarioRootDirectory + "/" + expectedServiceRequest.getExpectedServiceRequestFile(), e);
                }
            } else {
                expectedServiceRequest.setExpectedServiceRequest(null);
            }
        });

        saveItemsToFiles(
                getMqResponsesPath(scenarioRootDirectory, step),
                step.getMqMockResponseList(),
                MqMockResponse::getResponseBody,
                MqMockResponse::setResponseBody
        );

        saveItemsToFiles(
                getMqRequestsPath(scenarioRootDirectory, step),
                step.getExpectedMqRequestList(),
                ExpectedMqRequest::getRequestBody,
                ExpectedMqRequest::setRequestBody
        );
    }

    private String generateCodeForStep(int order, Step step) {
        String result = String.valueOf(order);
        if (StringUtils.isNotEmpty(step.getStepComment())) {
            result += "-" + translator.translate(step.getStepComment());
        }
        return result;
    }

    private <T extends CodeAccessible> void saveItemsToFiles(
            Path dataPath,
            List<T> items,
            Function<T, String> getter,
            BiConsumer<T, String> setter
    ) throws IOException {
        if (items == null) {
            return;
        }
        for (T item : items) {
            String data = getter.apply(item);
            if (StringUtils.isEmpty(data)) {
                continue;
            }
            if (StringUtils.isEmpty(item.getCode())) {
                item.generateCode();
            }
            setter.accept(item, null);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
            }
            File file = Paths.get(dataPath.toString(), item.getCode() + "." + FileExtensionsUtils.extensionByContent(data)).toFile();
            FileUtils.writeStringToFile(file, data, FILE_ENCODING);
        }
    }

    private Path getMqRequestsPath(File scenarioRootDirectory, Step step) {
        return Paths.get(scenarioRootDirectory.toString(), stepPath(step), "mq-requests");
    }

    private Path getMqResponsesPath(File scenarioRootDirectory, Step step) {
        return Paths.get(scenarioRootDirectory.toString(), stepPath(step), "mq-responses");
    }

    private String readFile(String path) {
        try {
            File file = new File(path);
            return FileUtils.readFileToString(file, FILE_ENCODING);
        } catch (IOException e) {
            log.error("Reading file {}", path, e);
        }
        return null;
    }

    private String stepPath(Step step) {
        return "steps/" + step.getCode() + "/";
    }

    private String stepExpectedResponseFile(Step step) {
        return "expected-response." + FileExtensionsUtils.extensionByContent(step.getExpectedResponse());
    }

    private String stepMqMessageFile(Step step) {
        return "mq-message." + FileExtensionsUtils.extensionByContent(step.getMqMessage());
    }

    private String mockResponseBodyFile(MockServiceResponse mockServiceResponse) {
        if (mockServiceResponse.getCode() == null) {
            mockServiceResponse.setCode(UUID.randomUUID().toString());
        }
        return "mock-response-" + mockServiceResponse.getCode() + "." + FileExtensionsUtils.extensionByContent(mockServiceResponse.getResponseBody());
    }

    private String expectedServiceRequestFile(ExpectedServiceRequest expectedServiceRequest) {
        if (expectedServiceRequest.getCode() == null) {
            expectedServiceRequest.setCode(UUID.randomUUID().toString());
        }
        return "expected-service-request-" + expectedServiceRequest.getCode() + "." + FileExtensionsUtils.extensionByContent(
                expectedServiceRequest.getExpectedServiceRequest());
    }

    protected Scenario loadScenarioFromFiles(File scenarioDirectory, String group, boolean fetchSteps) throws IOException {
        File scenarioFile = new File(scenarioDirectory, SCENARIO_YML_FILENAME);
        if (scenarioFile.exists()) {
            Scenario scenario = YamlUtils.loadAs(scenarioFile, Scenario.class);
            scenario.setCode(scenarioFile.getParentFile().getName());
            scenario.setScenarioGroup(group);
            File scenarioRootDirectory = scenarioFile.getParentFile();
            if (fetchSteps) {
                scenario.getStepList().forEach(step -> loadStepFromFiles(step, scenarioRootDirectory));
            } else {
                scenario.getStepList().clear();
            }
            return scenario;
        } else {
            Scenario scenario = new Scenario();
            scenario.setScenarioGroup(group);
            return  scenario;
        }

    }
}
