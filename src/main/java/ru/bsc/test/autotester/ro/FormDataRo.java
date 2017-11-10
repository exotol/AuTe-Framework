package ru.bsc.test.autotester.ro;

import ru.bsc.test.autotester.dto.AbstractRo;

public class FormDataRo extends AbstractRo {

    private static final long serialVersionUID = -7730693222002977456L;

    private Long id;
    private String fieldName;
    private String fieldType;
    private String value;
    private String filePath;
    private StepRo stepRo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public StepRo getStepRo() {
        return stepRo;
    }

    public void setStepRo(StepRo stepRo) {
        this.stepRo = stepRo;
    }
}
