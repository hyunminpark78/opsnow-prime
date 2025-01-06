package opsnow.framework.core.runtime;

import opsnow.framework.core.collections.ITypedCollection;
import opsnow.framework.core.collections.TypedCollection;
import opsnow.framework.core.system.AnnotationUtil;
import opsnow.framework.core.system.DefaultFeature;
import opsnow.framework.core.system.DefaultFeatureAnnotation;
import opsnow.framework.core.system.ValueFeature;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class AppRuntime {
    /// Features

    private static final ITypedCollection features = new TypedCollection();

    // #region Features

    public static <TFeature> void setFeature(TFeature instance) {
        features.set(instance.getClass(), instance);
    }

    public static void setFeature(Class<?> targetType, Object instance) {
        features.set(targetType, instance);
    }

    public static <TFeature> void trySetFeature(Function<Void, TFeature> factory) {
        features.trySet(factory.getClass(), factory::apply);
    }

    public static void trySetFeature(Class<?> targetType, Function<Void, Object> factory) {
        features.trySet(targetType, factory::apply);
    }
//
//    public static <TFeature> TFeature getFeature(Class<TFeature> featureClass) {
//        return featureClass.cast(getFeature((Class<?>) featureClass));
//    }
//
    public static Object getFeature(Class<?> targetType) {
        return features.getOrAdd(targetType, (t) -> {
            DefaultFeatureAnnotation annotation = AnnotationUtil.getAnnotation(targetType, DefaultFeatureAnnotation.class);

            if (annotation != null) {
                Object instance = AnnotationUtil.createInstanceFromAnnotation(annotation);
                if (instance != null && instance instanceof DefaultFeature) {
                    DefaultFeature defaultFeatrue = (DefaultFeature) instance;
                    return defaultFeatrue;
                }
            }
            return null;
        });
    }

    public static void clearFeatures() {
        for (Map.Entry<Class<?>, Object> entry : features) {
            if (entry.getValue() instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) entry.getValue()).close();
                } catch (Exception e) {
                    // Log or handle exception
                }
            }
        }
        features.clear();
    }
//
//    public static <TFeature> void configureFeature(Class<TFeature> featureClass, Function<TFeature, Void> configAction) {
//        TFeature feature = getFeature(featureClass);
//        if (feature != null) {
//            configAction.apply(feature);
//        }
//    }
//
//    public static void configureFeature(Class<?> targetType, Function<Object, Void> configAction) {
//        Object feature = getFeature(targetType);
//        if (feature != null) {
//            configAction.apply(feature);
//        }
//    }

    // #endregion

    // #region Values

    public static <TValue> void setValue(TValue instance) {
        features.set(instance.getClass(), new ValueFeature<>(instance));
    }

    public static void setValue(Class<?> targetType, Object instance) {
        Class<?> genericType = ValueFeature.class;
        features.set(genericType, new ValueFeature<>(instance));
    }

    public static <TValue> void setValue(Function<Void, TValue> factory) {
        features.trySet(factory.getClass(), (t) -> new ValueFeature<>(factory.apply(null)));
    }

    public static void setValue(Class<?> targetType, Function<Void, Object> factory) {
        features.trySet(ValueFeature.class, (t) -> new ValueFeature<>(factory.apply(null)));
    }
//
//    public static <TValue> TValue getValue(Class<TValue> valueType) {
//        ValueFeature<TValue> feature = (ValueFeature<TValue>) features.get(valueType);
//        return (feature != null) ? feature.getValue() : null;
//    }

    public static Object getValue(Class<?> targetType) {
        ValueFeature<?> feature = (ValueFeature<?>) features.get(targetType);
        return (feature != null) ? feature.getValue() : null;
    }

//    public static <TValue> TValue getValue(Class<TValue> valueType, Function<Void, TValue> fallback) {
//        ValueFeature<TValue> feature = (ValueFeature<TValue>) features.getOrAdd(valueType, () -> new ValueFeature<>(fallback.apply(null)));
//        return (feature != null) ? feature.getValue() : null;
//    }

    public static Object getValue(Class<?> targetType, Function<Void, Object> fallback) {
        ValueFeature<?> feature = (ValueFeature<?>) features.getOrAdd(targetType, (t) -> new ValueFeature<>(fallback.apply(null)));
        return (feature != null) ? feature.getValue() : null;
    }
//
//    public static <TValue> TValue removeValue(Class<TValue> valueType) {
//        ValueFeature<TValue> feature = (ValueFeature<TValue>) features.remove(valueType);
//        return (feature != null) ? feature.getValue() : null;
//    }

    public static Object removeValue(Class<?> targetType) {
        ValueFeature<?> feature = (ValueFeature<?>) features.remove(targetType);
        return (feature != null) ? feature.getValue() : null;
    }

    // #endregion
}
