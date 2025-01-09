package opsnow.framework.core.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@DefaultFeatureAnnotation(defaultClass = DefaultFeature.class)
class MyAnnotatedClass {
    // 이 클래스는 어노테이션을 통해 MyClass를 가리킵니다.
}


@SpringBootTest
public class AnnotationUtilTest {
    @Test
    public void test() {
        // MyAnnotatedClass에서 DefaultFeatureAnnotation 어노테이션을 읽고, 해당 클래스를 생성
        DefaultFeatureAnnotation annotation = AnnotationUtil.getAnnotation(MyAnnotatedClass.class, DefaultFeatureAnnotation.class);

        if (annotation != null) {
            // 어노테이션에서 지정한 클래스로 인스턴스를 생성
            Object instance = AnnotationUtil.createInstanceFromAnnotation(annotation);

            if (instance != null && instance instanceof DefaultFeature) {
                DefaultFeature defaultFeatrue = (DefaultFeature) instance;

                System.out.println(DefaultFeatureAnnotation.class);
            }
        }


    }
}
