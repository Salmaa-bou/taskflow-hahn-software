package ma.ensah.taskflowprojecthahnsoftwarebackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("TaskFlow API")
                        .description("""
                                Task Management System with Team Collaboration
                                
                                ## Features
                                - User Authentication (JWT)
                                - Project Management
                                - Team Collaboration with Roles
                                - Task Assignment with Permissions
                                - Progress Tracking
                                
                                ## Authentication
                                1. Register or login via `/api/auth/login`
                                2. Copy the JWT token from response
                                3. Click 'Authorize' button (🔒)
                                4. Enter: `Bearer <your-token>`
                                5. All authenticated endpoints will now work
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Hahn Software Morocco")
                                .email("candidate@hahn-software.com")
                                .url("https://hahn-software.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter JWT token obtained from /api/auth/login")));
    }
}