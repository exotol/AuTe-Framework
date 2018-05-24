package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeaderItem {
    String headerName;
    String headerValue;
    String compareType;

    public  HeaderItem copy() {
        HeaderItem copy = new HeaderItem();
        copy.setCompareType(getCompareType());
        copy.setHeaderValue(getHeaderValue());
        copy.setHeaderName(getHeaderName());
        return copy;
    }
}
