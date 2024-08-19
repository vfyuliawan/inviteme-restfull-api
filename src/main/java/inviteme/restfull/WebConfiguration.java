package inviteme.restfull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import inviteme.restfull.resolver.UserArgumentResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{

    @Autowired private UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(userArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000") // Replace with your frontend origin
        .allowedOrigins("https://invite-me.click")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");
    }

}
