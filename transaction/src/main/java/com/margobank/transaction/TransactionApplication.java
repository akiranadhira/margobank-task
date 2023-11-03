package com.margobank.transaction;

import com.margobank.transaction.dto.TransactionContactInfoDto;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableAdminServer
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {TransactionContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Transaction microservice REST API Documentation",
				description = "Margobank Task Transaction microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Akira Nadhira",
						email = "akiranadhira1812@gmail.com",
						url = "https://www.linkedin.com/in/akira-nadhira-07124b17b"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0.html"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Margobank Task Transaction microservice REST API Documentation",
				url = "https://spring.io/"
		)
)
public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}

}
