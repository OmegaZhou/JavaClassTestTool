package com.tongji.zhou.test_tool.Entity;

import java.util.List;

public class TestResult {
    private List<Object> parameters;
    private String right_result;
    private String real_result;
    private Boolean result;

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getRight_result() {
        return right_result;
    }

    public void setRight_result(String right_result) {
        this.right_result = right_result;
    }

    public String getReal_result() {
        return real_result;
    }

    public void setReal_result(String real_result) {
        this.real_result = real_result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
