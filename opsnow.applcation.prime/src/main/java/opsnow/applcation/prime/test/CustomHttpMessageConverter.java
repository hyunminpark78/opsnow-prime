package opsnow.applcation.prime.test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.Banner;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;


@Component
public class CustomHttpMessageConverter implements HttpMessageConverter<ActionBase> {

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
            // clazz를 사용하여 inputMessage에서 모델을 파싱
            // 객체를 읽을 때 clazz 타입에 맞게 읽어옵니다.
            ActionBase action = clazz.getDeclaredConstructor().newInstance();
            action.setModel(inputMessage);
            // inputMessage에서 해당 클래스에 맞는 모델을 파싱하여 설정
            // 예를 들어, ActionBase이 아니라 clazz의 실제 타입에 맞는 객체를 읽습니다.
            //Object model = objectMapper.readValue(inputMessage.getBody(), clazz.getGenericSuperclass());

            // 모델을 해당 인스턴스에 설정
            //action.setModel(model);
//            // ObjectMapper를 사용하여 inputMessage에서 모델을 파싱
//            ActionBase model = objectMapper.readValue(inputMessage.getBody(), ActionBase.class);
//
//            // clazz로 동적으로 ActionBase의 서브클래스 인스턴스를 생성
//            ActionBase action = clazz.getDeclaredConstructor().newInstance();
//
//            // 모델을 해당 인스턴스에 설정
//            action.setModel(model);
//
//            // 생성된 인스턴스를 리턴
//            return action;
            return action;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to create instance of " + clazz.getName(), e);
        }
    }

    @Override
    public void write(ActionBase ActionBase, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
