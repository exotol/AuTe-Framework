package ru.bsc.test.at.executor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "AT_FORM_DATA")
public class FormData {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_FORM_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "FIELD_NAME")
    private String fieldName;
    @Column(name = "FIELD_TYPE", length = 500)
    private String fieldType;
    @Column(name = "VALUE", length = 500)
    private String value;
    @Column(name = "FILE_PATH", length = 500)
    private String filePath;
    @ManyToOne
    @JoinColumn(name = "STEP_ID")
    @JsonBackReference
    private Step step;

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

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    protected FormData copy() {
        FormData formData = new FormData();
        formData.setFieldName(getFieldName());
        formData.setFieldType(getFieldType());
        formData.setFilePath(getFilePath());
        formData.setValue(getValue());
        return formData;
    }
}
