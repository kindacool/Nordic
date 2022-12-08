
package com.nordic.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class MainController {

    @GetMapping("/")
    public String Main() {
        return "Hello, Nordic!";
    }

}