package api.spring.bluebank.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:openapi.properties") 
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("api.spring.bluebank.controller")).paths(PathSelectors.any())
				.build().apiInfo(metadata()).useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, responseMessageForGET());
	}

	public class OpenApiConfig { 
		@Bean
		public GroupedOpenApi rootGroup() { 
			return GroupedOpenApi.builder()
					.group("root")
					.addOperationCustomizer((operation, handlerMethod) -> { 
						operation.addSecurityItem(new SecurityRequirement()
								.addList("bearerAuth")); 
						return operation; }).build(); } 
	}
	public static ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title("API - Banco BlueBank")
				.description("Projeto API Spring/AWS - Blue Bank")
				.version("1.0.0")
				.license("Apache License Version 2.0")
				.licenseUrl("http://localhost:8080/swagger-ui/")
				.contact(contact()).build();
	}

	private static Contact contact() {
		return new Contact("Hanely Taniguchi", "https://github.com/nyodinariai/S1-T2-BlueBank\r\n", "hanely.menezes@gmail.com");
	}

	private static List<Response> responseMessageForGET() {
		return new ArrayList<Response>() {
			private static final long serialVersionUID = 1L;
			{
				add(new ResponseBuilder().code("200").description("Sucesso!").build());
				add(new ResponseBuilder().code("201").description("Objeto Criado!").build());
				add(new ResponseBuilder().code("401").description("Não Autorizado!").build());
				add(new ResponseBuilder().code("403").description("Proibido!").build());
				add(new ResponseBuilder().code("404").description("Não Encontrado!").build());
				add(new ResponseBuilder().code("500").description("Erro!").build());
			}
		};
	}
}