package com.tongji.zhou.test_tool.Tool;

import com.tongji.zhou.test_tool.Config.FileConfig;
import com.tongji.zhou.test_tool.Entity.TestResult;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class TestServices {
    private static Object convert_value(String str, Class type) {
        if (type == int.class || type == Integer.class) {
            return Integer.valueOf(str);
        }
        if (type == long.class || type == Long.class) {
            return Long.valueOf(str);
        }
        if (type == float.class || type == Float.class) {
            return Float.valueOf(str);
        }
        if (type == double.class || type == Double.class) {
            return Double.valueOf(str);
        }
        return str;
    }

    public static Map<String, List<String>> getMethods(String class_name) {
        List<String> result = new ArrayList<>();
        List<String> origin = new ArrayList<>();
        try {
            Class clazz = Class.forName(class_name);
            var methods = clazz.getMethods();
            for (var item : methods) {
                if (item.getDeclaringClass() == clazz) {
                    var name = new StringBuffer();
                    if (Modifier.isStatic(item.getModifiers())) {
                        name.append("static ");
                    }
                    name.append(item.getReturnType().getSimpleName());
                    name.append(" ");
                    name.append(item.getName());
                    name.append("(");
                    var paras = item.getParameterTypes();
                    for (int i = 0; i < paras.length; ++i) {
                        if (i != 0) {
                            name.append(", ");
                        }
                        name.append(paras[i].getSimpleName());
                    }
                    name.append(")");
                    result.add(name.toString());
                    origin.add(item.getName());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Map<String, List<String>> map = new HashMap<>();
        map.put("simple_name", origin);
        map.put("name", result);
        return map;

    }

    public static List<TestResult> testClass(String class_name, String test_case_path, String method_name) {
        List<TestResult> results = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(test_case_path));
            Class clazz = Class.forName(class_name);
            var methods = clazz.getMethods();
            Method method = null;
            for (var item : methods) {
                if (item.getName().equals(method_name)) {
                    method = item;
                }
            }
            while (scanner.hasNext()) {
                var para_num = method.getParameterCount();
                List<Object> parameters = new ArrayList<>();
                for (int i = 0; i < para_num; ++i) {
                    parameters.add(convert_value(scanner.next(), method.getParameterTypes()[i]));
                }
                Object return_v;
                String true_result = scanner.next();

                if (Modifier.isStatic(method.getModifiers())) {
                    return_v = method.invoke(null, parameters.toArray());
                } else {
                    return_v = method.invoke(clazz.getDeclaredConstructor().newInstance());
                }
                TestResult testResult = new TestResult();
                testResult.setParameters(parameters);
                testResult.setResult(true_result.equals(return_v.toString()));
                testResult.setReal_result(return_v.toString());
                testResult.setRight_result(true_result);
                results.add(testResult);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    static public Boolean addClass(String path, String class_name) {
        if (System.getProperty("os.name").toLowerCase().startsWith("wind")) {
            path = path.replaceFirst("/", "");
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        Iterable<String> options = Arrays.asList("-d", path + "../../");
        StandardJavaFileManager fileManager = compiler
                .getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager
                .getJavaFileObjectsFromFiles(Arrays.asList(new File(
                        path + class_name + ".java")));

        return compiler.getTask(null, fileManager, null, options,
                null, compilationUnits).call();
    }
}