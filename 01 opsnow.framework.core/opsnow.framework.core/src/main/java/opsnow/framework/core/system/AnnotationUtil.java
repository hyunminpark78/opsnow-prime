package opsnow.framework.core.system;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class AnnotationUtil {

    // 어노테이션을 저장할 맵 (캐싱 목적)
    private static final Map<String, Annotation> annotationCache = new HashMap<>();

    // 제네릭으로 어노테이션을 조회하는 메서드
    public static <T extends Annotation> T getAnnotation(Class<?> targetClass, Class<T> annotationClass) {
        // 캐시에서 조회
        String key = targetClass.getName();
        if (annotationCache.containsKey(key)) {
            return (T) annotationCache.get(key);
        }

        // 어노테이션이 없으면, targetClass에서 어노테이션을 조회하고 캐시에 저장
        T annotation = targetClass.getAnnotation(annotationClass);
        if (annotation != null) {
            annotationCache.put(key, annotation);
        }
        return annotation;
    }

    // 어노테이션에서 클래스를 추출하는 메서드 (제네릭)
    public static <T extends Annotation> Class<?> getClassFromAnnotation(T annotation) {
        try {
            // 어노테이션의 메서드 호출하여 클래스를 반환
            return (Class<?>) annotation.annotationType().getMethod("defaultClass").invoke(annotation);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to extract class from annotation", e);
        }
    }

    // 어노테이션이 포함된 클래스에서 인스턴스를 생성하는 메서드
    public static <T extends Annotation> Object createInstanceFromAnnotation(T annotation) {
        if (annotation != null) {
            try {
                // 어노테이션에서 클래스를 추출
                Class<?> implClass = getClassFromAnnotation(annotation);
                // 해당 클래스의 인스턴스를 생성하여 반환
                return createInstance(implClass);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create instance from annotation", e);
            }
        }
        return null;
    }

    // 주어진 클래스로 인스턴스를 생성하는 메서드
    private static Object createInstance(Class<?> implClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Constructor<?> constructor = implClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}