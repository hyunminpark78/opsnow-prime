package opsnow.applcation.prime.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "test22";
    }

    @PostMapping("/test2")
    public String test2(@RequestBody DefaultModel model) {
        //System.out.println("==================================");
        model.excuteCore();
        return model.getAddress();
    }

    @PostMapping("/test3")
    public String test3(@RequestBody ModelAction<DefaultModel> model) {
        //System.out.println("==================================");
        //model.executeCore();
        model.executeCore();
        return "test3";
    }
}
