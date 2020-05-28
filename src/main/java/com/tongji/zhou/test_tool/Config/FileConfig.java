package com.tongji.zhou.test_tool.Config;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileConfig {
    public static String JavaFilePath;

    static {
        try {
            JavaFilePath = ResourceUtils.getURL("classpath:").getPath()+"TestClass/";
            File dir=new File(JavaFilePath);
            if(!dir.exists()){
                dir.mkdir();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String ClassPath="TestClass.";
}
