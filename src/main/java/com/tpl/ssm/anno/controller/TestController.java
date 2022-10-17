package com.tpl.ssm.anno.controller;

import com.tpl.ssm.anno.entity.TestEntity;
import com.tpl.ssm.anno.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 1. Spring5.1.x -> jackson2.9.x
 * 2.The origin server did not find a current representation for the target resource
 * 原因是WEB-INF只能转发进去, 重定向是进不去的
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    public String listTests(TestEntity test, Model model) {
        List<TestEntity> tests = testService.listTests(test);
        model.addAttribute("tests", tests);
        return "test";
    }

    @PostMapping("/save")
    public String saveTest(TestEntity test) {
        testService.saveTest(test);
        return "redirect:/test/";
    }

    @PostMapping("/modify")
    public String modifyTest(TestEntity test) {
        testService.modifyTest(test);
        return "redirect:/test/";
    }

    @RequestMapping("/remove")
    public String removeTest(TestEntity test) {
        testService.removeTest(test);
        return "redirect:/test/";
    }

}
