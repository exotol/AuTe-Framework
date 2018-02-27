package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDataRo implements AbstractRo {
    private static final long serialVersionUID = -7730693222002977456L;

    private String fieldName;
    private String fieldType;
    private String value;
    private String filePath;
    private String mimeType;
}
