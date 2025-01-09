package opsnow.service.common.httpServicestack.configuration;

import opsnow.service.common.httpServicestack.converter.CustomHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    private final CustomHttpMessageConverter customHttpMessageConverter;

    public CustomWebMvcConfig(CustomHttpMessageConverter customHttpMessageConverter) {
        System.out.println("CustomWebMvcConfig");
        this.customHttpMessageConverter = customHttpMessageConverter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        System.out.println("extendMessageConverters");
        converters.add(0, customHttpMessageConverter); // 우선순위를 위해 맨 앞에 추가
    }
}