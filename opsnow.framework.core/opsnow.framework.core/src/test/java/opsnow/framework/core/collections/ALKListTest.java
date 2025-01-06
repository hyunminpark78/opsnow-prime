package opsnow.framework.core.collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ALKListTest {
    @Test
    void Test() {
        ALKList<String> list = new ALKList<>();

        list.add("Hello World!");

        list.forEach(System.out::println);
    }
}
