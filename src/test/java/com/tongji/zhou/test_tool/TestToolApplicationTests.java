package com.tongji.zhou.test_tool;

import com.tongji.zhou.test_tool.Controller.WebController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class TestToolApplicationTests {

    @Test
    void contextLoads() throws ClassNotFoundException {
        var tmp=new WebController().getClasses();

    }

}
