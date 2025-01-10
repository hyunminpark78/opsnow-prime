package opsnow.application.prime.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import opsnow.framework.core.system.AnnotationUtil;
import opsnow.service.common.httpServicestack.annotation.DefaultModelAnnotation;
import opsnow.service.common.httpServicestack.command.ActionBase;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
@Component
public class CustomHttpMessageConverter implements HttpMessageConverter<ActionBase> {

    private HttpMessageConverter<Object> messageConverter = new MappingJackson2HttpMessageConverter();

    private final ObjectMapper objectMapper;

    public CustomHttpMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        boolean canRead = ActionBase.class.isAssignableFrom(clazz) &&
                (MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(mediaType.toString()));

        return canRead;
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
