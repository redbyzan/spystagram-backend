package io.redbyzan.spystagram.rest;

import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roger on 2016. 5. 2..
 */
@RestController
public class SecureController {

    @RequestMapping(method = RequestMethod.GET, value = "/api/me")
    @ApiOperation(value = "Returns the authentication object of the current user")
    public Map<String, Object> me(Authentication authentication) {

        Map<String, Object> result = new HashMap<>();
        result.put("authentication", authentication);
        return result;
    }
}
