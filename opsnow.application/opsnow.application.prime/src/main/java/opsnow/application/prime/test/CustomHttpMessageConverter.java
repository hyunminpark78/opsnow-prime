package opsnow.application.prime.test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import opsnow.framework.core.system.AnnotationUtil;
import opsnow.framework.core.system.DefaultFeature;
import opsnow.framework.core.system.DefaultFeatureAnnotation;
import org.springframework.boot.Banner;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.List;
import java.util.Set;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;


@Component
public class CustomHttpMessageConverter implements HttpMessageConverter<ActionBase> {

    private HttpMessageConverter<Object> messageConverter = new MappingJackson2HttpMessageConverter();

    private final ObjectMapper objectMapper;

    public CustomHttpMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
//        boolean a = ActionBase.class.isAssignableFrom(clazz) &&
//                (MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(mediaType.toString()));

        System.out.println("===" + MediaType.APPLICATION_JSON_UTF8_VALUE + "===");
        System.out.println("===" + mediaType + "===");
        System.out.println(MediaType.APPLICATION_JSON_UTF8_VALUE.equals(mediaType.toString()));
        //System.out.println("================================== 21" + MediaType.APPLICATION_JSON.equals(mediaType));
//        System.out.println("================================== 22" + MediaType.APPLICATION_JSON_UTF8_VALUE);
//        System.out.println("================================== 22" + mediaType);
//        System.out.println("================================== 23" + MediaType.APPLICATION_JSON_UTF8_VALUE.equals(mediaType));

//        System.out.println("================================== 2" + a);
//
//        System.out.println("================================== 2" + ActionBase.class.isAssignableFrom(clazz));
//        System.out.println("================================== 2" + MediaType.APPLICATION_JSON.equals(mediaType));

//        System.out.println("================================== 2" + MediaType.APPLICATION_JSON);
//        System.out.println("================================== 2" + MediaType.APPLICATION_JSON_UTF8_VALUE);
//        System.out.println("================================== 2" + mediaType);

        return true;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of();
    }

    @Override
    public ActionBase read(Class<? extends ActionBase> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try {
            ActionBase action = clazz.getDeclaredConstructor().newInstance();
            if (action != null) {
                DefaultModelAnnotation annotation = AnnotationUtil.getAnnotation(clazz, DefaultModelAnnotation.class);
                Class<?> annotationClz = AnnotationUtil.getClassFromAnnotation(annotation);
                action.setModel(messageConverter.read(annotationClz, inputMessage));
            }
            return action;
        } catch (Exception e) {
            throw new IOException("Failed to create instance of " + clazz.getName(), e);
        }
    }

    @Override
    public void write(ActionBase ActionBase, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
