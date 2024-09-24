package com.exam.portal;

import com.exam.portal.config.RSAProperties;
import com.exam.portal.model.Role;
import com.exam.portal.repository.RoleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@EnableConfigurationProperties(RSAProperties.class)
@SpringBootApplication
public class PortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

	@Bean
	public ApplicationRunner initializer(RoleRepository roleRepository) {
		return args -> roleRepository.saveAll(Arrays.asList(
				Role.builder().roleName("USER").description("User Access").build(),
				Role.builder().roleName("ADMIN").description("Admin Control").build()));
	}
}
