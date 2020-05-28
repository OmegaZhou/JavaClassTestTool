package com.tongji.zhou.test_tool.Config;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class FileConfig {
    public static String JavaFilePath;

    static {
        try {
            JavaFilePath = ResourceUtils.getURL("classpath:").getPath()+"TestClass/";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String ClassPath="TestClass.";
}
