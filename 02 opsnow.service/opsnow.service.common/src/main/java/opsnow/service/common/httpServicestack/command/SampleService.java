package opsnow.service.common.httpServicestack.command;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleService {
    @Autowired
    private HttpServletRequest request;

    public void test() {
        System.out.println("Test");
    }
}
