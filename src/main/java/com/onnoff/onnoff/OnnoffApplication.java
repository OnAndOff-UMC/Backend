package com.onnoff.onnoff;

import com.onnoff.onnoff.auth.feignClient.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@SpringBootApplication
@EnableJpaAuditing
public class OnnoffApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnnoffApplication.class, args);
	}

}
