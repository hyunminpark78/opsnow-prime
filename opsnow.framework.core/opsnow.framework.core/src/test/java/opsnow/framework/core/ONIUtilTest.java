package opsnow.framework.core;

import opsnow.framework.core.collections.ALKList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ONIUtilTest {
    @Test
    void Test() {
        System.out.println(ONIUtil.makeGuid(""));
    }
}
