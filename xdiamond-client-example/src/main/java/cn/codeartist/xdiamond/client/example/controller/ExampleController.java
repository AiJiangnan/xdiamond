package cn.codeartist.xdiamond.client.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 艾江南
 * @date 2019/4/14
 */
@RestController
@RequestMapping("example")
public class ExampleController {

    @GetMapping
    public String test(@Value("${name}") String test) {
        return test;
    }
}
