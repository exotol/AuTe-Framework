package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 */
@Getter
@Setter
public class MockServiceResponseRo implements AbstractRo {
    private static final long serialVersionUID = -7918346254164488513L;

    private String code;
    private String serviceUrl;
    private String responseBody;
    private String responseBodyFile;
    private Integer httpStatus;
    private String contentType;
    private String userName;
    private String password;
    private String pathFilter;
    private List<HeaderItemRo> headers;

}
