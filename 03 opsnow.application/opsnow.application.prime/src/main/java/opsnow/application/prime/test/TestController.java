package opsnow.application.prime.test;

import opsnow.service.common.httpServicestack.command.ModelAction;
import opsnow.service.common.httpServicestack.dto.DefaultModel;
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

    @PostMapping("/test2")
    public String test2(@RequestBody DefaultModel model) {
        //System.out.println("==================================");
        //model.excuteCore();
        return model.getAddress();
    }

    @PostMapping("/test3")
    public Object test3(@RequestBody ModelAction action) {
        //System.out.println("==================================");
        //model.executeCore();

        //action.executeCore();
        return action.executeCore();
        //return "1234";
    }
}
