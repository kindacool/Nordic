
package com.nordic.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nordic.repository.test.TestMapper;

import java.util.List;

@RestController
public class MainController {

    private TestMapper mapper;

    public MainController(TestMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String Main() {
        return "Hello, Nordic!";
    }

}