package com.lorin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/15 20:42
 */


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * TODO
     *
     * @author lorin
     * @date 2021/12/15 20:42
     * @param null
     * @return null
     */
    @Value("${file.staticPatternPath}")
    private String staticPatternPath;
    @Value("${file.uploadPath}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticPatternPath + "/**").addResourceLocations("file:" + uploadPath);
    }
}