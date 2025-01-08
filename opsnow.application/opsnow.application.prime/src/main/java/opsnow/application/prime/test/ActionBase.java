package opsnow.application.prime.test;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ActionBase<T> {
    protected  T model;

    public void setModel(T model) {
        this.model = model;
    }

    protected void init() {
        System.out.println("ActionBase init");
    }

    protected void executeCore() {
        init();
    }
}
