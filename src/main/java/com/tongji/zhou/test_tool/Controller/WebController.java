package com.tongji.zhou.test_tool.Controller;


import com.tongji.zhou.test_tool.Config.FileConfig;
import com.tongji.zhou.test_tool.Entity.ClassInfo;
import com.tongji.zhou.test_tool.Entity.TestResult;
import com.tongji.zhou.test_tool.Tool.TestServices;
import com.tongji.zhou.test_tool.Tool.Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebController {

    @GetMapping("GetClasses")
    @ResponseBody
    public List<ClassInfo> getClasses() throws ClassNotFoundException {
        File file = new File(FileConfig.JavaFilePath);
        var classes = file.listFiles();
        List<ClassInfo> results = new ArrayList<>();
        for (var item : classes) {
            ClassInfo info = new ClassInfo();
            info.setClass_name(item.getName());
            var methods = TestServices.getMethods(FileConfig.ClassPath + item.getName() + "." + item.getName());
            info.setMethods(methods);
            info.setTest_cases(new ArrayList<>());
            for (var method : methods) {
                var files = item.list();
                boolean flag = true;
                for (var name : files) {
                    if (name.equals(method + ".csv")) {
                        flag = false;
                        info.getTest_cases().add(name);
                    }
                }
                if (flag) {
                    info.getTest_cases().add(null);
                }
            }
            results.add(info);
        }
        return results;
    }

    @PostMapping("uploadClass")
    @ResponseBody
    public String addClass(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        BufferedOutputStream stream = null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String fileName = file.getOriginalFilename();
                String class_name = fileName.split("\\.")[0];
                File class_dir = new File(FileConfig.JavaFilePath + class_name);
                if (!class_dir.exists()) {
                    class_dir.mkdir();
                }
                stream = new BufferedOutputStream(new FileOutputStream(FileConfig.JavaFilePath + class_name + "/" + fileName));
                String package_str = "package TestClass." + class_name + ";\n";
                stream.write(package_str.getBytes());
                stream.write(bytes);
                stream.close();
                TestServices.addClass(FileConfig.JavaFilePath + class_name + "/", class_name);
            } catch (Exception e) {
                return "You failed to upload  => " + e.getMessage();
            }
        } else {
            return "You failed to upload  because the file was empty.";
        }
        return "success";
    }

    @PostMapping("uploadTestCase")
    @ResponseBody
    public String addTestCase(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String class_name = request.getParameter("class_name");
        String method_name = request.getParameter("method_name");
        BufferedOutputStream stream = null;

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                stream = new BufferedOutputStream(
                        new FileOutputStream(FileConfig.JavaFilePath + class_name + "/" + method_name + ".csv"));
                stream.write(bytes);
                stream.close();

            } catch (Exception e) {
                return "You failed to upload  => " + e.getMessage();
            }
        } else {
            return "You failed to upload  because the file was empty.";
        }
        return "success";
    }

    @GetMapping("removeClass")
    @ResponseBody
    public String removeClass(@RequestParam("class_name") String class_name) {
        try {
            Utils.delete_dir(FileConfig.JavaFilePath + class_name);
        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }

    @GetMapping("removeMethod")
    @ResponseBody
    public String removeClass(
            @RequestParam("class_name") String class_name, @RequestParam("method_name") String method_name) {
        try {
            File file = new File(FileConfig.JavaFilePath + class_name + "/" + method_name + ".csv");
            Files.delete(Paths.get(file.toURI()));
        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }

    @GetMapping("test")
    @ResponseBody
    public List<TestResult> test(
            @RequestParam("class_name") String class_name, @RequestParam("method_name") String method_name) {
        return TestServices.testClass(FileConfig.ClassPath + class_name+"."+class_name,
                FileConfig.JavaFilePath + class_name + "/" + method_name + ".csv", method_name);
    }
}
