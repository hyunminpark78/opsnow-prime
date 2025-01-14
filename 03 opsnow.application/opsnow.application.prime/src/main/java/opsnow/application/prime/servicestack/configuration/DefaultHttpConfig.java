package opsnow.application.prime.servicestack.configuration;

import opsnow.application.prime.servicestack.converter.DefaultHttpMessageConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */

@Configuration
@ComponentScan(basePackages = {
        "opsnow.application.prime",
        "opsnow.service.common"
})
@Profile("dev")
public class DefaultHttpConfig implements WebMvcConfigurer {

    private final DefaultHttpMessageConverter customHttpMessageConverter;

    public DefaultHttpConfig(DefaultHttpMessageConverter customHttpMessageConverter) {
        this.customHttpMessageConverter = customHttpMessageConverter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, customHttpMessageConverter); // 우선순위를 위해 맨 앞에 추가
    }
}