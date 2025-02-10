package com.pragma.franchise.infrastructure.entrypoints.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi(
            @Value("${app.title}") String appTitle,
            @Value("${app.description}") String appDescription,
            @Value("${app.version}") String appVersion,
            @Value("${app.termsOfService}") String appTermsOfService,
            @Value("${app.license}") String appLicense,
            @Value("${app.licenseUrl}") String appLicenseUrl
    ){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(appTitle)
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService(appTermsOfService)
                        .license(new License().name(appLicense).url(appLicenseUrl))
                );
    }
}