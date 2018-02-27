package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportProjectRo implements AbstractRo {
    private static final long serialVersionUID = -3629008469971044019L;

    private String projectCode;
    private String yamlContent;
}
