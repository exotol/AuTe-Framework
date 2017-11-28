package ru.bsc.test.at.executor.model;

@SuppressWarnings("WeakerAccess")
public class FormData extends AbstractModel {

    private String fieldName;
    private FieldType fieldType;
    private String value;
    private String filePath;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
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

    protected FormData copy() {
        FormData formData = new FormData();
        formData.setFieldName(getFieldName());
        formData.setFieldType(getFieldType());
        formData.setFilePath(getFilePath());
        formData.setValue(getValue());
        return formData;
    }

    @Override
    public String toString() {
        return "FormData{" +
                ", fieldName='" + fieldName + '\'' +
                ", fieldType=" + fieldType +
                ", value='" + value + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
