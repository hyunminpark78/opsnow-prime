package opsnow.framework.core.text;

import opsnow.framework.core.text.Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncoderTest {
    @Test
    void Test() {
        String s = "var1=1&var2=2+2%2f3&var1=3";

        System.out.println(Encoder.fromQueryString(s));
    }
}
