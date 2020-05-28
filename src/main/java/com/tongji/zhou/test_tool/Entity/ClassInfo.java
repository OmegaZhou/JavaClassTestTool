package com.tongji.zhou.test_tool.Entity;

import java.util.List;

public class ClassInfo {
    private String class_name;
    private List<String> methods;

    public List<String> getTest_cases() {
        return test_cases;
    }

    public void setTest_cases(List<String> test_cases) {
        this.test_cases = test_cases;
    }

    private List<String> test_cases;
    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }
}
