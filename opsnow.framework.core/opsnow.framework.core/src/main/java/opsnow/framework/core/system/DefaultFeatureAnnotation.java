package opsnow.framework.core.system;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefaultFeatureAnnotation {
    Class<?> defaultClass();
}