package ru.bsc.test.at.util;

import lombok.*;

import java.util.List;

/**
 * Created by smakarov
 * 01.06.2018 16:46
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("WeakerAccess")
public class TestObject {
    private String str;
    private int i;
    private long l;
    private double d;
    private List<String> list;
}
