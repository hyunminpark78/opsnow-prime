package opsnow.framework.core.collections;

import opsnow.framework.core.ONIUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ALKListTest {
    @Test
    void Test() {
        ALKList<String> list = new ALKList<>();

        list.add("Hello World!");

        list.forEach(System.out::println);

        System.out.println("list.size() : " + list.size());

        Assertions.assertThat(list.size()).isEqualTo(1);
    }
}
