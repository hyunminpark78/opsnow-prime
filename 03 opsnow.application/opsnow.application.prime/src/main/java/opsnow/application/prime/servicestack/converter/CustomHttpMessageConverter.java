package opsnow.application.prime.servicestack.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import opsnow.framework.core.system.AnnotationUtil;
import opsnow.framework.core.text.Encoder;
import opsnow.service.common.httpServicestack.annotation.DefaultModelAnnotation;
import opsnow.service.common.httpServicestack.command.HttpActionBase;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
@Component
public class CustomHttpMessageConverter implements HttpMessageConverter<HttpActionBase> {

    private HttpMessageConverter<Object> messageConverter = new MappingJackson2HttpMessageConverter();

    private final ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    public CustomHttpMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        boolean canRead = HttpActionBase.class.isAssignableFrom(clazz) &&
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
    public HttpActionBase read(Class<? extends HttpActionBase> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try {
            HttpActionBase action = clazz.getDeclaredConstructor().newInstance();
            if (action != null) {
                DefaultModelAnnotation annotation = AnnotationUtil.getAnnotation(clazz, DefaultModelAnnotation.class);
                Class<?> annotationClz = AnnotationUtil.getClassFromAnnotation(annotation);
                action.initAction(messageConverter.read(annotationClz, inputMessage), request);
            }
            return action;
        } catch (Exception e) {
            throw new IOException("Failed to create instance of " + clazz.getName(), e);
        }
    }

    @Override
    public void write(HttpActionBase HttpActionBase, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
