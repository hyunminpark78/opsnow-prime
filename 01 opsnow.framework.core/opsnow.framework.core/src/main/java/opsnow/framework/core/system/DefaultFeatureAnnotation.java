package opsnow.framework.core.system;

import java.lang.annotation.*;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefaultFeatureAnnotation {
    Class<?> defaultClass();
}