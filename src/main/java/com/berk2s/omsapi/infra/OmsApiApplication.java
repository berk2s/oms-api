package com.berk2s.omsapi.infra;

import com.berk2s.omsapi.domain.annotations.DomainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = "com.berk2s",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainService.class})
        })
public class OmsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmsApiApplication.class, args);
    }

}
