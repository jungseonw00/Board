package com.study.jasypt;

import com.study.JasyptConfig.JasyptTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JasyptApplicationTests {

    @Autowired
    private JasyptTest jasyptTest;

    @Test
    void jasyptTest() {
        System.out.println("jasyptTest.getPropertiesTestValue() = " + jasyptTest.getPropertiesTestValue());
    }
}
