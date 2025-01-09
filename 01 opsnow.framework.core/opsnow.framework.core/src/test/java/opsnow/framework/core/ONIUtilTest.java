package opsnow.framework.core;

import opsnow.framework.core.collections.ALKList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ONIUtilTest {
    @Test
    void Test() {

        String GuidA = ONIUtil.makeGuid("");
        String GuidB = ONIUtil.makeGuid("");

        System.out.println("GuidA : " + GuidA);
        System.out.println("GuidB : " + GuidB);

        Assertions.assertThat(GuidA).isNotEqualTo(GuidB);

    }
}
