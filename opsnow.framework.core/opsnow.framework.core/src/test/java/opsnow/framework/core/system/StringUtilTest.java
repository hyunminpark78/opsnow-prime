package opsnow.framework.core.system;

import opsnow.framework.core.collections.ALKList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class StringUtilTest {
    @Test
    void Test() {

        Boolean isMail = StringUtil.isEmail("hyunmin.park@opsnow");

        System.out.println("isMail : " + isMail);

        Assertions.assertThat(isMail).isEqualTo(false);
    }
}
