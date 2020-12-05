package com.nemtool.explorer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: main running class
 * @author Masker
 * @date 2020.06.28
 */
@SpringBootApplication(scanBasePackages= {"com.nemtool.explorer"})
@MapperScan("com.nemtool.explorer.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
