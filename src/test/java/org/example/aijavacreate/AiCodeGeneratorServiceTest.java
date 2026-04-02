package org.example.aijavacreate;

import jakarta.annotation.Resource;
import org.example.aijavacreate.ai.AiCodeGeneratorService;
import org.example.aijavacreate.ai.model.HtmlCodeResult;
import org.example.aijavacreate.ai.model.MultiFileCodeResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    /**
     * 测试生成 HTML 代码
     */
    @Test
    void generateHtmlCode() {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("做个程序员鱼皮的工作记录小工具");
        Assertions.assertNotNull(result);
    }
    /**
     * 测试生成多文件代码
     */
    @Test
    void generateMultiFileCode() {
        MultiFileCodeResult multiFileCode = aiCodeGeneratorService.generateMultiFileCode("做个程序员鱼皮的留言板");
        Assertions.assertNotNull(multiFileCode);
    }
}


