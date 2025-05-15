package com.healthcare.appointment.feignClients;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestAttributes;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Retrieve the JWT token from request attributes
            String token = (String) RequestContextHolder.getRequestAttributes()
                    .getAttribute("jwtToken", RequestAttributes.SCOPE_REQUEST);

            System.out.println("Feign Request Interceptor Invoked");
            System.out.println("Token Passed: " + token);

            if (token != null && !token.isEmpty()) {
                requestTemplate.header("Authorization", "Bearer " + token);
                System.out.println("Authorization Header Set: Bearer " + token);
            } else {
                System.out.println("Authorization Header Missing");
            }
        };
    }
}
