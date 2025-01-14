package opsnow.application.prime.test;

import jakarta.servlet.http.HttpServletRequest;
import opsnow.service.common.httpServicestack.command.SampleAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test22";
    }

    @PostMapping("/test3")
    public Object test3(@RequestBody SampleAction action) {
        return action.executeCore();
    }
}
