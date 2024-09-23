package com.study.crmProject;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// env 변수 등록과 JPA 사용을 위해 Annotation과 main() 수정
@EnableJpaAuditing
@SpringBootApplication
public class CrmProjectApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(CrmProjectApplication.class, args);
	}
}
