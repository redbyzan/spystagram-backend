package io.redbyzan.spystagram.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by roger on 2016. 5. 2..
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/helloworld")
    public String home() {
        return "Hello World~!";
    }

}
