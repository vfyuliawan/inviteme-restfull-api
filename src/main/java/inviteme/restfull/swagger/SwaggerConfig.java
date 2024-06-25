package inviteme.restfull.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@Configuration
@OpenAPIDefinition(info = @Info(title = "INVITE ME API", version = "v1"), security = @SecurityRequirement(name = "bearerAuth"))
// @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {

    @Bean
  public GroupedOpenApi publicApi() {
      return GroupedOpenApi.builder()
              .group("inviteme-public")
              .pathsToMatch("/api/**")
              .build();
  }

}
